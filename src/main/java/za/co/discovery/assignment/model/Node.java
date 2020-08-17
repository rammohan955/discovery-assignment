package za.co.discovery.assignment.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *  This model class represents a connection of one {@link za.co.discovery.assignment.dao.entity.Planet} with its immediate neighbours.
 */

@Getter
@Setter
@EqualsAndHashCode(of={"name"})
public class Node {

    private String name;

    private List<Node> shortestPath = new LinkedList<>();

    private Double distance = Double.MAX_VALUE;

    private Map<Node, Double> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, Double distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(String name) {
        this.name = name;
    }
}
