package za.co.discovery.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.dao.entity.Planet;
import za.co.discovery.assignment.model.Graph;
import za.co.discovery.assignment.model.Node;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  This Service is responsible for computing the shortest path between the given planets.
 */
@Service
public class ShortestPathService {

    @Autowired
    private GraphService graphService;

    @Autowired
    private PlanetService planetService;

    public List<String> getShortestPathBetweenPlanets(String sourcePlanet, String destinationPlanet) {
        Graph graph = graphService.createGraphUsingDataFromDb();
        Planet src = planetService.getPlanetByName(sourcePlanet);
        Planet dest = planetService.getPlanetByName(destinationPlanet);
        if (src == null || dest == null) {
            throw new ValidationException(
                    "Both source and destination nodes need to be present to evaluate the shortest path");
        } else {
            Node sourceNode = graph.getNodeMap().get(src.getPlanetId());
            Node destinationNode = graph.getNodeMap().get(dest.getPlanetId());
            if (sourceNode == null || destinationNode == null) {
                throw new RuntimeException("Missing route data on source and/or destination node");
            } else {
                return getShortestPath(computeShortestPath(sourceNode, destinationNode));
            }

        }

    }

    public List<Node> computeShortestPath(Node source, Node destination) {
        List<Node> nodeListOnShortestPath = new ArrayList<>();
        source.setDistance(0.0);

        Set<Node> processedNodes = new HashSet<>();
        Set<Node> unprocessedNodes = new HashSet<>();

        unprocessedNodes.add(source);

        while (unprocessedNodes.size() != 0) {
            Node currentNode = getClosestNode(unprocessedNodes);
            unprocessedNodes.remove(currentNode);
            for (Map.Entry<Node, Double> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Double edgeWeight = adjacencyPair.getValue();
                if (!processedNodes.contains(adjacentNode)) {
                    computeShortestDistance(adjacentNode, edgeWeight, currentNode);
                    if(adjacentNode.equals(destination)){
                        return adjacentNode.getShortestPath();
                    }
                    unprocessedNodes.add(adjacentNode);
                }
            }
            processedNodes.add(currentNode);
        }
        return nodeListOnShortestPath;
    }

    private Node getClosestNode(Set<Node> unprocessedNodes) {
        return unprocessedNodes.stream().min((node1, node2) -> node1.getDistance() < node2.getDistance() ? -1 :
                node1.getDistance() == node2.getDistance() ? 0 : 1).orElse(null);

    }

    private void computeShortestDistance(Node nodeToBeProcessed, Double weight, Node sourceNode) {
        Double sourceDistance = sourceNode.getDistance();
        if (sourceDistance + weight < nodeToBeProcessed.getDistance()) {
            nodeToBeProcessed.setDistance(sourceDistance + weight);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            nodeToBeProcessed.setShortestPath(shortestPath);
        }
    }

    private List<String> getShortestPath(List<Node> shortestPath){
        Map<String,String> planetsMap = getPlanetMap();
        return shortestPath.stream().map(node -> planetsMap.get(node.getName())).collect(Collectors.toList());
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
