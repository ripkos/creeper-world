package game.lib.graph;

import resource.Sprite;

import java.util.List;

public class Graph {
    private final List<Sprite> vertexes;
    private final List<Edge> edges;

    public Graph(List<Sprite> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<Sprite> getSprites() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }



}