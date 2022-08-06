package game.lib.graph;

import resource.Sprite;

public class Edge  {
    private final String id;
    private final Sprite source;
    private final Sprite destination;
    private final int weight;

    public Edge(String id, Sprite source, Sprite destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }
    public Sprite getDestination() {
        return destination;
    }

    public Sprite getSource() {
        return source;
    }
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " " + destination;
    }


}
