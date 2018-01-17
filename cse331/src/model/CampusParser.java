package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import graphStructure.Graph;

/**
 * this class parses and loads data from given files
 *
 * @author WP
 *
 */
public class CampusParser {

    /**
     * Reads building data. Each line of the data file should contain a
     * shortname, longname, x_coord, and y_coord separated by tabs.
     *
     * @param filename
     *            file to read from
     * @param short2LongName
     *            map in which abbreviated names will be mapped to long form
     *            names. Pre-existing data is overriden.
     * @param name2Loc
     *            map in which abbreviated names will be mapped to a PointD
     *            location. Pre-existing data is overriden.
     * @requires filename exists and has an existing corresponding file
     * @modifies short2LongName, name2Loc
     * @effects short2LongName is filled with abbreviated names mapped to long
     *          form
     * @effects name2loc is filled with abbreviated names mapped to PointD
     *          locations
     * @throws MalformedDataException
     *             if file is not of proper form
     */
    public static void parseBuildingData(String filename, Map<String, String> short2LongName,
            Map<String, PointD> name2Loc) throws MalformedDataException {
        if (filename == null || short2LongName == null || name2Loc == null) {
            throw new IllegalArgumentException();
        }
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filename));

            // erase pre-existing data
            short2LongName.clear();
            name2Loc.clear();

            // Construct the collections of characters and books, one
            // <character, book> pair at a time.
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                // Ignore comment lines.
                if (inputLine.startsWith("#")) {
                    continue;
                }

                // Parse the data, throwing an exception for malformed lines.
                String[] tokens = inputLine.split("\t");
                if (tokens.length != 4) {
                    System.out.println(inputLine);
                    throw new MalformedDataException("Line should have 4 tab separated tokens: " + inputLine);
                }

                String shortName = tokens[0];
                String longName = tokens[1];

                Double x = Double.parseDouble(tokens[2]);
                Double y = Double.parseDouble(tokens[3]);

                short2LongName.put(shortName, longName);
                name2Loc.put(shortName, new PointD(x, y));

            }
        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e.toString());
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    /**
     * Reads path data. Each line of the data file should contain a coordinate
     * pair followed by indented lines containing a coordinate pair then a colon
     * and space then a distance between the two coordinate pairs
     *
     * @param filename
     *            file to read from
     * @param campusGraph
     *            graph with PointD nodes connected by edges with Double labels.
     *            Pre-existing data is overriden.
     * @requires filename exists and has an existing corresponding file
     * @modifies campusGraph
     * @effects campusGraph is filled with data. All coordinates are added as
     *          PointD nodes and the corresponding edges with distance labels
     *          are also added.
     * @throws MalformedDataException
     *             if file is not of proper form
     */
    public static void parsePathData(String filename, Graph<PointD, Double> campusGraph) throws MalformedDataException {
        if (filename == null || campusGraph == null) {
            throw new IllegalArgumentException();
        }
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filename));

            // erase pre-existing data
            for (PointD p : campusGraph.getNodes()) {
                campusGraph.removeNode(p);
            }

            // Construct the collections of characters and books, one
            // <character, book> pair at a time.
            String inputLine;
            PointD startNode = null;
            while ((inputLine = reader.readLine()) != null) {
                // Ignore comment lines.
                if (inputLine.startsWith("#")) {
                    continue;
                }
                inputLine = inputLine.replace("\t", "");

                // Parse the data, throwing an exception for malformed lines.
                String[] tokens = inputLine.split(": ");
                String[] coordTokens = tokens[0].split(",");
                if (tokens.length != 1 && tokens.length != 2) {
                    System.out.println(inputLine);
                    throw new MalformedDataException("Line should contain exactly one tab: " + inputLine);
                } else if (coordTokens.length != 2) {
                    System.out.println(inputLine);
                    throw new MalformedDataException(
                            "Line should contain a comma separated coordinate pair: " + inputLine);
                }

                if (tokens.length == 1) {
                    startNode = new PointD(Double.parseDouble(coordTokens[0]), Double.parseDouble(coordTokens[1]));

                    campusGraph.addNode(startNode);
                } else if (tokens.length == 2) {
                    if (startNode == null) {
                        throw new MalformedDataException("file must start with an initial point");
                    }
                    PointD endNode = new PointD(Double.parseDouble(coordTokens[0]), Double.parseDouble(coordTokens[1]));
                    campusGraph.addNode(endNode);

                    campusGraph.addEdge(Double.parseDouble(tokens[1]), startNode, endNode);
                }
            }
        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e.toString());
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}
