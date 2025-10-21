import dev.floraf.loader.data.DependencyGraph;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests relating to the dependency resolution system of Mashup.
 * @see DependencyGraph
 */
@DisplayName("Dependency graph tests")
public class TestDependencyGraph {
    private final DependencyGraph testGraph = new DependencyGraph();

    @Test
    @DisplayName("Add vertex to graph")
    public void testAddVertex() {

    }

    @Test
    @DisplayName("Add edge to graph")
    public void testAddEdge() {

    }

    @Test
    @DisplayName("Prevent self-referential dependency")
    /**
     * Ensures that a patch (represented as a vertex on the graph) cannot
     * have a dependency on itself.
     */
    public void testPreventBootstrapping() {

    }

    /**
     * Verifies whether removing a vertex from the graph works as intended at
     * the most basic level (whether or not the vertex provided can be found in
     * the graph after running the method).
     * 
     * @see #testRemoveVertex()
     */
    @Test
    @DisplayName("Remove vertex from graph")
    public void testRemoveVertex() {

    }

    /**
     * Verifies whether removing an edge from the graph works as intended at
     * the most basic level (whether or not the edge provided can be found in
     * the graph after running the method).
     * 
     * @see #testRemoveEdge()
     */
    @Test
    @DisplayName("Remove edge from graph")
    public void testRemoveEdge() {

    }

    /**
     * Verifies that attempting to get indices
     */
    @Test
    @DisplayName("Input out-of-bounds index")
    public void testOOB() {

    }

    /**
     * Verifies that the dependency graph's {@link DependencyGraph#validate()}
     * function detects dependency cycles (i.e. whether Tarjan's strongly
     * connected components algorithm was properly implemented).
     */
    @Test
    @DisplayName("Prevent and identify dependency cycles")
    public void testDependencyCycle() {

    }

    /**
     * Verifies that the {@link DependencyGraph#validate()} method of the graph
     * accurately topologically sorts the input in reverse order (this is due
     * to the nature of how Tarjan's algorithm works).
     */
    @Test
    @DisplayName("Reverse topological sort of valid dependency graph")
    public void testTopologicalSort() {

    }

    /**
     * 
     */
    @Test
    @DisplayName("Halt on unmet hard dependency")
    public void testRequiredDependency() {

    }

    /**
     * Ensures that firm dependencies not loading can cause hard dependencies
     * to halt the mod-loading process.
     */
    @Test
    @DisplayName("Halt on cascaded unmet hard dependency")
    public void testFirmDepCascade() {

    }
}
