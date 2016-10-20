package Lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    String[] languages = new String[]{"Arabic", "Basque", "Catalan", "Chinese", "Czech", "English",
            "Greek", "Hungarian", "Italian", "Turkish"};

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }


    public void run() {
        //readLanguages();
        //timerTestMLC();

        Timer timer= new Timer();
        timer.start();

        /*AGraph<String> graph = readG("data/Basque_syntactic_dependency_network.txt");
        System.out.println("MLC" +  graph.meanLocalClustering());
        //System.out.println("Edges " + graph.getEdges().size() + " " + graph.getEdges());
        List<Integer> degSeq = graph.getDegreeSequence();
        System.out.println(degSeq);

        GraphFactory factory = new GraphFactory();
        factory.generateSwitchingGraph(graph, 3);
        System.out.println("T" + timer.delta()); timer.start();
        System.out.println(degSeq.equals(graph.getDegreeSequence()));
        System.out.println(graph.getDegreeSequence());
        System.out.println("MLC" + graph.meanLocalClustering());*/


        timer.start();
        AGraph<String> dGraph = readD("data/Turkish_syntactic_dependency_network.txt");
        System.out.println("T" + timer.delta()); timer.start();
        System.out.println("*MLC" + dGraph.meanLocalClustering());
        System.out.println("T" + timer.delta()); timer.start();
        List<Integer> degreeSequence = dGraph.getDegreeSequence();
        //System.out.println("Edges " + dGraph.getEdges().size() + " " + dGraph.getEdges());

        DirectedGraphFactory dFac = new DirectedGraphFactory();
        dFac.generateSwitchingGraph(dGraph,8);
        System.out.println("T" + timer.delta()); timer.start();
        System.out.println(degreeSequence.equals(dGraph.getDegreeSequence()));

        System.out.println("MLC" + dGraph.meanLocalClustering());


    }

    private void timerTestMLC(){
        Timer timer= new Timer();
        timer.start();
        Graph<String> g = readM("data/English_syntactic_dependency_network.txt");
        System.out.println("*" + timer.delta()); timer.start();
        Graph<Integer> erdosRenyi = generateErdosRenyiGraph(g.vertices, g.edges);
        System.out.println("*" + timer.delta()); timer.start();
        System.out.println(g.meanLocalClustering());
        System.out.println("*" + timer.delta()); timer.start();
        System.out.println(erdosRenyi.meanLocalClustering());

        System.out.println(timer.delta());

        /*System.out.println(g.summaryDegreeSequences());
        System.out.println(erdosRenyi.summaryDegreeSequences());
        System.out.println(g.meanLocalClustering());
        System.out.println(erdosRenyi.meanLocalClustering());*/



        timer.start();

        AGraph<String> graph = readG("data/English_syntactic_dependency_network.txt");
        System.out.println("*" + timer.delta()); timer.start();
        System.out.println(graph.meanLocalClustering());
        System.out.println("*" + timer.delta()); timer.start();

        System.out.println(timer.delta());
        GraphFactory graphFactory = new GraphFactory();
        AGraph<Integer> erdosRenyiG = graphFactory.generateErdosRenyiGraph(g.vertices, g.edges);
        System.out.println(erdosRenyiG.meanLocalClustering());

        System.out.println("*" + timer.delta());

        System.out.println(sum(g.degreeSequence()));
        System.out.println(sum(graph.getDegreeSequence()));
    }

    private int sum(List<Integer> l){
        int sum=0;
        for(int i : l) sum+= i;
        return sum;
    }

    public Graph<Integer> generateErdosRenyiGraph(int vertices, int edges) {
        Map<Integer, Set<Integer>> adjacencies = new HashMap<>();

        for (int i = 0; i < vertices; i++) adjacencies.put(i, new HashSet<>());
        int i = 0;
        while (i < edges) {
            int rand1 = ThreadLocalRandom.current().nextInt(0, vertices);
            int rand2 = ThreadLocalRandom.current().nextInt(0, vertices);
            Set<Integer> neighbours = adjacencies.get(rand1);
            if (!neighbours.contains(rand2)) {
                neighbours.add(rand2);
                adjacencies.get(rand2).add(rand1);
                i++;
            }
        }
        return new Graph<>(vertices, edges, adjacencies);
    }


    public void readLanguages() {
        for (int i = 0; i < languages.length; i++) {
            Graph<String> graph = readM("data/" + languages[i] + "_syntactic_dependency_network.txt");
            System.out.format("%10s %s%n", languages[i], graph.summaryDegreeSequences());
        }
    }

    public AGraph<String> readG(String filename) {
        AGraph<String> graph;
        int nVertices, nEdges;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            String[] numbers = line.split("\\s+");
            nVertices = Integer.parseInt(numbers[0]);
            nEdges = Integer.parseInt(numbers[1]);

            graph = new MapGraph<>(nVertices, nEdges);


            line = br.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                graph.addEdge(words[0], words[1]);
                line = br.readLine();
            }

        } catch (IOException io) {
            System.out.println("IOException");
            throw (new RuntimeException(io));
        }

        return graph;
    }

    public AGraph<String> readD(String filename) {
        AGraph<String> graph;
        int nVertices, nEdges;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            String[] numbers = line.split("\\s+");
            nVertices = Integer.parseInt(numbers[0]);
            nEdges = Integer.parseInt(numbers[1]);

            graph = new DirectedEdgeGraph<>(nVertices, nEdges);


            line = br.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                graph.addEdge(words[0], words[1]);
                line = br.readLine();
            }

        } catch (IOException io) {
            System.out.println("IOException");
            throw (new RuntimeException(io));
        }

        return graph;
    }


    public Graph<String> readM(String filename) {
        int nVertices, nEdges;
        Map<String, Set<String>> edgelist = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();

            String[] numbers = line.split("\\s+");
            nVertices = Integer.parseInt(numbers[0]);
            nEdges = Integer.parseInt(numbers[1]);


            int j = 0;
            String lastWord = "abc";
            line = br.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");

                if (j == 0) {
                    Set<String> edgesAtVertex = new HashSet<>();
                    edgesAtVertex.add(words[1]);
                    edgelist.put(words[0], edgesAtVertex);
                    j++;
                } else if (!words[0].equals(lastWord)) {
                    ++j;
                    Set<String> edgesAtVertex = new HashSet<>();
                    edgesAtVertex.add(words[1]);
                    edgelist.put(words[0], edgesAtVertex);
                } else {
                    edgelist.get(words[0]).add(words[1]);
                }
                lastWord = words[0];

                line = br.readLine();
            }

        } catch (IOException io) {
            System.out.println("IOException");
            throw (new RuntimeException(io));
        }


        Set<String> tempSet = new HashSet<>();
        tempSet.addAll(edgelist.keySet());
        for (String vertex : tempSet) {
            for (String neighbour : edgelist.get(vertex)) {
                if (edgelist.containsKey(neighbour)) {
                    edgelist.get(neighbour).add(vertex);
                } else {
                    Set<String> edgesAtVertex = new HashSet<>();
                    edgesAtVertex.add(vertex);
                    edgelist.put(neighbour, edgesAtVertex);
                }
            }
        }

        return new Graph<>(nVertices, nEdges, edgelist);
    }


    /************* TRY OUTS, NOT IMPORTANT **********/

    /*public void read(String filename) {
        ArrayList<VertexAdjacencies> edgelist = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();

            String[] numbers = line.split("\\s+");
            int nVertices = Integer.parseInt(numbers[0]);
            int nEdges = Integer.parseInt(numbers[1]);


            int j = 0;
            String lastWord = "abc";
            line = br.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");

                if (j == 0) {
                    ArrayList<String> edgesAtVertex = new ArrayList<>();
                    edgesAtVertex.add(words[1]);
                    edgelist.add(new VertexAdjacencies(words[0], edgesAtVertex));
                    j++;
                } else if (!words[0].equals(lastWord)) {
                    ++j;
                    ArrayList<String> edgesAtVertex = new ArrayList<>();
                    edgesAtVertex.add(words[1]);
                    edgelist.add(new VertexAdjacencies(words[0], edgesAtVertex));
                } else {
                    edgelist.get(j-1).getNeighbours().add(words[1]);
                }
                lastWord = words[0];

                line = br.readLine();
            }

            for(int i=0; i<edgelist.size(); i++){
                System.out.println(edgelist.get(i));
            }

        } catch (IOException io) {
            System.out.println("IOException");
            throw (new RuntimeException(io));
        }
    }
    private class VertexAdjacencies {
        String vertex;
        ArrayList<String> neighbours;

        public String getVertex() {
            return vertex;
        }

        public ArrayList<String> getNeighbours() {
            return neighbours;
        }

        public VertexAdjacencies(String vertex, ArrayList<String> neighbours) {

            this.vertex = vertex;
            this.neighbours = neighbours;
        }

        @Override
        public String toString() {
            return "VertexAdjacencies{" +
                    "vertex='" + vertex + '\'' +
                    ", neighbours=" + neighbours +
                    '}';
        }
    }*/
}
