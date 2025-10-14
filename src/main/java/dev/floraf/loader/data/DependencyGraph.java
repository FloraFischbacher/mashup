package dev.floraf.loader.data;

import java.util.*;

/**
 * An implementation of a directed acyclic graph that serves as a means of
 * tracking dependencies of Mashup mods through the use of Tarjan's strongly-
 * connected-components algorithm to identify dependency cycles and to ensure
 * that the graph is, in fact, a DAG.
 */
public class DependencyGraph {
    /**
     *
     */
    private int elements;
    /**
     *
     */
    private boolean valid = false;

    /**
     *
     */
    private ArrayList<Optional<HashSet<Integer>>> incoming;
    /**
     *
     */
    private ArrayList<Optional<HashSet<Integer>>> outgoing;

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

        public Vertex(MashupPatch data) {}
    }

    /**
     *
     */
    public class Edge {
        int from;
        int to;
        DependencyType type;

        public Edge(int from, int to, DependencyType type) {}
    }

    /**
     * An implementation of dependency validation using Tarjan's strongly-
     * connected-components algorithm. A majority of the work is actually
     * performed by the depth-first search defined in
     * {@link DependencyGraph#dfs()}.
     *
     * @see DependencyGraph#dfs()
     * @return A reverse-topological-sorted list of all vertices (i.e. the load
     *         order of all patches in the dependency graph), or a list of sets
     *         of vertices that form dependency cycles.
     * @apiNote When using the {@link Either} return value, it's recommended to
     *          use a <code>switch</code> expression to exhaustively
     *          pattern-match the result.
     */
    public Either<List<Vertex>, List<Set<Vertex>>> validate() {
        List<Vertex> exampleLeft = new ArrayList<>();
        List<Set<Vertex>> exampleRight = new ArrayList<>();

        exampleRight.add(new HashSet<>());
        exampleRight.add(new HashSet<>());

        if (valid) return new Left<>(exampleLeft);
        else return new Right<>(exampleRight);
    }

    /**
     * Implementation of a depth-first search for the dependency graph.
     */
    public void dfs() {

    }
}
