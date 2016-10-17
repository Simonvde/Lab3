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

        Graph<String> g = readM("data/Turkish_syntactic_dependency_network.txt");
        Graph<Integer> erdosRenyi = generateErdosRenyiGraph(g.vertices, g.edges);
        System.out.println(g.summaryDegreeSequences());
        System.out.println(erdosRenyi.summaryDegreeSequences());
        System.out.println(g.meanLocalClustering());
        System.out.println(erdosRenyi.meanLocalClustering());
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

    public Graph<Integer> generateSwitchingGraph(Graph<String> graph, int Q) {

        return null;
    }

    public void readLanguages() {
        for(int i=0; i<languages.length; i++) {
            Graph<String> graph = readM("data/" + languages[i] + "_syntactic_dependency_network.txt");
            System.out.format("%10s %s%n",languages[i],graph.summaryDegreeSequences());
        }
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
