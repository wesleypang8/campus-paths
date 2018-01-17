package model;

/**
 * A data structure that represents a point on a 2d coordinate plane. Can also
 * calculate the relative direction between two PointD objects.
 *
 * @author WP
 *
 */
public class PointD {
    private static final double oneEighthPi = Math.PI / 8;
    private static final double threeEighthsPi = 3.0 / 8.0 * Math.PI;
    private static final double fiveEighthsPi = 5.0 / 8.0 * Math.PI;
    private static final double sevenEighthsPi = 7.0 / 8.0 * Math.PI;
    private static final double negOneEighthPi = -Math.PI / 8;
    private static final double negThreeEighthsPi = -3.0 / 8.0 * Math.PI;
    private static final double negFiveEighthsPi = -5.0 / 8.0 * Math.PI;
    private static final double negSevenEighthsPi = -7.0 / 8.0 * Math.PI;

    private final double x;
    private final double y;

    // abstraction function: PointD objects represent points in a 2d plane with
    // this.x = point.x_coord and this.y = point.y_coord

    // no rep invariant needed.

    /**
     * Constructor
     *
     * @param x
     *            x coord
     * @param y
     *            y coord
     * @effects creates a new PointD object with coordinates x and y
     */
    public PointD(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getter for x coord
     *
     * @return x
     */
    public double getX() {
        return this.x;
    }

    /**
     * getter for y coord
     *
     * @return y
     */
    public double getY() {
        return this.y;
    }

    /**
     * returns the direction of another PointD relative to this. There are eight
     * compass directions (N, NE, E, SE, S, SW, W, and NW). If a point lies on a
     * border between sectors, the single letter direction is chosen. The
     * coordinate plane being used has directions based on the directions pixels
     * increase. A PointD's relative direction to itself is always "E".
     *
     * @param other
     *            the other PointD to compare to
     * @return the relative direction between two PointD objects
     * @throws IllegalArgumentException
     *             iff other is null.
     */
    public String relativeDirection(PointD other) {
        if (other == null) {
            throw new IllegalArgumentException();
        }
        double rad = Math.atan2((other.y - this.y), (other.x - this.x));
        if (rad >= PointD.sevenEighthsPi) {
            return "W";
        } else if (rad > PointD.fiveEighthsPi) {
            return "SW";
        } else if (rad >= PointD.threeEighthsPi) {
            return "S";
        } else if (rad > PointD.oneEighthPi) {
            return "SE";
        } else if (rad >= PointD.negOneEighthPi) {
            return "E";
        } else if (rad > PointD.negThreeEighthsPi) {
            return "NE";
        } else if (rad >= PointD.negFiveEighthsPi) {
            return "N";
        } else if (rad > PointD.negSevenEighthsPi) {
            return "NW";
        } else {
            return "W";
        }
    }

    @Override
    public String toString() {
        return "x: " + this.x + "; y: " + this.y;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PointD && ((PointD) o).x == (this.x) && ((PointD) o).y == (this.y);
    }

    @Override
    public int hashCode() {
        return 13 * (this.x + "").hashCode() + (this.y + "").hashCode();
    }
}
