package Lab3;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Simon Van den Eynde
 */
public class GraphFactory {
    public AGraph<Integer> generateErdosRenyiGraph(int vertices, int edges) {
        AGraph<Integer> graph = new MapListGraph<>(vertices, edges);
        int i = 0;
        while (i < edges) {
            int rand1 = ThreadLocalRandom.current().nextInt(0, vertices);
            int rand2 = ThreadLocalRandom.current().nextInt(0, vertices);
            Collection<Integer> neighbours = graph.getNeighbours(rand1);
            if (!neighbours.contains(rand2)) {
                graph.addEdge(rand1, rand2);
                i++;
            }
        }
        return graph;
    }

    public AGraph<String> generateSwitchingGraph(AGraph<String> graph, int Q) {
        List<Integer> degreeSequence = graph.getDegreeSequence();
        int counter=0;
        int failures = 0;
        List<Edge<String>> edges = graph.getEdges();
        for (int i = 0; i < Q * graph.getnEdges(); i++) {
            if(++counter%100==0) System.out.println(counter + " " + failures);


            int rand1 = ThreadLocalRandom.current().nextInt(0, graph.getnEdges());
            int rand2 = ThreadLocalRandom.current().nextInt(0, graph.getnEdges());

            Edge<String> e1 = edges.get(rand1);
            Edge<String> e2 = edges.get(rand2);

            List<String> vertices = new ArrayList<>();
            vertices.add(e1.getStart());
            vertices.add(e1.getEnd());
            vertices.add(e2.getStart());
            vertices.add(e2.getEnd());


            Set<String> s = new HashSet<>(vertices);
            if (s.size() != vertices.size()) {
                failures++;
                continue;
            }

            if (graph.getNeighbours(vertices.get(0)).contains(vertices.get(3)) ||
                    graph.getNeighbours(vertices.get(1)).contains(vertices.get(2))) {
                failures++;
                continue;
            }

            graph.removeEdge(e1);
            graph.removeEdge(e2);

            graph.addEdge(vertices.get(0), vertices.get(3));
            graph.addEdge(vertices.get(1), vertices.get(2));

            /*edges.remove(e1);
            edges.remove(e2);
            edges.remove(new Edge(e1.getEnd(),e1.getStart()));
            edges.remove(new Edge(e1.getStart(),e1.getEnd()));
            edges.add(new Edge(vertices.get(0),vertices.get(3)));
            edges.add(new Edge(vertices.get(1),vertices.get(2)));
            edges.add(new Edge(vertices.get(3),vertices.get(0)));
            edges.add(new Edge(vertices.get(2),vertices.get(1)));*/

        }


        System.out.println(Q * graph.getnEdges() + " " + failures);
        return graph;
    }

}
