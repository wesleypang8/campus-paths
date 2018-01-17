package model.test;

import org.junit.Before;
import org.junit.Test;

import model.CampusGraph;
import model.MalformedDataException;

/**
 * this class tests the implementation of CampusGraph
 *
 * @author WP
 *
 */
public class CampusGraphTest {
    private CampusGraph model;
    private static final String UNKNOWN_NAME = "OOG";

    @Before
    public void startUp() throws MalformedDataException {
        this.model = new CampusGraph("campus_buildings.dat", "campus_paths.dat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullConstructor() throws MalformedDataException {
        CampusGraph nullG = new CampusGraph(null, null);
    }

    @Test(expected = MalformedDataException.class)
    public void testBadFormData() throws MalformedDataException {
        CampusGraph swapped = new CampusGraph("campus_paths.dat", "campus_buildings.dat");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadGetName() {
        this.model.getLongName(CampusGraphTest.UNKNOWN_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullGetName() {
        this.model.getLongName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindPathsNull() {
        this.model.findPath(null, null);
    }
}
