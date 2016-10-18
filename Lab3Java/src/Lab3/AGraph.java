package Lab3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Simon Van den Eynde
 */
public abstract class AGraph<T> {
    protected int nVertices;
    protected int nEdges;

    private double meanDegree;
    private double networkDensity;

    protected List<Edge<T>> edges = new ArrayList<>();

    public AGraph(int nVertices, int nEdges) {
        this.nVertices = nVertices;
        this.nEdges = nEdges;
        meanDegree = (double) 2 * nEdges / nVertices;
        networkDensity = (double) 2 * nEdges / (nVertices * (nVertices - 1));
    }

    public int getnVertices() {
        return nVertices;
    }

    public int getnEdges() {
        return nEdges;
    }

    public double getMeanDegree() {
        return meanDegree;
    }

    public double getNetworkDensity() {
        return networkDensity;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    abstract void addEdge(T vertex, T neighbour);

    abstract Collection<T> getNeighbours(T vertex);

    abstract List<Integer> getDegreeSequence();

    abstract double meanLocalClustering();

    abstract public List<T> vertexList();

    abstract void removeEdge(Edge e);

}
