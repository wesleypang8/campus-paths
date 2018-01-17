package model;

import java.util.List;
import java.util.Scanner;

import graphStructure.Edge;

public class View {

    // view/controller, not an ADT

    /**
     * view/controller. allows client to find path between two buildings, list
     * all the buildings in the model, or see the menu
     *
     * @param args
     *            command line args
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CampusGraph model = null;
        try {
            model = new CampusGraph("campus_buildings.dat", "campus_paths.dat");
        } catch (MalformedDataException e) {
            e.printStackTrace();
        }
        View.printMenu();

        while (true) {
            System.out.print("Enter an option ('m' to see the menu): ");

            String inputLine = sc.nextLine();
            while ((inputLine.trim().length() == 0) || (inputLine.charAt(0) == '#')) {
                System.out.println(inputLine);
                inputLine = sc.nextLine();
            }
            if (inputLine.equals("b")) {
                View.printBuildings(model);
            } else if (inputLine.equals("r")) {
                View.printPath(model, sc);
            } else if (inputLine.equals("q")) {
                return;
            } else if (inputLine.equals("m")) {
                View.printMenu();
            } else {
                System.out.println("Unknown option");
                System.out.println();
            }
        }
    }

    /**
     * prints path given a CampusGraph model and a scanner with additional args
     *
     * @param model
     *            model of campus
     * @param sc
     *            scanner listening for args
     */
    private static void printPath(CampusGraph model, Scanner sc) {
        System.out.print("Abbreviated name of starting building: ");
        String start = sc.nextLine();
        System.out.print("Abbreviated name of ending building: ");
        String end = sc.nextLine();

        List<Edge<PointD, Double>> path = model.findPath(start, end);
        if (path == null) {
            return;
        }
        System.out.println("Path from " + model.getLongName(start) + " to " + model.getLongName(end) + ":");
        double dist = 0;

        for (Edge<PointD, Double> e : path) {
            PointD startP = e.getStart();
            PointD endP = e.getEnd();
            double edge = e.getLabel();
            dist += edge;
            System.out.println('\t' + "Walk " + Math.round(edge) + " feet " + startP.relativeDirection(endP) + " to ("
                    + Math.round(endP.getX()) + ", " + Math.round(endP.getY()) + ")");
        }
        System.out.println("Total distance: " + Math.round(dist) + " feet");
        System.out.println();

    }

    /**
     * prints the menu
     */
    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println('\t' + "r to find a route");
        System.out.println('\t' + "b to see a list of all buildings");
        System.out.println('\t' + "q to quit");
        System.out.println();
    }

    /**
     * prints all buildings in model in form shortform then a colon and space
     * then longform
     *
     * @param model
     *            model of campus
     */
    private static void printBuildings(CampusGraph model) {
        System.out.println("Buildings:");
        for (String s : model.getBuildings().keySet()) {
            System.out.println('\t' + s + ": " + model.getBuildings().get(s));
        }
        System.out.println();
    }
}
