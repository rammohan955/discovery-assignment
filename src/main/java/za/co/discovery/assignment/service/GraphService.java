package za.co.discovery.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.dao.entity.Route;
import za.co.discovery.assignment.model.Graph;
import za.co.discovery.assignment.model.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *  This Service is responsible for everything to do with {@link Graph}
 */
@Service
public class GraphService {

    @Autowired
    private RouteService routeService;

    public Graph createGraphUsingDataFromDb(){
        Map<String,Node> nodeMap = new HashMap<>();
        Set<Node> nodeSet = new HashSet<>();
        Iterable<Route> routes = routeService.findAllRoutes();
        for(Route route : routes){
            Node sourceNode = getNode(nodeMap,route.getSource());
            Node destNode = getNode(nodeMap,route.getDestination());
            sourceNode.addDestination(destNode,route.getDistance());
            nodeSet.add(sourceNode);
            nodeMap.put(route.getSource(),sourceNode);

            destNode.addDestination(sourceNode,route.getDistance());
            nodeSet.add(destNode);
            nodeMap.put(route.getDestination(),destNode);


        }
        Graph graph = new Graph(nodeSet,nodeMap);
        return graph;
    }

    private Node getNode(Map<String,Node> nodeMap, String source){
        Node node = null;
        if(nodeMap.containsKey(source)){
            node = nodeMap.get(source);
        }else{
            node = new Node(source);
        }
        return node;
    }


}
