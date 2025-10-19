package dev.floraf.loader.data;

import java.util.*;
import java.util.function.Consumer;

/**
 * An implementation of a directed acyclic graph that serves as a means of
 * tracking dependencies of Mashup mods through the use of Tarjan's strongly-
 * connected-components algorithm to identify dependency cycles and to ensure
 * that the graph is, in fact, a DAG.
 */
public class DependencyGraph {
    private int current = -1;

    private ArrayList<Vertex> vertices = new ArrayList<>();

    /**
     *
     */
    private HashMap<Integer, HashSet<Integer>> incoming = new HashMap<>();
    /**
     *
     */
    private HashMap<Integer, HashSet<Integer>> outgoing = new HashMap<>();
    /**
     * 
     */
    private HashMap<MashupPatch, Vertex> byPatchId = new HashMap<>();

    /**
     * A "node" on the graph, or (more accurately to this context) a single
     * patch for Mashup that has been registered.
     */
    public class Vertex {
        int id;
        MashupPatch data;

        int index = -1;
        int lowlink = -1;

        boolean onStack = false;

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return id == vertex.id;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        public Vertex(MashupPatch data) {
            current += 1;

            this.id = current;
            this.data = data;

            incoming.put(current, new HashSet<>());
            outgoing.put(current, new HashSet<>());
            byPatchId.put(data, this);
        }
    }

    /**
     *
     */
    public class Edge {
        int source;
        int target;
        DependencyType type;

        public Edge(int source, int target, DependencyType type) {
            // This shouldn't ever happen, but I'm not taking any chances.
            if (!incoming.containsKey(source) || !incoming.containsKey(target)) {
                throw new IllegalArgumentException();
            }

            if (source == target) {
                throw new IllegalArgumentException(
                    "A patch can't depend on itself!");
            }

            outgoing.get(source).add(target);
            incoming.get(target).add(source);
        }
    }

    /**
     * Traverses through the graph's vertices to find a list of
     * @return
     */
    public boolean analyze() {
        return true;
    }

    /**
     * An implementation of Tarjan's strongly- connected-components algorithm.
     * Part two of {@link DependencyGraph#resolve()}.
     *
     * @see DependencyGraph#resolve()
     * @return A reverse-topological-sorted list of all vertices (i.e. the load
     *         order of all patches in the dependency graph), or a list of sets
     *         of vertices that form dependency cycles.
     * @apiNote When using the {@link Either} return value, it's recommended to
     *          use a <code>switch</code> expression to exhaustively
     *          pattern-match the result.
     */
    public Either<List<Vertex>, List<Set<Vertex>>> validate() {
        // By defining the method here, this allows us to keep the DFS-
        // specific variables contained within the outer method's scope.
        class DFS {
            int               dfsIndex = 0;
            Stack<Vertex>     dfsStack = new Stack<>();
            List<Set<Vertex>> cycles   = new ArrayList<>();
            List<Vertex>      order    = new ArrayList<>();

            public void search(Vertex vertex) {
                vertex.index = dfsIndex;
                vertex.lowlink = dfsIndex;

                dfsIndex += 1;
                dfsStack.push(vertex);
                vertex.onStack = true;

                for (int edge : outgoing.get(vertex.id)) {
                    Vertex child = vertices.get(edge);

                    if (child.index == -1) {
                        search(child);
                        vertex.lowlink = Math.min(vertex.lowlink, child.lowlink);
                    } else if (child.onStack) {
                        vertex.lowlink = Math.min(vertex.lowlink, child.index);
                    }
                }

                if (vertex.lowlink == vertex.index) {
                    ArrayList<Vertex> component = new ArrayList<>();

                    Vertex child;
                    do {
                        child = dfsStack.pop();
                        child.onStack = false;
                        component.add(child);
                    } while (child.id != vertex.id);

                    if (component.size() > 1) cycles.add(new HashSet<>(component));
                    else order.add(component.getFirst());
                }
            }
        }

        DFS dfs = new DFS();

        for (Vertex vertex : vertices) {
            if (vertex.index == -1) {
                dfs.search(vertex);
            }
        }

        if (dfs.cycles.size() > 0) return new Right<>(dfs.cycles);
        else return new Left<>(dfs.order);
    }
}
