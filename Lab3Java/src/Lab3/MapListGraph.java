package Lab3;

import java.util.*;

/**
 * Created by Simon Van den Eynde
 */
public class MapListGraph<T> extends AGraph<T> {
    private Map<T, List<T>> adjacencies = new HashMap<>();

    private double meanLocalClustering = 0;
    private List<Integer> degreeSequence;

    public MapListGraph(int nVertices, int nEdges) {
        super(nVertices, nEdges);
    }

    public List<Integer> getDegreeSequence() {
        //if (degreeSequence != null) return degreeSequence;

        List<Integer> degreeSeq = new ArrayList<>();
        for (List<T> neighbours : adjacencies.values()) {
            degreeSeq.add(neighbours.size());
        }
        degreeSeq.sort(Comparator.naturalOrder());
        degreeSequence = degreeSeq;
        return degreeSequence;
    }

    private Map<T,List<T>> sortedMap;

    Comparator<Map.Entry<T, List<T>>> entryComparator = (o1, o2) -> (o1.getValue().size() - o2.getValue().size());

    public Map<T, List<T>> sortByValue(Comparator<Map.Entry<T, List<T>>> entryComparator)
    {
        //if(sortedMap !=null) return sortedMap;
        List<Map.Entry<T, List<T>>> list = new LinkedList<>(  adjacencies.entrySet() );

        Collections.sort( list, entryComparator);

        sortedMap = new LinkedHashMap<>();
        for (Map.Entry<T, List<T>> entry : list)
        {
            sortedMap.put( entry.getKey(), entry.getValue() );
        }
        return sortedMap;
    }

    private Map<T,Set<T>> setAdjacencies;

    private Map<T, Set<T>> setAdjacencies() {
        //if (!changed && setAdjacencies != null) return setAdjacencies;
        setAdjacencies = new HashMap<>();
        for (T vertex : adjacencies.keySet()) {
            Set<T> neighbours = new HashSet<>();
            neighbours.addAll(adjacencies.get(vertex));
            setAdjacencies.put(vertex, neighbours);
        }
        return setAdjacencies;
    }

    public double meanLocalClustering() {
        //if (meanLocalClustering > 0) return meanLocalClustering;
        //adjacencies=sortByValue(entryComparator);

        Map<T, Set<T>> tSetMap = setAdjacencies();
        for (List<T> neighbours : adjacencies.values()) {

            int connectedNeighbours = 0;
            int neighbourPairs = 0;
            if (neighbours.size() < 2) continue;

            List<Set<T>> neighboursOfNeighbours = new ArrayList<>();
            for(int i=0; i<neighbours.size(); i++) {
                neighboursOfNeighbours.add(tSetMap.get(neighbours.get(i)));
            }

            for (int i = 0; i < neighbours.size() - 1; i++) {
                for (int j = i + 1; j < neighbours.size(); j++) {
                    if (neighboursOfNeighbours.get(i).contains(neighbours.get(j))) connectedNeighbours++;
                    neighbourPairs++;
                }

            }
            meanLocalClustering += (double) connectedNeighbours / neighbourPairs;
        }
        return meanLocalClustering/getnVertices();
    }

    /*public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map)
    {
        List<Map.Entry<K, V>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return ( o1.getValue() ).compareTo( o2.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }*/



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
            adjacencies.put(vertex, neighbours);
            edges.add(new Edge(vertex,neighbour));
        } else {
            if (!neighV.contains(neighbour)) {
                neighV.add(neighbour);
                edges.add(new Edge(vertex,neighbour));
            }
        }
    }

    @Override
    public List<T> getNeighbours(T vertex) {
        List<T> neighbours = adjacencies.get(vertex);
        if (neighbours == null) return new ArrayList<>();
        return neighbours;
    }

    public List<T> vertexList(){
        List<T> vertexList = new ArrayList<>();
        vertexList.addAll(adjacencies.keySet());
        return vertexList;
    }

    public void removeEdge(Edge e){
        removeDirectedEdge(e);
        removeDirectedEdge(new Edge(e.getEnd(),e.getStart()));
    }

    public void removeDirectedEdge(Edge e){
        edges.remove(e);
        adjacencies.get(e.getStart()).remove(e.getEnd());
    }
}



