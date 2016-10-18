/*
package Lab3;

import java.util.*;

*/
/**
 * Created by Simon Van den Eynde
 *//*

public class DegreeGraph<T> extends AGraph<T> {

    private Map<Vertex<T>, List<T>> adjacencies = new TreeMap<>();

    private double meanLocalClustering = 0;
    private List<Integer> degreeSequence;

    public DegreeGraph(int nVertices, int nEdges) {
        super(nVertices, nEdges);
    }

    public List<Integer> getDegreeSequence() {
        if (degreeSequence != null) return degreeSequence;

        List<Integer> degreeSeq = new ArrayList<>();
        for (List<T> neighbours : adjacencies.values()) {
            degreeSeq.add(neighbours.size());
        }
        degreeSeq.sort(Comparator.naturalOrder());
        degreeSequence = degreeSeq;
        return degreeSequence;
    }

    private Map<T, Set<T>> setAdjacencies;

    private Map<T, Set<T>> setAdjacencies() {
        if (setAdjacencies != null) return setAdjacencies;
        setAdjacencies = new HashMap<>();
        for (Vertex<T> vertex : adjacencies.keySet()) {
            Set<T> neighbours = new HashSet<>();
            neighbours.addAll(adjacencies.get(vertex));
            setAdjacencies.put(vertex.getVertex(), neighbours);
        }
        return setAdjacencies;
    }

    public double meanLocalClustering() {
        if (meanLocalClustering > 0) return meanLocalClustering;
        for (List<T> neighbours : adjacencies.values()) {

            int connectedNeighbours = 0;
            int neighbourPairs = 0;
            if (neighbours.size() < 2) continue;

            List<Set<T>> neighboursOfNeighbours = new ArrayList<>();
            for (int i = 0; i < neighbours.size(); i++)
                neighboursOfNeighbours.add(setAdjacencies().get(neighbours.get(i)));

            for (int i = 0; i < neighbours.size() - 1; i++) {
                for (int j = i + 1; j < neighbours.size(); j++) {
                    if (neighboursOfNeighbours.get(i).contains(neighbours.get(j))) connectedNeighbours++;
                    neighbourPairs++;
                }
            }
            meanLocalClustering += (double) connectedNeighbours / neighbourPairs;
        }
        return meanLocalClustering;
    }

    @Override
    public List<T> vertexList() {
        return null;
    }


    class ColorComparator implements Comparator<T> {
        public int compare(T c1, T c2) {
            return adjacencies.get(c1).size() - (adjacencies.get(c2).size());
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }


    @Override
    public String toString() {
        return "Lab3.Graph{" +
                "vertices=" + nVertices +
                ", edges=" + nEdges +
                ", adjacencies=" + adjacencies +
                '}';
    }

    @Override
    public void addEdge(T vertex, T neighbour) {
        addDirectedEdge(vertex, neighbour);
        addDirectedEdge(neighbour, vertex);
    }

    private void addDirectedEdge(T vertex, T neighbour) {
        List<T> neighV = adjacencies.get(vertex);
        if (neighV == null) {
            List<T> neighbours = new ArrayList<>();
            neighbours.add(neighbour);
            adjacencies.put(new Vertex<>(vertex,1), neighbours);
        } else {
            if (!neighV.contains(neighbour)) {
                neighV.add(neighbour);
            }
        }
    }

    @Override
    public List<T> getNeighbours(T vertex) {
        List<T> neighbours = adjacencies.get(vertex);
        if (neighbours == null) return new ArrayList<>();
        return neighbours;
    }

    private class Vertex<T> {
        private T vertex;
        private int degree;

        public Vertex(T vertex, int degree) {
            this.vertex = vertex;
            this.degree = degree;
        }

        public void incrementDegree() {
            degree++;
        }

        public T getVertex() {
            return vertex;
        }

        public int getDegree() {
            return degree;
        }
    }

}
*/
