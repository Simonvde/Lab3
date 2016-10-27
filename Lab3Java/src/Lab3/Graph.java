package Lab3;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Created by Simon Van den Eynde
 */

//T should be a primitive type!! (especially: Copy should be a deep copy).
public class Graph<T extends Comparable<T>> {
    public Map<T, Set<T>> adjacencies = new LinkedHashMap<>();

    private Set<Edge<T>> edges;

    private Graph() {
        edges = new HashSet<>();
    }

    public Graph(Set<Edge<T>> edges) {
        this.edges = edges;
        for (Edge<T> e : edges) {
            addEdge(e.getStart(), e.getEnd());
        }
    }

    private Graph(Set<Edge<T>> edges, Map<T, Set<T>> adjacencies) {
        this.edges = new HashSet<>(edges);
        this.adjacencies = new LinkedHashMap<>();
        for (Map.Entry<T, Set<T>> entries : adjacencies.entrySet()) {
            this.adjacencies.put(entries.getKey(), new HashSet<>(entries.getValue()));
        }
    }

    public int getnVertices() {
        return adjacencies.size();
    }

    public int getnEdges() {
        return edges.size();
    }

    public double getMeanDegree() {
        return (double) 2 * getnEdges() / getnVertices();
    }

    public double getNetworkDensity() {
        return (double) 2 * getnEdges() / (getnVertices() * (getnVertices() - 1));
    }

    public Set<Edge<T>> getEdges() {
        return edges;
    }

    public List<Integer> getDegreeSequence() {
        List<Integer> degreeSeq = new ArrayList<>();
        for (Collection<T> neighbours : adjacencies.values()) {
            degreeSeq.add(neighbours.size());
        }
        //degreeSeq.sort(Comparator.naturalOrder());
        return degreeSeq;
    }

    Comparator<Map.Entry<T, Set<T>>> increasingDegree = Comparator.comparingInt((Map.Entry<T, Set<T>> o1) -> o1.getValue().size());
    Comparator<Map.Entry<T, Set<T>>> decreasingDegree = Comparator.comparingInt((Map.Entry<T, Set<T>> o1) -> o1.getValue().size()).reversed();
    Comparator<Map.Entry<T, Set<T>>> inputSort = Comparator.comparing((Map.Entry<T, Set<T>> o1) -> o1.getKey());

