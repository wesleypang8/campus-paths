package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import graphStructure.Edge;
import graphStructure.Graph;


/**
 * this class is the model for buildings and their paths on campus. It can list
 * all the buildings on campus, find the shortest path between two buildings and
 * find the longform name of an abbreviated building.
 *
 * @author WP
 *
 */
public class CampusGraph {
    // switch for checking rep invariant
    private static final boolean CHECK = false;

    // maps abbreviated building name to long form name
    private final Map<String, String> short2LongName;
    // maps abbrevated building name to its location
    private final Map<String, PointD> name2Loc;

    // Graph with PointD nodes and Double labeled edges
    private final Graph<PointD, Double> cGraph;

    // Abstraction function: this model represents buildings and the paths
    // between them. this.short2LongName maps abbreviated names to their long
    // form, this.name2Loc maps short names to its location, and this.cGraph
    // represents a graph with nodes of points on a coordinate plane connected
    // by edges with labels with the distance between them.

    // Representation Invariant:
    // none of the fields are null,
    // none of the keys or values in either this.short2LongName or this.name2Loc
    // are null

    /**
     * constructor, builds a new CampusGraph with data from files corresponding
     * to the strings passed in.
     *
     * @param buildingFile
     *            name of data file to read for info on buildings. Should be of
     *            short_name, long_name, x_coord, y_coord form, with each token
     *            separated by a tab
     * @param pathFile
     *            name of data file to read for info on paths. Should be of form
     *            x_coord, y_coord, followed by indented lines of the form
     *            x_coord,y_coord then a colon and a space then the distance
     *            between the original and indented coordinates
     * @requires the args buildingFile and pathFile are names of files that
     *           exist
     * @effects builds a new CampusGraph object with data from args
     * @throws MalformedDataException
     *             if the files are not of proper format
     * @throws IllegalArgumentException
     *             if the args are null
     */
    public CampusGraph(String buildingFile, String pathFile) throws MalformedDataException {
        if (buildingFile == null || pathFile == null) {
            throw new IllegalArgumentException();
        }
        this.short2LongName = new HashMap<String, String>();
        this.name2Loc = new HashMap<String, PointD>();
        this.cGraph = new Graph<PointD, Double>();

        CampusParser.parseBuildingData("./src/model/data/" + buildingFile, this.short2LongName, this.name2Loc);
        CampusParser.parsePathData("./src/model/data/" + pathFile, this.cGraph);
        this.checkRep();

    }

    /**
     * returns location corresponding to a building
     * 
     * @param s
     *            the building to find location for (in abbreviated form)
     * @return PointD location for that building
     * @throws IllegalArgumentException
     *             iff arg is null
     */
    public PointD getLocation(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        return name2Loc.get(s);
    }

    /**
     * returns a copy of the buildings in alphabetical order
     *
     * @return a copy of the buildings in alphabetical order
     */
    public Map<String, String> getBuildings() {
        this.checkRep();
        return new TreeMap<String, String>(this.short2LongName);
    }

    /**
     * returns the long form name of a building given an abbreviated name
     *
     * @param shortName
     *            abbreviated name of building
     * @return long form name of the building
     * @throws IllegalArgumentException
     *             if arg is null or if building is not in the model
     */
    public String getLongName(String shortName) {
        this.checkRep();
        if (shortName == null) {
            throw new IllegalArgumentException();
        }
        if (!this.getBuildings().containsKey(shortName)) {
            throw new IllegalArgumentException();
        }
        this.checkRep();
        return this.short2LongName.get(shortName);
    }

    /**
     * find the path between two buildings
     *
     * @param start
     *            building at start of path
     * @param end
     *            building at end of path
     * @return a path from start to end
     * @throws IllegalArgumentException
     *             if args are null or if the building does not exist in the
     *             model (only looks at abbreviated names)
     */
    public List<Edge<PointD, Double>> findPath(String start, String end) {
        this.checkRep();
        if (start == null || end == null) {
            throw new IllegalArgumentException();
        }

        boolean unknown = false;

        if (!this.name2Loc.containsKey(start)) {
            System.out.println("Unknown building: " + start);
            unknown = true;
        }

        if (!this.name2Loc.containsKey(end)) {
            System.out.println("Unknown building: " + end);
            unknown = true;
        }
        if (unknown) {
            System.out.println();
            return null;
        }
        this.checkRep();
        return findWeightedPath(this.cGraph, this.name2Loc.get(start), this.name2Loc.get(end));
    }
    
    /**
     * find shortest path from start node to end node in graph based on edge
     * weights. Picks the least weight, lowest nodes path. Paths are defined as
     * a list of edges
     *
     * @param graph
     *            graph to search through
     * @param start
     *            start node of path
     * @param end
     *            end node of path
     * @return shortest path between nodes (least node path if more than one
     *         with lowest weight)
     * @throws IllegalArgumentException
     *             if any args null or nodes not in graph
     */
    public static <T> List<Edge<T, Double>> findWeightedPath(Graph<T, Double> graph, T start, T end) {
        if (graph == null || start == null || end == null) {
            throw new IllegalArgumentException();
        }
        if (!graph.containsNode(start) || !graph.containsNode(end)) {
            throw new IllegalArgumentException();
        }
        Queue<List<Edge<T, Double>>> activePaths = new PriorityQueue<List<Edge<T, Double>>>(
                new Comparator<List<Edge<T, Double>>>() {
                    @Override
                    public int compare(List<Edge<T, Double>> l1, List<Edge<T, Double>> l2) {
                        double weight1 = 0;
                        double weight2 = 0;
                        for (Edge<T, Double> e : l1) {
                            weight1 += e.getLabel();

                        }
                        for (Edge<T, Double> e : l2) {
                            weight2 += e.getLabel();

                        }
                        int result = Double.compare(weight1, weight2);
                        if (result == 0) {
                            result = Integer.compare(l1.size(), l2.size());
                        }
                        return result;
                    }
                });

        Set<T> foundNodes = new HashSet<T>();
        activePaths.add(new ArrayList<Edge<T, Double>>(Arrays.asList(new Edge<T, Double>(0.0, start, start))));

        while (!activePaths.isEmpty()) {
            List<Edge<T, Double>> minPath = activePaths.remove();
            T minDest = minPath.get(minPath.size() - 1).getEnd();
            if (foundNodes.contains(minDest)) {
                continue;
            }
            if (minDest.equals(end)) {
                minPath.remove(0);
                return minPath;
            }
            for (Edge<T, Double> e : graph.getEdges(minDest)) {
                if (foundNodes.contains(e.getEnd())) {
                    continue;
                }
                List<Edge<T, Double>> newPath = new ArrayList<Edge<T, Double>>(minPath);
                newPath.add(e);
                activePaths.add(newPath);

            }
            foundNodes.add(minDest);
        }
        return null;
    }

    /**
     * checks rep invariant. fields cannot be null, and none of the keys or
     * values in the maps can be null either.
     */
    private void checkRep() {
        if (!CampusGraph.CHECK) {
            return;
        }
        if (this.name2Loc == null || this.cGraph == null || this.short2LongName == null) {
            throw new RuntimeException();
        }
        for (String s : this.short2LongName.keySet()) {
            if (s == null) {
                throw new RuntimeException();
            }
        }
        for (String s : this.short2LongName.values()) {
            if (s == null) {
                throw new RuntimeException();
            }
        }
        for (String s : this.name2Loc.keySet()) {
            if (s == null) {
                throw new RuntimeException();
            }

        }
        for (PointD p : this.name2Loc.values()) {
            if (p == null) {
                throw new RuntimeException();
            }
        }

    }

}
