package za.co.discovery.assignment.model;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *  The route data from DB is read and modelled into this object
 */
@Data
public class Graph {

    private Set<Node> nodes = new HashSet<>();

    private Map<String, Node> nodeMap = new HashMap<>();

    /*
        Do not allow creating Graph with no data.
     */
    private Graph() {

    }

    public Graph(Set<Node> nodes, Map<String, Node> nodeMap) {
        this.nodes = nodes;
        this.nodeMap = nodeMap;
    }
}