    //Random comparator based on Hashcode.
    private Comparator<Map.Entry<T, Set<T>>> randomSort() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int x = r.nextInt(), y = r.nextInt(), z = r.nextInt();
        return Comparator.comparingInt((Map.Entry<T,Set<T>> s)->s.getKey().hashCode()^x)
                .thenComparingInt(s->s.getValue().hashCode()^y)
                .thenComparing(s->s.getValue().size()^z);
    }

    private Map<T, Set<T>> sortByValue(Comparator<Map.Entry<T, Set<T>>> entryComparator) {
        //if(sortedMap !=null) return sortedMap;
        List<Map.Entry<T, Set<T>>> list = new LinkedList<>(adjacencies.entrySet());

        Collections.sort(list, entryComparator);

        Map<T, Set<T>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<T, Set<T>> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Graph<T> sortByIncreasingDegree() {
        Graph<T> graph = new Graph(edges, sortByValue(increasingDegree));
        return graph;
    }

    public Graph<T> sortByDecreasingDegree() {
        Graph<T> graph = new Graph(edges, sortByValue(decreasingDegree));
        return graph;
    }
    public Graph<T> sortByInput() {
        Graph<T> graph = new Graph(edges, sortByValue(inputSort));
        return graph;
    }
    public Graph<T> sortRandom() {
        Graph<T> graph = new Graph(edges, sortByValue(randomSort()));
        return graph;
    }

    public double meanLocalClustering() {
        double meanLocalClustering = 0;
        for (Set<T> neighbourSet : adjacencies.values()) {
            if (neighbourSet.size() < 2) continue;

            meanLocalClustering += getVertexContribution(neighbourSet);
        }
        return meanLocalClustering / getnVertices();
    }

    /*
    @Return true if the mean local clustering of this graph is greater than the given clustering.
     */
    public boolean meanLocalClustering(double hypothesisClustering) {
        double meanLocalClustering = 0;
        int nVert = getnVertices();
        int verticesLeft = nVert;

        for (Set<T> neighbourSet : adjacencies.values()) {
            if (neighbourSet.size() < 2) continue;

            double vertexContribution = getVertexContribution(neighbourSet);
            meanLocalClustering += vertexContribution;

            verticesLeft--;

            if (meanLocalClustering / nVert > hypothesisClustering) {
                return true;
            }
            if ((meanLocalClustering + verticesLeft) / nVert < hypothesisClustering) {
                return false;
            }
        }
        return meanLocalClustering/nVert > hypothesisClustering;
    }

    private double getVertexContribution(Set<T> neighbourSet) {
        List<T> neighbours = new ArrayList<>(neighbourSet);

        int connectedNeighbours = 0;
        int neighbourPairs = 0;

        List<Set<T>> neighboursOfNeighbours = new ArrayList<>();
        for (int i = 0; i < neighbours.size() - 1; i++)
            neighboursOfNeighbours.add(adjacencies.get(neighbours.get(i)));

        for (int i = 0; i < neighbours.size() - 1; i++) {
            for (int j = i + 1; j < neighbours.size(); j++) {
                if (neighboursOfNeighbours.get(i).contains(neighbours.get(j))) connectedNeighbours++;
                neighbourPairs++;
            }
        }

        return (double) connectedNeighbours / neighbourPairs;
    }

    @Override
    public String toString() {
        return "Lab3.Graph{" +
                "vertices=" + getnVertices() +
                ", edges=" + getnEdges() +
                ", adjacencies=" + adjacencies +
                '}';
    }

    private void addEdge(T vertex, T neighbour) {
        addDirectedEdge(vertex, neighbour);
        addDirectedEdge(neighbour, vertex);
    }

    private void addDirectedEdge(T vertex, T neighbour) {
        Set<T> neighV = adjacencies.get(vertex);
        if (neighV == null) {
            Set<T> neighbours = new HashSet<>();
            neighbours.add(neighbour);
            adjacencies.put(vertex, neighbours);
        } else {
            if (!neighV.contains(neighbour)) {
                neighV.add(neighbour);
            }
        }
    }

    public Set<T> getNeighbours(T vertex) {
        Set<T> neighbours = adjacencies.get(vertex);
        if (neighbours == null) return new HashSet<>();
        return neighbours;
    }

    public Graph<T> generateSwitchingGraph(int Q) {
        Graph<T> switchedGraph = new Graph<>(edges, adjacencies);

        int failures = 0;
        List<Edge<T>> switchedEdges = new ArrayList<>();
        switchedEdges.addAll(switchedGraph.getEdges());


        for (int i = 0; i < Q * switchedGraph.getnEdges(); i++) {

            int direction1 = ThreadLocalRandom.current().nextInt(0, 2);
            int direction2 = ThreadLocalRandom.current().nextInt(0, 2);
            int rand1 = ThreadLocalRandom.current().nextInt(0, switchedGraph.getnEdges());
            int rand2 = ThreadLocalRandom.current().nextInt(0, switchedGraph.getnEdges());

            Edge<T> e1 = switchedEdges.get(rand1);
            Edge<T> e2 = switchedEdges.get(rand2);


            List<T> vertices = new ArrayList<>();
            selectDirection(vertices, direction1, e1);
            selectDirection(vertices, direction2, e2);


            Set<T> s = new HashSet<>(vertices);
            if (s.size() != vertices.size()) {
                if(s.size()==3 && (vertices.get(0)==vertices.get(2) || vertices.get(1)==vertices.get(3))){}
                else {
                    failures++;
                    continue;
                }
            }

            Set<T> neighbours0 = switchedGraph.getNeighbours(vertices.get(0));
            if (neighbours0.contains(vertices.get(3))) {
                failures++;
                continue;
            }

            Set<T> neighbours1 = switchedGraph.getNeighbours(vertices.get(1));
            if (neighbours1.contains(vertices.get(2))) {
                failures++;
                continue;
            }


            Set<T> neighbours2 = switchedGraph.getNeighbours(vertices.get(2));
            Set<T> neighbours3 = switchedGraph.getNeighbours(vertices.get(3));

            neighbours0.remove(vertices.get(1));
            neighbours1.remove(vertices.get(0));
            neighbours2.remove(vertices.get(3));
            neighbours3.remove(vertices.get(2));


            neighbours0.add(vertices.get(3));
            neighbours3.add(vertices.get(0));
            neighbours1.add(vertices.get(2));
            neighbours2.add(vertices.get(1));


            switchedEdges.set(rand1, new Edge<>(vertices.get(0), vertices.get(3)));
            switchedEdges.set(rand2, new Edge<>(vertices.get(1), vertices.get(2)));
        }

        //System.out.println(Q * switchedGraph.getnEdges() + " " + failures);
        return switchedGraph;
    }

    private void selectDirection(List<T> vertices, int direction, Edge<T> e) {
        if (direction == 0) {
            vertices.add(e.getStart());
            vertices.add(e.getEnd());
        } else {
            vertices.add(e.getEnd());
            vertices.add(e.getStart());
        }
    }

    public Graph<Integer> generateErdosRenyiGraph() {
        Graph<Integer> graph = new Graph<>();
        int i = 0;
        while (i < getnEdges()) {
            int rand1 = ThreadLocalRandom.current().nextInt(0, getnVertices());
            int rand2 = ThreadLocalRandom.current().nextInt(0, getnVertices());
            Set<Integer> neighbours = graph.getNeighbours(rand1);
            if (!neighbours.contains(rand2)) {
                graph.addEdge(rand1, rand2);
                i++;
            }
        }
        return graph;
    }


}
