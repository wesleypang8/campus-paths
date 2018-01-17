package graphStructure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Graphs represent a collection of data nodes and the edges between them. They
 * are immutable. Nodes cannot have edges with other nodes outside of the graph.
 * There can be no two nodes with the same data. Nodes and labels are of a
 * generic type
 *
 * @author WP
 *
 */
public class Graph<T, E> {
    private final Map<T, Set<Edge<T, E>>> nodeMap;
    private static final boolean CHECK = false;

    // abstraction function:
    // Graphs represent a series of data nodes and the directed edges that
    // connect them. The graph represents the nodes
    // in nodeSet, and each node references its edges itself.
    //
    // Representation Invariant:
    // nodeSet!=null &&
    // all nodes in nodeSet!=null &&
    // no node has an edge with a node not in nodeSet

    /**
     * constructor
     *
     * @effects Constructs a new empty graph
     */
    public Graph() {
        this.nodeMap = new HashMap<T, Set<Edge<T, E>>>();
        this.checkRep();
    }

    /**
     * adds a node to the graph
     *
     * @param node
     *            node to be added
     * @requires s not null
     * @modifies this
     * @effects a new node is added to the graph
     * @return true if node is successfully added (not pre-existing)
     */
    public boolean addNode(T node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        this.checkRep();
        boolean added = false;
        if (!this.nodeMap.containsKey(node)) {
            this.nodeMap.put(node, new HashSet<Edge<T, E>>());
            added = true;
        }
        this.checkRep();
        return added;
    }

    /**
     * removes node from graph
     *
     * @param node
     *            data of the node to be removed
     * @requires data not null
     * @modifies this
     * @effects node with matching data is removed. all edges to node are also
     *          removed
     * @return true if node successfully removed (is in the graph)
     */
    public boolean removeNode(T node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        this.checkRep();

        // break early instead of later if not in graph
        if (!this.nodeMap.containsKey(node)) {
            this.checkRep();
            return false;
        }

        // removes all edges ending at removed node
        for (T t : this.nodeMap.keySet()) {
            Set<Edge<T, E>> edges = this.nodeMap.get(t);
            for (Edge<T, E> e : edges) {
                if (e.getEnd().equals(node)) {
                    edges.remove(e);
                }
            }
        }
        this.nodeMap.remove(node);
        this.checkRep();
        return true;

    }

    /**
     * check if node is in graph
     *
     * @param node
     *            to be checked
     * @requires node not null
     * @return true iff graph contains node
     */
    public boolean containsNode(T node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        this.checkRep();
        boolean result = this.nodeMap.containsKey(node);
        this.checkRep();
        return result;

    }

    /**
     * adds edge to graph
     *
     * @param label
     *            label of edge
     * @param start
     *            start node
     * @param end
     *            end node
     * @requires no args null and both nodes are in graph
     * @modifies start
     * @effects adds a directed edge to start node
     * @return true if successfully added (not pre-existing)
     */
    public boolean addEdge(E label, T start, T end) {
        if (label == null || start == null || end == null) {
            throw new IllegalArgumentException();
        }
        if (!(this.nodeMap.containsKey(start) && this.nodeMap.containsKey(end))) {
            throw new IllegalArgumentException();
        }
        this.checkRep();
        boolean result = this.nodeMap.get(start).add(new Edge<T, E>(label, start, end));
        this.checkRep();
        return result;

    }

    /**
     * removes edge
     *
     * @param label
     *            label of edge to be removed
     * @param start
     *            start node
     * @param end
     *            end
     * @requires no args null and nodes are within graph
     * @modifies start
     * @effects edge with label is removed from start
     * @return true if successfully removed (the edge existed)
     */
    public boolean removeEdge(E label, T start, T end) {
        if (label == null || start == null || end == null) {
            throw new IllegalArgumentException();
        }
        if (!(this.containsNode(start) && this.containsNode(end))) {
            throw new IllegalArgumentException();
        }
        this.checkRep();
        boolean result = this.nodeMap.get(start).remove(new Edge<T, E>(label, start, end));
        this.checkRep();
        return result;

    }

