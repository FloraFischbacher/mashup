package dev.floraf.loader.data;

import java.util.*;

import dev.floraf.loader.data.exception.RequiredPatchException;
import dev.floraf.loader.misc.either.Either;
import dev.floraf.loader.misc.either.Left;
import dev.floraf.loader.misc.either.Right;

/**
 * An implementation of a directed acyclic graph that serves as a means of
 * tracking dependencies of Mashup mods through the use of Tarjan's strongly-
 * connected-components algorithm to identify dependency cycles and to ensure
 * that the graph is, in fact, a DAG.
 */
public class DependencyGraph {
    private int currentVertex = -1;

    private ArrayList<Optional<Vertex>> vertices = new ArrayList<>();

    /**
     *
     */
    private ArrayList<Optional<ArrayList<Integer>>> incoming = new ArrayList<>();
    /**
     *
     */
    private ArrayList<Optional<ArrayList<Integer>>> outgoing = new ArrayList<>();
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
        Optional<MashupPatch> data;

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
            currentVertex += 1;

            this.id = currentVertex;
            this.data = Optional.of(data);

            incoming.add(currentVertex, Optional.of(new ArrayList<>()));
            outgoing.add(currentVertex, Optional.of(new ArrayList<>()));
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
            this.source = source;
            this.target = target;
            this.type = type;

            // This shouldn't ever happen, but I'm not taking any chances.
            if (
                incoming.get(source).isEmpty()
                || outgoing.get(target).isEmpty()
            ) {
                throw new IllegalArgumentException();
            }

            if (source == target) {
                throw new IllegalArgumentException(
                    "A patch can't depend on itself!");
            }

            outgoing.get(source).get().add(target);
            incoming.get(target).get().add(source);
        }
    }

    public boolean removeVertex(int target) {
        if (target >= vertices.size() || target < 0) return false;

        Optional<Vertex> toRemove = vertices.get(target);
        if (toRemove.isEmpty()) return false;
        
        incoming.set(target, Optional.empty());
        outgoing.set(target, Optional.empty());
        
        for (int v = 0; v < incoming.size(); v++) {
            Optional<ArrayList<Integer>> inc = incoming.get(v);
            Optional<ArrayList<Integer>> out = outgoing.get(v);

            if (inc.isPresent()) {
                ArrayList<Integer> arr = inc.get();
                for (int i = 0; i < arr.size(); i++) {
                    if (arr.get(i) == target) arr.remove(i);
                }
            }

            if (out.isPresent()) {
                ArrayList<Integer> arr = inc.get();
                for (int o = 0; o < arr.size(); o++) {
                    if (arr.get(o) == target) arr.remove(o);
                }
            }
        }

        return true;
    }

    public boolean removeEdge(int source, int target) {
        return true;
    }

    /**
     * Traverses through the graph's vertices to find which dependencies are
     * satisfied and responds appropriately if they are not found.
     */
    public void analyze() throws RequiredPatchException {
        for (Optional<Vertex> vertex : vertices) {
            if (vertex.isEmpty()) continue;
        }
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

                for (int edge : outgoing.get(vertex.id).get()) {
                    Vertex child = vertices.get(edge).get();

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

        for (Optional<Vertex> vertex : vertices) {
            if (vertex.get().index == -1) {
                dfs.search(vertex.get());
            }
        }

        if (dfs.cycles.size() > 0) return new Right<>(dfs.cycles);
        else return new Left<>(dfs.order);
    }
}
