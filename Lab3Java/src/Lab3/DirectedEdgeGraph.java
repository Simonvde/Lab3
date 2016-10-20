package Lab3;

import java.util.*;

/*
 * Created by Simon Van den Eynde
 */

public class DirectedEdgeGraph<T> extends AGraph<T> {
    private Map<T, List<T>> adjacencies = new HashMap<>();

    private double meanLocalClustering = 0;
    private List<Integer> degreeSequence;

    public DirectedEdgeGraph(int nVertices, int nEdges) {
        super(nVertices, nEdges);
        edges = new HashSet<>();
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
                    Set<T> ts = neighboursOfNeighbours.get(i);
                    Set<T> ts1 = neighboursOfNeighbours.get(j);
                    if(ts==null && ts1==null){
                        neighbourPairs++;
                        continue;
                    }
                    if(ts!=null && ts.contains(neighbours.get(j))){
                        connectedNeighbours++;
                    } else if(ts1 != null && ts1.contains(neighbours.get(i))){
                        connectedNeighbours++;
                    }
                    neighbourPairs++;
                }

            }
            meanLocalClustering += (double) connectedNeighbours / neighbourPairs;
        }
        return meanLocalClustering/getnVertices();
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
        edges.add(new Edge<>(vertex,neighbour));
        addDirectedEdge(vertex,neighbour);
        addDirectedEdge(neighbour,vertex);
    }

    public void addEdgeSpecial(T vertex, T neighbour){
        addDirectedEdgeSpecial(vertex,neighbour);
        addDirectedEdgeSpecial(neighbour,vertex);
    }
    public void removeEdgeSpecial(Edge e){
        adjacencies.get(e.getStart()).remove(e.getEnd());
        adjacencies.get(e.getEnd()).remove(e.getStart());
    }

    /*public void buildAdjacencies(){
        adjacencies.clear();
        for(Edge<T> e : edges) {
            addDirectedEdge(e.getStart(),e.getEnd());
            addDirectedEdge(e.getEnd(),e.getStart());
        }
    }*/

    private void addDirectedEdgeSpecial(T vertex, T neighbour) {
        List<T> neighV = adjacencies.get(vertex);
        neighV.add(neighbour);
    }

    private void addDirectedEdge(T vertex, T neighbour) {
        List<T> neighV = adjacencies.get(vertex);
        if (neighV == null) {
            List<T> neighbours = new ArrayList<>();
            neighbours.add(neighbour);
            adjacencies.put(vertex, neighbours);
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

    public List<T> vertexList(){
        List<T> vertexList = new ArrayList<>();
        vertexList.addAll(adjacencies.keySet());
        return vertexList;
    }

    public void removeEdge(Edge e){
        edges.remove(e);
        adjacencies.get(e.getStart()).remove(e.getEnd());
        adjacencies.get(e.getEnd()).remove(e.getStart());
    }

}
