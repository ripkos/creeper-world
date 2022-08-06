package game.lib.graph;

import resource.Sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class Alg {

    private final List<Sprite> nodes;
    private final List<Edge> edges;
    private Set<Sprite> settledNodes;
    private Set<Sprite> unSettledNodes;
    private Map<Sprite, Sprite> predecessors;
    private Map<Sprite, Integer> distance;

    public Alg(Graph graph) {
        // Create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Sprite>(
                graph.getSprites()
                 );
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }

    public void execute(Sprite source) {
        settledNodes = new HashSet<Sprite>();
        unSettledNodes = new HashSet<Sprite>();
        distance = new HashMap<Sprite, Integer>();
        predecessors = new HashMap<Sprite, Sprite>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Sprite node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Sprite node) {
        List<Sprite> adjacentNodes = getNeighbors(node);
        for (Sprite target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Sprite node, Sprite target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Sprite> getNeighbors(Sprite node) {
        List<Sprite> neighbors = new ArrayList<Sprite>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Sprite getMinimum(Set<Sprite> Sprites) {
        Sprite minimum = null;
        for (Sprite Sprite : Sprites) {
            if (minimum == null) {
                minimum = Sprite;
            } else {
                if (getShortestDistance(Sprite) < getShortestDistance(minimum)) {
                    minimum = Sprite;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Sprite Sprite) {
        return settledNodes.contains(Sprite);
    }

    private int getShortestDistance(Sprite destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Sprite> getPath(Sprite target) {
        LinkedList<Sprite> path = new LinkedList<Sprite>();
        Sprite step = target;
        // Check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}