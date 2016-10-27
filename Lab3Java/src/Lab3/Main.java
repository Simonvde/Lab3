package Lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {

    String[] languages = new String[]{"Arabic", "Basque", "Catalan", "Chinese", "Czech", "English",
            "Greek", "Hungarian", "Italian", "Turkish"};

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {
        readLanguages();

        nonOptimizedClustering(20,10);

        optimizedClustering(30,15);

        speedTestSorting();
    }

    public void speedTestSorting(){

        Timer timer = new Timer();

        int repetitions=5;
        System.out.format("%10s %6s %6s %6s %6s %6s%n","languages","normal","random","increasingDegree","input","decreasingDegree");
        for (String language : languages) {
            Graph<String> graph = readGraph("data/" + language + "_syntactic_dependency_network.txt");

            double[] times = new double[repetitions];

            //Run program a few times in advance to make measurements equal.
            for(int i=0; i<3; i++) mlcCheck(graph,1,1);

            for(int i=0; i<repetitions; i++) {

                timer.end();
                timer.start();
                mlcCheck(graph, 1, 1);
                times[0] += timer.delta();
                timer.start();

                mlcCheck(graph.sortRandom(), 1, 1);
                times[1] += timer.delta();
                timer.start();

                mlcCheck(graph.sortByIncreasingDegree(), 1, 1);
                times[2] += timer.delta();
                timer.start();

                mlcCheck(graph.sortByInput(), 1, 1);
                times[3] += timer.delta();
                timer.start();

                mlcCheck(graph.sortByDecreasingDegree(), 1, 1);
                times[4] += timer.delta();
                timer.start();
            }

            for(int i=0; i<repetitions; i++) times[i] /=repetitions;


            System.out.format("%10s & %4.2f & %4.2f & %4.2f & %4.2f & %4.2f%n", language, times[0],times[1],times[2],times[3],times[4]);
        }
    }

    public void optimizedClustering(int T, int Q){
        Timer timer = new Timer();
        timer.start();

        for (String language : languages) {
            Graph<String> graph = readGraph("data/" + language + "_syntactic_dependency_network.txt");
            double[] result = mlcCheck(graph.sortByIncreasingDegree(), T, Q);

            System.out.format("%s & %4.2f & %6.4f & %6.4f%n", language, result[0], result[1], result[2]);
            /*System.out.format("%10s %6d %6d %5.1f %8.1e%n%n", language, graph.getnVertices(), graph.getnEdges(),
                    graph.getMeanDegree(), graph.getNetworkDensity());*/
        }

        System.out.format("T %5.3f", timer.delta());
    }

    private double[] mlcCheckNonOptim(Graph<String> graph, int T, int Q) {
        double currentMlc = graph.meanLocalClustering();
        double nullHypothesisER = 0;
        double nullHypothesisSwitching = 0;

        Timer t = new Timer();
        t.end();
        t.start();
        double timeCounter = 0;
        for (int i = 0; i < T; i++) {
            Graph<Integer> integerGraph = graph.generateErdosRenyiGraph();
            nullHypothesisER += integerGraph.meanLocalClustering();
            nullHypothesisSwitching += graph.generateSwitchingGraph(Q).meanLocalClustering();
            double delta = t.delta();
            timeCounter += delta;
            //System.out.print(" " + i + "||" + delta + " ");
            t.end();
            t.start();
        }
        nullHypothesisER /= T;
        nullHypothesisSwitching /= T;
       // System.out.println(" TOT||" + timeCounter);

        return new double[]{currentMlc, nullHypothesisER, nullHypothesisSwitching};
    }

    public void nonOptimizedClustering(int T, int Q){
        Timer timer = new Timer();
        timer.start();

        for (String language : languages) {
            Graph<String> graph = readGraph("data/" + language + "_syntactic_dependency_network.txt");
            double[] result = mlcCheckNonOptim(graph, T, Q);

            System.out.format("%10s: %8.6f NHer: %6.4f NHswitching: %6.4f%n", language, result[0], result[1], result[2]);
            /*System.out.format("%10s %6d %6d %5.1f %8.1e%n%n", language, graph.getnVertices(), graph.getnEdges(),
                    graph.getMeanDegree(), graph.getNetworkDensity());*/
        }

        System.out.format("T %5.3f", timer.delta());
    }

    private double[] mlcCheck(Graph<String> graph, int T, int Q) {
        double currentMlc = graph.meanLocalClustering();
        double nullHypothesisER = 0;
        double nullHypothesisSwitching = 0;

        Timer t = new Timer();
        t.end();
        t.start();
        double timeCounter = 0;
        for (int i = 0; i < T; i++) {
            Graph<Integer> integerGraph = graph.generateErdosRenyiGraph();
            nullHypothesisER += (integerGraph.meanLocalClustering(currentMlc)?1:0);
            nullHypothesisSwitching += (graph.generateSwitchingGraph(Q).meanLocalClustering(currentMlc)?1:0);
            double delta = t.delta();
            timeCounter += delta;
            //System.out.print(" " + i + "||" + delta + " ");
            t.end();
            t.start();
        }
        nullHypothesisER /= T;
        nullHypothesisSwitching /= T;
        // System.out.println(" TOT||" + timeCounter);

        return new double[]{currentMlc, nullHypothesisER, nullHypothesisSwitching};
    }


    public void readLanguages() {
        for (String language : languages) {
            Graph<String> graph = readGraph("data/" + language + "_syntactic_dependency_network.txt");
            System.out.format("%10s %6d %6d %5.1f %8.1e%n", language, graph.getnVertices(), graph.getnEdges(),
                    graph.getMeanDegree(), graph.getNetworkDensity());
        }
    }

    public Graph<String> readGraph(String filename) {
        Set<Edge<String>> edges = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine();

            String line = br.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                edges.add(new Edge<>(words[0], words[1]));
                line = br.readLine();
            }

        } catch (IOException io) {
            System.out.println("IOException");
            throw (new RuntimeException(io));
        }

        return new Graph<>(edges);
    }

}