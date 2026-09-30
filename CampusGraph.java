import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * CampusGraph.java
 * -------------------------------------------------
 * Member 4 - Requirements 7-11:
 * "Use a graph to represent campus locations and their connections."
 * "Represent the graph using an adjacency list or adjacency matrix."
 * "Provide operations to add and remove campus locations and
 *  connections/roads."
 * "Display connected locations and/or the campus network."
 * "Implement at least one graph traversal: BFS or DFS."
 *
 * We model the campus as an UNDIRECTED graph:
 *   - VERTICES  = campus locations (e.g. "Library", "Canteen")
 *   - EDGES     = roads/paths between two locations
 *
 * We use an ADJACENCY LIST: a Map where each location points to a list
 * of the locations directly connected to it. This is memory-efficient
 * and easy to traverse, which is why it's the more common choice over
 * an adjacency matrix for real-world sized graphs.
 *
 * We implement BOTH BFS and DFS traversal (the brief only requires
 * one, so you can mention/demo whichever your lecturer prefers, or
 * show both).
 */
public class CampusGraph {

    // LinkedHashMap keeps locations in the order they were added, which
    // makes displayGraph() output easier to read/demo.
    private Map<String, List<String>> adjacencyList;

    public CampusGraph() {
        adjacencyList = new LinkedHashMap<>();
    }

    /** Adds a new location (vertex). Returns false if it already exists. */
    public boolean addLocation(String location) {
        if (adjacencyList.containsKey(location)) return false;
        adjacencyList.put(location, new ArrayList<>());
        return true;
    }

    /**
     * Removes a location and any roads connected to it.
     * Returns false if the location doesn't exist.
     */
    public boolean removeLocation(String location) {
        if (!adjacencyList.containsKey(location)) return false;
        adjacencyList.remove(location);
        // Also remove this location from every other location's neighbour list
        for (List<String> neighbours : adjacencyList.values()) {
            neighbours.remove(location);
        }
        return true;
    }

    /**
     * Adds a two-way road between loc1 and loc2.
     * Returns false if either location doesn't exist.
     */
    public boolean addConnection(String loc1, String loc2) {
        if (!adjacencyList.containsKey(loc1) || !adjacencyList.containsKey(loc2)) {
            return false;
        }
        if (!adjacencyList.get(loc1).contains(loc2)) adjacencyList.get(loc1).add(loc2);
        if (!adjacencyList.get(loc2).contains(loc1)) adjacencyList.get(loc2).add(loc1);
        return true;
    }

    /** Removes the road between loc1 and loc2, if it exists. */
    public boolean removeConnection(String loc1, String loc2) {
        if (!adjacencyList.containsKey(loc1) || !adjacencyList.containsKey(loc2)) {
            return false;
        }
        adjacencyList.get(loc1).remove(loc2);
        adjacencyList.get(loc2).remove(loc1);
        return true;
    }

    public boolean hasLocation(String location) {
        return adjacencyList.containsKey(location);
    }

    /** Prints every location and the locations it's directly connected to. */
    public void displayGraph() {
        if (adjacencyList.isEmpty()) {
            System.out.println("No campus locations added yet.");
            return;
        }
        System.out.println("----- Campus Network (Adjacency List) -----");
        for (String loc : adjacencyList.keySet()) {
            System.out.println(loc + "  ->  " + adjacencyList.get(loc));
        }
    }

    /**
     * BREADTH-FIRST SEARCH: visits the starting location, then all of
     * its direct neighbours, then all of THEIR neighbours, and so on
     * (level by level). Good for finding the shortest path in terms of
     * number of roads.
     */
    public void bfsTraversal(String start) {
        if (!adjacencyList.containsKey(start)) {
            System.out.println("Location not found: " + start);
            return;
        }
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        System.out.print("BFS Traversal from \"" + start + "\": ");
        while (!queue.isEmpty()) {
            String current = queue.poll();
            System.out.print(current + " ");
            for (String neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        System.out.println();
    }

    /**
     * DEPTH-FIRST SEARCH: goes as far as possible down one path before
     * backtracking. Implemented recursively here.
     */
    public void dfsTraversal(String start) {
        if (!adjacencyList.containsKey(start)) {
            System.out.println("Location not found: " + start);
            return;
        }
        Set<String> visited = new LinkedHashSet<>();
        System.out.print("DFS Traversal from \"" + start + "\": ");
        dfsHelper(start, visited);
        System.out.println();
    }

    private void dfsHelper(String current, Set<String> visited) {
        visited.add(current);
        System.out.print(current + " ");
        for (String neighbour : adjacencyList.get(current)) {
            if (!visited.contains(neighbour)) {
                dfsHelper(neighbour, visited);
            }
        }
    }
}
