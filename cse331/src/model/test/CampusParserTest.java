package model.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import graphStructure.Graph;
import model.CampusParser;
import model.MalformedDataException;
import model.PointD;

/**
 * this class tests the implementation of the CampusParser class
 *
 * @author WP
 *
 */
public class CampusParserTest {

    private Map<String, String> short2LongName;
    private Map<String, PointD> name2Loc;
    private Graph<PointD, Double> campusGraph;

    @Before
    public void startUp() throws MalformedDataException {
        this.short2LongName = new HashMap<String, String>();
        this.name2Loc = new HashMap<String, PointD>();
        this.campusGraph = new Graph<PointD, Double>();
        // model = new CampusGraph("campus_buildings.dat","campus_paths.dat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArgsBuildings() throws MalformedDataException {
        CampusParser.parseBuildingData(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArgsPaths() throws MalformedDataException {
        CampusParser.parsePathData(null, null);
    }

    @Test(expected = MalformedDataException.class)
    public void testBadFormatBuildings() throws MalformedDataException {
        CampusParser.parseBuildingData("./src/hw8/data/" + "campus_paths.dat", this.short2LongName, this.name2Loc);
    }

    @Test(expected = MalformedDataException.class)
    public void testBadFormatPaths() throws MalformedDataException {
        CampusParser.parsePathData("./src/hw8/data/" + "campus_buildings.dat", this.campusGraph);
    }

    @Test
    public void testNonEmptyMapsBuildings() throws MalformedDataException {
        this.short2LongName.put("OOG", "unknown new building");
        CampusParser.parseBuildingData("./src/hw8/data/" + "campus_buildings.dat", this.short2LongName, this.name2Loc);
        Assert.assertFalse(this.short2LongName.containsKey("OOG"));
    }

    @Test
    public void testNonEmptyMapsPaths() throws MalformedDataException {
        PointD newPoint = new PointD(1234.5678, 9876.5432);
        this.campusGraph.addNode(newPoint);
        CampusParser.parsePathData("./src/hw8/data/" + "campus_paths.dat", this.campusGraph);
        Assert.assertFalse(this.campusGraph.containsNode(newPoint));
    }
}