    /**
     * checks if two nodes are adjacent
     *
     * @param start
     *            start node
     * @param end
     *            end node
     * @requires args not null and are in graph
     * @return true iff directed edge from start to end exists
     */
    public boolean isAdjacent(T start, T end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException();
        }
        if (!(this.containsNode(start) && this.containsNode(end))) {
            throw new IllegalArgumentException();
        }
        this.checkRep();
        boolean result = false;
        for (Edge<T, E> e : this.nodeMap.get(start)) {
            if (e.getEnd().equals(end)) {
                result = true;
            }
        }
        this.checkRep();
        return result;

    }

    /**
     * gets children of a node
     *
     * @param node
     *            the node
     * @requires s not null and in graph
     * @return set of children of node
     */
    public Set<T> getChildren(T node) {
        if (node == null || !this.containsNode(node)) {
            throw new IllegalArgumentException();
        }
        this.checkRep();
        Set<T> result = new HashSet<T>();
        for (Edge<T, E> e : this.nodeMap.get(node)) {
            result.add(e.getEnd());
        }
        this.checkRep();
        return result;
    }

    /**
     * gets edges between two nodes
     *
     * @param start
     *            start node
     * @param end
     *            end node
     * @requires both nodes not null and in graph
     * @return set of directed edges from start to end
     */
    public Set<Edge<T, E>> getDirectedEdgesBetween(T start, T end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException();
        }
        if (!(this.containsNode(start) && this.containsNode(end))) {
            throw new IllegalArgumentException();
        }
        this.checkRep();
        Set<Edge<T, E>> result = new HashSet<Edge<T, E>>();
        for (Edge<T, E> e : this.nodeMap.get(start)) {
            if (e.getEnd().equals(end)) {
                result.add(e);
            }
        }
        this.checkRep();
        return result;
    }

    /**
     * gets edges of a node
     *
     * @param node
     *            data of node
     * @requires data not null and matches a node in graph
     * @return set of edges of matching node
     */
    public Set<Edge<T, E>> getEdges(T node) {
        return new HashSet<Edge<T, E>>(this.nodeMap.get(node));
    }

    /**
     * gets all nodes in graph. passed by copy not reference
     *
     * @return set of all nodes in the graph
     */
    public Set<T> getNodes() {
        this.checkRep();
        Set<T> result = new HashSet<T>(this.nodeMap.keySet());
        this.checkRep();
        return result;
    }

    /**
     * size of graph (num nodes)
     *
     * @return size of graph/ number of nodes
     */
    public int size() {
        this.checkRep();
        int result = this.nodeMap.size();
        this.checkRep();
        return result;
    }

    /**
     * checks if graph is empty
     *
     * @return true iff no nodes in graph
     */
    public boolean isEmpty() {
        this.checkRep();
        boolean result = this.nodeMap.isEmpty();
        this.checkRep();
        return result;
    }

    /**
     * checks the representation invariant. NodeSet cannot be null, none of the
     * nodes in nodeSet can be null, and nodes cannot have edges to nodes not in
     * the graph.
     */
    private void checkRep() {
        if (!Graph.CHECK) {
            return;
        }
        if (this.nodeMap == null) {
            throw new RuntimeException("node set cannot be null");
        }
        for (T t : this.nodeMap.keySet()) {
            if (t == null) {
                throw new RuntimeException("nodes cannot be null");
            }
            for (Edge<T, E> e : this.nodeMap.get(t)) {
                if (e == null) {
                    throw new RuntimeException();
                }
                if (!this.nodeMap.containsKey(e.getEnd())) {
                    throw new RuntimeException("nodes cannot be connected to nodes not in the graph");
                }
            }

        }
    }

}
