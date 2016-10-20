package Lab3;

/**
 * Created by Simon Van den Eynde
 */
public class Edge<T> {
    private T start;
    private T end;

    public Edge(T start, T end) {
        this.start = start;
        this.end = end;
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "{" +start + " "+ end + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge<?> edge = (Edge<?>) o;

        if (start.equals(edge.start) && end.equals(edge.end)) return true;
        if(start.equals(edge.end) && end.equals(edge.start)) return true;
        return false;

    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }
}
