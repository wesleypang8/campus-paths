package model.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.PointD;

/**
 * this class tests the implementation of the PointD class
 *
 * @author WP
 *
 */
public class PointDTest {
    public static final double epsilon = 0.000000001;
    private static final double TAN_PI_EIGHT = Math.tan(Math.PI / 8);

    private PointD p1, zeroP;

    @Before
    public void startUp() {
        this.p1 = new PointD(2, 3);
        this.zeroP = new PointD(0, 0);

    }

    @Test
    public void testGetX() {
        Assert.assertEquals(2.0, this.p1.getX(), PointDTest.epsilon);
    }

    @Test
    public void testGetY() {
        Assert.assertEquals(3.0, this.p1.getY(), PointDTest.epsilon);
    }

    @Test
    public void testEquals() {
        Assert.assertEquals(this.p1, new PointD(2, 3));
    }

    @Test
    public void testDirection() {
        Assert.assertEquals("SE", this.zeroP.relativeDirection(this.p1));
    }

    @Test
    public void testDirectionBorder() {
        PointD border = new PointD(1, PointDTest.TAN_PI_EIGHT);
        PointD underBorder = new PointD(1, PointDTest.TAN_PI_EIGHT + PointDTest.epsilon);
        Assert.assertEquals("E", this.zeroP.relativeDirection(border));
        Assert.assertEquals("SE", this.zeroP.relativeDirection(underBorder));

    }

    @Test
    public void testDirectionEdge() {
        PointD piRad = new PointD(1, 3);
        Assert.assertEquals("W", this.p1.relativeDirection(piRad));
    }

    @Test
    public void testDirectionSelf() {
        Assert.assertEquals("E", this.zeroP.relativeDirection(this.zeroP));
    }

}
