package graphStructure.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import graphStructure.Edge;
import graphStructure.Graph;

/**
 * This class implements a testing driver which reads test scripts from files
 * for testing Graph.
 **/
@SuppressWarnings("null")
public class TestDriver {

    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                TestDriver.printUsage();
                return;
            }

            TestDriver td;

            if (args.length == 0) {
                td = new TestDriver(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File(fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new TestDriver(new FileReader(tests), new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    TestDriver.printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw5.test.HW5TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw5.test.HW5TestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    // TODO for the student: Parameterize the next line correctly.
    protected final Map<String, Graph<String, String>> graphs = new HashMap<String, Graph<String, String>>();
    protected final PrintWriter output;
    protected final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW5TestDriver which reads command from <tt>r</tt>
     *          and writes results to <tt>w</tt>.
     **/
    public TestDriver(Reader r, Writer w) {
        this.input = new BufferedReader(r);
        this.output = new PrintWriter(w);
    }

    /**
     * @effects Executes the commands read from the input and writes results to
     *          the output
     * @throws IOException
     *             if the input or output sources encounter an IOException
     **/
    public void runTests() throws IOException {
        String inputLine;
        while ((inputLine = this.input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) || (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                this.output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    this.executeCommand(command, arguments);
                }
            }
            this.output.flush();
        }
    }

    protected void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("CreateGraph")) {
                this.createGraph(arguments);
            } else if (command.equals("AddNode")) {
                this.addNode(arguments);
            } else if (command.equals("AddEdge")) {
                this.addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                this.listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                this.listChildren(arguments);
            } else {
                this.output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            this.output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        this.createGraph(graphName);
    }

    private void createGraph(String graphName) {
        // Insert your code here.

        this.graphs.put(graphName, new Graph<String, String>());
        this.output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        this.addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        // Insert your code here.

        Graph<String, String> graph = this.graphs.get(graphName);
        graph.addNode((nodeName));
        this.output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        this.addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName, String edgeLabel) {
        // Insert your code here.

        Graph<String, String> graph = this.graphs.get(graphName);
        graph.addEdge(edgeLabel, parentName, childName);
        this.output
                .println("added edge " + edgeLabel + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        this.listNodes(graphName);
    }

    private void listNodes(String graphName) {
        // Insert your code here.

        Graph<String, String> graph = this.graphs.get(graphName);

        List<String> nodes = new ArrayList<String>();
        this.output.print(graphName + " contains:");
        for (String n : graph.getNodes()) {
            nodes.add(" " + n);
        }
        Collections.sort(nodes);
        for (String s : nodes) {
            this.output.print(s);
        }
        this.output.println();
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        this.listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        // Insert your code here.

        Graph<String, String> graph = this.graphs.get(graphName);
        this.output.print("the children of " + parentName + " in " + graphName + " are:");

        List<String> childrenList = new ArrayList<String>();
        for (Edge<String, String> e : graph.getEdges(parentName)) {
            childrenList.add(" " + e.getEnd() + "(" + e.getLabel() + ")");// add
                                                                          // edge
                                                                          // name
        }
        Collections.sort(childrenList);
        for (String s : childrenList) {
            this.output.print(s);
        }
        this.output.println();
        // System.out.println("the children of "+parentName+" in "+graphName+"
        // are:"+children);

    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    protected static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
