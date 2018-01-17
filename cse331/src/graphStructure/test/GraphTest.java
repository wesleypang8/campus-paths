package graphStructure.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import graphStructure.Graph;

public class GraphTest {

    Graph<String, String> g1, emptyG;
    Graph<Integer, Integer> intGraph;
    String me, parent, friend;
    int one, two, three;

    @Before
    public void setUp() {
        this.g1 = new Graph<String, String>();
        this.emptyG = new Graph<String, String>();
        this.intGraph = new Graph<Integer, Integer>();

        this.me = "me";
        this.parent = "parent";
        this.friend = "friend";

        this.one = 1;
        this.two = 2;
        this.three = 3;

        this.g1.addNode(this.me);
        this.g1.addNode(this.parent);
        this.g1.addNode(this.friend);

        this.intGraph.addNode(this.one);
        this.intGraph.addNode(this.two);
        this.intGraph.addNode(this.three);

        this.g1.addEdge("me2friend", "me", "friend");
        this.g1.addEdge("friend2me", "friend", "me");
        this.g1.addEdge("parent2me", "parent", "me");

        this.intGraph.addEdge(2, this.one, this.two);
        this.intGraph.addEdge(6, this.two, this.three);
        this.intGraph.addEdge(6, this.three, this.two);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNodeNull() {
        this.g1.addNode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContainsNodeNull() {
        this.g1.containsNode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeNull() {
        this.g1.addEdge(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeOutside() {
        this.g1.addEdge("newEdge", "teacher", this.me);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeNull() {
        this.g1.removeEdge(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsAdjacentNull() {
        this.g1.isAdjacent(this.me, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsAdjacentOutside() {
        String teacher = "teacher";
        this.g1.addEdge("me2teacher", this.me, teacher);
        this.g1.isAdjacent(this.me, teacher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetChildrenNull() {
        this.g1.getChildren(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetChildrenOutside() {
        String teacher = ("teacher");
        this.g1.getChildren(teacher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDirectedEdgesBetweenNull() {
        this.g1.getDirectedEdgesBetween(this.me, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDirectedEdgesBetweenOutside() {
        String teacher = ("teacher");
        this.g1.addEdge("me2teacher", this.me, teacher);
        this.g1.getDirectedEdgesBetween(this.me, teacher);
    }

    @Test(expected = RuntimeException.class)
    public void testAddNode() {
        String newN = ("new");
        this.g1.addNode(newN);
        Assert.assertTrue(this.g1.containsNode(newN));
        this.g1.addNode(null);
    }

    @Test
    public void testRemoveNode() {
        this.g1.removeNode("me");
        Assert.assertFalse(this.g1.containsNode(this.me));
        Assert.assertFalse(this.g1.removeNode("notANode"));

        this.intGraph.removeNode(this.one);
        Assert.assertFalse(this.intGraph.containsNode(this.one));
    }

    @Test
    public void testContainsNode() {
        Assert.assertTrue(this.g1.containsNode(this.me));
        Assert.assertTrue(this.intGraph.containsNode(this.one));
    }

    @Test
    public void testAddEdge() {
        this.g1.addEdge("me2parent", ("me"), ("parent"));
        Assert.assertTrue(this.g1.isAdjacent(("me"), ("parent")));

        this.intGraph.addEdge(3, this.three, this.one);
        Assert.assertTrue(this.intGraph.isAdjacent(this.three, this.one));
    }

    @Test
    public void testAddEdgeString() {
        this.g1.addEdge("me2parent", "me", "parent");
        Assert.assertTrue(this.g1.isAdjacent(("me"), ("parent")));
    }

    @Test
    public void testRemoveEdge() {
        this.g1.removeEdge("me2friend", this.me, this.friend);
        Assert.assertFalse(this.g1.isAdjacent(this.me, this.friend));
    }

    @Test
    public void testIsAdjacent() {
        Assert.assertTrue(this.g1.isAdjacent(this.me, this.friend));
    }

    @Test
    public void testGetChildren() {
        this.g1.addEdge("parent2friend", this.parent, this.friend);
        Assert.assertTrue(this.g1.getChildren(this.parent).contains(this.friend));
        Assert.assertTrue(this.g1.getChildren(this.parent).contains(this.me));
    }

    @Test
    public void testGetChildrenString() {
        this.g1.addEdge("parent2friend", this.parent, this.friend);
        Assert.assertTrue(this.g1.getChildren("parent").contains(this.friend));
        Assert.assertTrue(this.g1.getChildren("parent").contains(this.me));
    }

    @Test
    public void testGetDirectedEdgesBetween() {
        this.g1.addEdge("newEdge", this.me, this.friend);
        Assert.assertEquals(2, this.g1.getDirectedEdgesBetween(this.me, this.friend).size());
    }

    @Test
    public void testGetNodes() {
        Assert.assertTrue(this.g1.getNodes().contains(this.me));
        Assert.assertTrue(this.g1.getNodes().contains(("parent")));
        Assert.assertFalse(this.g1.getNodes().contains(("teacher")));

        Assert.assertTrue(this.intGraph.getNodes().contains(this.one));
    }

    @Test
    public void testSize() {
        Assert.assertEquals(3, this.g1.size());
    }

    @Test
    public void testIsEmpty() {
        Assert.assertFalse(this.g1.isEmpty());
        Assert.assertTrue(this.emptyG.isEmpty());
    }

    @Test
    public void testAddEdgeToSelf() {
        this.g1.addEdge("me2me", this.me, this.me);
        Assert.assertTrue(this.g1.isAdjacent(this.me, this.me));
    }

    @Test(expected = RuntimeException.class)
    public void testAddNodeWithOutsideEdge() {
        String teacher = ("teacher");
        this.g1.addEdge("teacher2student", teacher, ("student"));
        this.g1.addNode(teacher);
    }
}
