package q4;


public class Edge {
    Vertex start;
    Vertex end;
    Integer weight;

    public Edge(Vertex start, Vertex end, Integer weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}
