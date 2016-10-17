package Lab3;

import java.util.*;

/**
 * Created by Simon Van den Eynde
 */
public class Graph<T> {
    int vertices;
    int edges;
    private double meanDegree;
    private double networkDensity;
    Map<T, Set<T>> adjacencies;
    private Map<T,List<T>> listAdjacencies;

    private double meanLocalClustering=0;
    private List<Integer> degreeSequence;

    public Graph(int vertices, int edges, Map<T, Set<T>> adjacencies) {
        this.vertices = vertices;
        this.edges = edges;
        this.adjacencies = adjacencies;
        meanDegree = (double)2*edges/vertices;
        networkDensity = (double)2*edges/(vertices*(vertices-1));
    }

    public List<Integer> degreeSequence(){
        if(degreeSequence!=null) return degreeSequence;

        List<Integer> degreeSeq = new ArrayList<>();
        for (T vertex : adjacencies.keySet()) {
            degreeSeq.add(adjacencies.get(vertex).size());
        }
        degreeSeq.sort(Comparator.naturalOrder());
        degreeSequence=degreeSeq;
        return degreeSequence;
    }

    public String summaryDegreeSequences(){

        return String.format("%6d %6d %5.1f %8.1e", vertices,edges,meanDegree,networkDensity);
    }

    private Map<T, List<T>> listAdjacencies(){
        if(listAdjacencies!=null) return listAdjacencies;
        listAdjacencies = new HashMap<>();
        for(T vertex : adjacencies.keySet()){
            List<T> neighbours = new ArrayList<>();
            neighbours.addAll(adjacencies.get(vertex));
            listAdjacencies.put(vertex,neighbours);
        }
        return listAdjacencies;
    }

    public double meanLocalClustering(){
        if(meanLocalClustering>0) return meanLocalClustering;
        for(List<T> neighbours : listAdjacencies().values()){
            int connectedNeighbours=0;
            int neighbourPairs=0;
            if(neighbours.size()<2) continue;
            for(int i=0; i<neighbours.size()-1; i++){
                for(int j=i+1; j<neighbours.size(); j++){
                    if(adjacencies.get(neighbours.get(i)).contains(neighbours.get(j))) connectedNeighbours++;
                    neighbourPairs++;
                }
            }
            meanLocalClustering+=(double)connectedNeighbours/neighbourPairs;
        }
        return meanLocalClustering;
    }


    @Override
    public String toString() {
        return "Lab3.Graph{" +
                "vertices=" + vertices +
                ", edges=" + edges +
                ", adjacencies=" + adjacencies +
                '}';
    }
}

