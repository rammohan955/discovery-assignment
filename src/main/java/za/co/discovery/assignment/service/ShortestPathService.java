package za.co.discovery.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.dao.entity.Planet;
import za.co.discovery.assignment.model.Graph;
import za.co.discovery.assignment.model.Node;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  This Service is responsible for computing the shortest path between the given planets.
 */
@Service
public class ShortestPathService {

    @Autowired
    private GraphService graphService;

    @Autowired
    private PlanetService planetService;

    public List<String> getShortestPathBetweenNodes(String source, String destination) {
        Graph graph = graphService.createGraphUsingDataFromDb();
        Planet src = planetService.getPlanetByName(source);
        Planet dest = planetService.getPlanetByName(destination);
        if (src == null || dest == null) {
            throw new ValidationException(
                    "Both source and destination nodes need to be present to evaluate the shortest path");
        } else {
            Node sourceNode = graph.getNodeMap().get(src.getPlanetId());
            Node destinationNode = graph.getNodeMap().get(dest.getPlanetId());
            if (sourceNode == null || destinationNode == null) {
                throw new RuntimeException("Missing route data on source and/or destination node");
            } else {
                return getShortestPath(calculateShortestPath(sourceNode, destinationNode));
            }

        }

    }

    public List<Node> calculateShortestPath(Node source, Node destination) {
        List<Node> shortestPath = new ArrayList<>();
        source.setDistance(0.0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Double> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Double edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    if(adjacentNode.equals(destination)){
                        return adjacentNode.getShortestPath();
                    }
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return shortestPath;
    }

    private Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        Double lowestDistance = Double.MAX_VALUE;
        for (Node node : unsettledNodes) {
            Double nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(Node evaluationNode, Double edgeWeigh, Node sourceNode) {
        Double sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private List<String> getShortestPath(List<Node> shortestPath){
        Map<String,String> planetsMap = getPlanetMap();
        List<String> shortestPathString = new ArrayList<>(shortestPath.size());
        for(Node node : shortestPath){
            shortestPathString.add(planetsMap.get(node.getName()));
        }
        return shortestPathString;
    }

    private Map<String,String> getPlanetMap(){
        Iterable<Planet> planets = planetService.getAllPlanets();
        Map<String,String> planetsMap = new HashMap<>();
        for(Planet p : planets){
            planetsMap.put(p.getPlanetId(),p.getPlanetName());
        }
        return planetsMap;
    }
}
