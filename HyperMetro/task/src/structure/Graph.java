package structure;

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;

public class Graph {
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;
    private int resultDistance = 0;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void add(String from, String to, int cost) {
        Edge temp = findEdge(from, to);
        if (temp != null) {
            temp.cost = cost;
        }
        else {
            Edge e = new Edge(from, to, cost);
            edges.add(e);
        }
    }

    private Vertex findVertex(String v) {
        for (Vertex each : vertices) {
            if (each.value.equals(v))
                return each;
        }
        return null;
    }

    private Edge findEdge(Vertex v1, Vertex v2) {
        for (Edge each : edges) {
            if (each.from.equals(v1) && each.to.equals(v2)) {
                return each;
            }
        }
        return null;
    }

    private Edge findEdge(String from, String to) {
        for (Edge each : edges) {
            if (each.from.value.equals(from) && each.to.value.equals(to)) {
                return each;
            }
        }
        return null;
    }
    private boolean Dijkstra(String v1) {
        resetDistances();
        Vertex source = findVertex(v1);
        source.minDistance = 0;

        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(source);

        while (!pq.isEmpty()) {
            Vertex u = pq.poll();
            for (Vertex v : u.outgoing) {
                Edge e = findEdge(u, v);
                if (e==null) {
                    return false;
                }
                int totalDistance = u.minDistance + e.cost;
                if (totalDistance < v.minDistance) {
                    pq.remove(v);
                    v.minDistance = totalDistance;

                    v.previous = u;
                    pq.add(v);
                }
            }
        }


        return true;
    }


    private List<String> getShortestPath(Vertex target) {
        List<String> path = new ArrayList<String>();

        if (target.minDistance==Integer.MAX_VALUE) {
            path.add("No path found");
            return path;
        }

        int neededDistance = -1;

        for (Vertex v = target; v != null; v = v.previous) {
            path.add(v.value);
            if (neededDistance == -1) {
                neededDistance = v.minDistance;
            }
        }

        resultDistance = neededDistance;

        Collections.reverse(path);
        return path;
    }

    public int getResultDistance() {
        return resultDistance;
    }

    private void resetDistances() {
        for (Vertex each : vertices) {
            each.minDistance = Integer.MAX_VALUE;
            each.previous = null;
        }
    }

    public List<String> getPath(String from, String to) {

        if (Dijkstra(from) == false) {
            return null;
        }

        List<String> path = getShortestPath(findVertex(to));
        return path;
    }

    class Vertex implements Comparable<Vertex> {
        String value;

        Vertex previous = null;
        int minDistance = Integer.MAX_VALUE;

        List<Vertex> incoming;
        List<Vertex> outgoing;

        public Vertex(String value) {
            this.value = value;
            incoming = new ArrayList<>();
            outgoing = new ArrayList<>();
        }

        @Override
        public int compareTo(Vertex other)
        {
            return Integer.compare(minDistance, other.minDistance);
        }

        public void addIncoming(Vertex vert)
        {
            incoming.add(vert);
        }

        public void addOutgoing(Vertex vert)
        {
            outgoing.add(vert);
        }
    }

    class Edge {
        Vertex from;
        Vertex to;
        int cost;

        public Edge(String v1, String v2, int cost) {
            from = findVertex(v1);
            if (from == null)
            {
                from = new Vertex(v1);
                vertices.add(from);
            }
            to = findVertex(v2);
            if (to == null)
            {
                to = new Vertex(v2);
                vertices.add(to);
            }
            this.cost = cost;

            from.addOutgoing(to);
            to.addIncoming(from);
        }

        @Override
        public String toString()
        {
            return "Edge From: " + from.value + " to: " + to.value + " cost: " + cost;
        }
    }

    public String edgesToString()
    {
        String retval = "";
        for (Edge each : edges)
        {
            retval += each + "\n";
        }
        return retval;
    }
}