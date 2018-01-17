package graphStructure;

/**
 * Edges are immutable directed connections between two nodes and it also
 * carries a label.
 *
 * @author WP
 *
 */
public class Edge<T, E> {
    private E label;
    private final T start;
    private final T end;
    private static final boolean CHECK = false;

    // abstraction function:
    // Edges represent a connection between two nodes, start and end. They are
    // directional, meaning that the connection
    // should only go one way. Edges also have labels to identify them.

    // Representation Invariant:
    // label!=null &&
    // start!=null &&
    // end!= null

    /**
     * constructor, parameters cannot be null
     *
     * @param label
     *            the label for the edge
     * @param start
     *            the starting node
     * @param end
     *            the end node
     * @effects creates a new edge between start and end with label
     */
    public Edge(E label, T start, T end) {
        this.label = label;
        this.start = start;
        this.end = end;
        this.checkRep();
    }

    /**
     * @return the edge's label
     */
    public E getLabel() {
        return this.label;

    }

    /**
     * @return the reference to the start node
     */
    public T getStart() {
        return this.start;

    }

    /**
     * @return the reference to the end node
     */
    public T getEnd() {
        return this.end;

    }

    /**
     * @param label
     *            label to be changed to
     */
    public void setLabel(E label) {
        this.checkRep();
        this.label = label;
        this.checkRep();
    }

    /**
     * standard equals method
     *
     * @param the
     *            other object to compare to
     * @return true iff the object passed has the exact same fields.
     */
    @Override
    public boolean equals(/* @Nullable */ Object obj) {
        this.checkRep();
        if (!(obj instanceof Edge<?, ?>)) {
            return false;
        }
        Edge<?, ?> edge = (Edge<?, ?>) obj;
        return edge.label.equals(this.label) && edge.start.equals(this.start) && edge.end.equals(this.end);

    }

    /**
     * standard hashcode function
     *
     * @return an int all equal edges will also return
     */
    @Override
    public int hashCode() {
        this.checkRep();
        return this.label.hashCode() * 3 + this.start.hashCode() * 7 + this.end.hashCode() * 11;

    }

    /**
     * standard to string method.
     *
     * @return the object in string form with the format "label start end"
     */
    @Override
    public String toString() {
        this.checkRep();
        return this.label + " " + this.start + " " + this.end;
    }

    /**
     * checks the representation invariant. All of the fields must be non-null
     */
    private void checkRep() {
        if (!Edge.CHECK) {
            return;
        }
        if (this.label == null) {
            throw new RuntimeException("label cannot be null");

        }
        if (this.start == null) {
            throw new RuntimeException("start cannot be null");
        }
        if (this.end == null) {
            throw new RuntimeException("end cannot be null");
        }
    }
}
