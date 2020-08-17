package za.co.discovery.assignment.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.discovery.assignment.dao.entity.Planet;
import za.co.discovery.assignment.model.Graph;
import za.co.discovery.assignment.model.Node;

import javax.validation.ValidationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShortestPathServiceTest {

    @InjectMocks
    private ShortestPathService shortestPathService;

    @Mock
    private GraphService graphService;

    @Mock
    private PlanetService planetService;

    @Test
    public void getShortestPathBetweenNodes_WithNoMappingSourceTest(){
        when(planetService.getPlanetByName("A")).thenReturn(null);
        Assertions.assertThrows(ValidationException.class,() -> shortestPathService.getShortestPathBetweenPlanets("A","B"));
    }

    @Test
    public void getShortestPathBetweenNodes_WithNoMappingDestinationTest(){
        when(planetService.getPlanetByName("A")).thenReturn(new Planet());
        when(planetService.getPlanetByName("B")).thenReturn(null);
        Assertions.assertThrows(ValidationException.class,() -> shortestPathService.getShortestPathBetweenPlanets("A","B"));
    }

    @Test
    public void getShortestPathBetweenNodesTest_MissingRouteDataOnSource(){
        Planet planet1 = new Planet();
        planet1.setPlanetId("A");
        planet1.setPlanetName("Earth");

        Planet planet2 = new Planet();
        planet2.setPlanetId("E");
        planet2.setPlanetName("Saturn");

        when(planetService.getPlanetByName("A")).thenReturn(planet1);
        when(planetService.getPlanetByName("E")).thenReturn(planet2);
        when(graphService.createGraphUsingDataFromDb()).thenReturn(new Graph(Collections.emptySet(),Collections.emptyMap()));
        Assertions.assertThrows(RuntimeException.class,() -> shortestPathService.getShortestPathBetweenPlanets("A","E"));
    }

    @Test
    public void getShortestPathBetweenNodesTest_MissingRouteDataOnDestination(){
        Planet planet1 = new Planet();
        planet1.setPlanetId("A");
        planet1.setPlanetName("Earth");

        Planet planet2 = new Planet();
        planet2.setPlanetId("E");
        planet2.setPlanetName("Saturn");

        when(planetService.getPlanetByName("A")).thenReturn(planet1);
        when(planetService.getPlanetByName("E")).thenReturn(planet2);

        Set<Node> nodeSet = Set.of(new Node("A"));
        Map<String,Node> nodeMap = Map.of("A",new Node("A"));
        when(graphService.createGraphUsingDataFromDb()).thenReturn(new Graph(nodeSet,nodeMap));
        Assertions.assertThrows(RuntimeException.class,() -> shortestPathService.getShortestPathBetweenPlanets("A","E"));
    }

    @Test
    public void getShortestPathBetweenNodesTest_WithPlanetMapMissing(){
        Planet planet1 = new Planet();
        planet1.setPlanetId("A");
        planet1.setPlanetName("Earth");

        Planet planet2 = new Planet();
        planet2.setPlanetId("C");
        planet2.setPlanetName("Uranus");

        when(planetService.getPlanetByName("A")).thenReturn(planet1);
        when(planetService.getPlanetByName("C")).thenReturn(planet2);

        when(graphService.createGraphUsingDataFromDb()).thenReturn(getGraph());
        List<String> shortestPath = shortestPathService.getShortestPathBetweenPlanets("A","C");
        Assertions.assertNotNull(shortestPath);
        Assertions.assertEquals(1,shortestPath.size());
    }

    @Test
    public void getShortestPathBetweenNodesTest_WithValidData(){
        Planet planet1 = new Planet();
        planet1.setPlanetId("A");
        planet1.setPlanetName("Earth");

        Planet planet2 = new Planet();
        planet2.setPlanetId("C");
        planet2.setPlanetName("Uranus");

        when(planetService.getPlanetByName("A")).thenReturn(planet1);
        when(planetService.getPlanetByName("C")).thenReturn(planet2);
        when(planetService.getAllPlanets()).thenReturn(Arrays.asList(planet1,planet2));

        when(graphService.createGraphUsingDataFromDb()).thenReturn(getGraph());
        List<String> shortestPath = shortestPathService.getShortestPathBetweenPlanets("A","C");
        Assertions.assertNotNull(shortestPath);
        Assertions.assertEquals(1,shortestPath.size());
    }

    private Graph getGraph(){
        Node node1 = new Node("A");
        Node node2 = new Node("B");
        Node node3 = new Node("C");

        node1.addDestination(node2,0.1);
        node2.addDestination(node3,0.4);
        node1.addDestination(node3,1.5);

        return new Graph(Set.of(node1,node2,node3),Map.of("A",node1,"B",node2,"C",node3));

    }
}
