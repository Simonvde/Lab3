/*
package Lab3;

import java.util.*;

*/
/**
 * Created by Simon Van den Eynde
 *//*

public class ArrayGraph<T> extends AGraph<T> {
    List<Vertex> adjacencies = new ArrayList<>();

    public ArrayGraph(int nVertices, int nEdges) {
        super(nVertices, nEdges);
    }

    @Override
    void addEdge(T vertex, T neighbour) {
        int index = Collections.binarySearch(adjacencies, vertex);
        if(index<0){
            List<T> neighbours = new ArrayList<>();
            neighbours.add(neighbour);
            adjacencies[-i]=vertex, neighbours);
        }
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
    Collection<T> getNeighbours(T vertex) {
        return null;
    }

    @Override
    List<Integer> getDegreeSequence() {
        return null;
    }

    @Override
    double meanLocalClustering() {
        return 0;
    }

    private class Vertex<T>{
        T vertex;
        Set<T> neighbourSet = new HashSet<>();
        List<T> neighbourList = new ArrayList<>();

        public Vertex(T vertex) {
            this.vertex = vertex;
        }
        
        public void addNeighbour(T neighbour){
            neighbourSet.add(neighbour);
            neighbourList.add(neighbour);
        }
    }
}
*/
