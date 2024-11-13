package q4;

import java.util.ArrayList;

public class Vertex {
    int data;
    ArrayList<Edge> edges;

    public Vertex(int data) {
        this.data = data;
        edges = new ArrayList<>();
    }

    // This function is still added when the edge already exist, fix this later
    public void addEdge(Vertex end, int weight) {
        this.edges.add(new Edge(this, end, weight));
    }

    public void removeEdge(Vertex end) {
        // This is close to the filter method in JavaScript
        this.edges.removeIf(edge -> edge.end.equals(end));
    }

}
