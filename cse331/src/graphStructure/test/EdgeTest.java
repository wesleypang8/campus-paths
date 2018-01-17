package graphStructure.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import graphStructure.Edge;

public class EdgeTest {

    String me, parent, friend;
    Edge<String, String> p2m, m2f, f2m;

    @Before
    public void setUp() {
        this.me = "me";
        this.parent = "parent";
        this.friend = "friend";

        this.p2m = new Edge<String, String>("p2m", this.parent, this.me);
        this.m2f = new Edge<String, String>("m2f", this.me, this.friend);
        this.f2m = new Edge<String, String>("f2m", this.friend, this.me);
    }

    @Test
    public void testGetLabel() {
        Assert.assertEquals("p2m", this.p2m.getLabel());
    }

    @Test
    public void testGetStart() {
        Assert.assertEquals(this.me, this.m2f.getStart());
    }

    @Test
    public void testGetEnd() {
        Assert.assertEquals(this.me, this.f2m.getEnd());
    }

    @Test
    public void testEquals() {
        Object other = new Edge<String, String>("p2m", this.parent, this.me);
        Assert.assertTrue(this.p2m.equals(other));
    }
}
