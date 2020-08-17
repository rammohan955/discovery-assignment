package za.co.discovery.assignment.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;
import za.co.discovery.assignment.dao.entity.Route;
import za.co.discovery.assignment.model.Graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GraphServiceTest {

    @InjectMocks
    private GraphService graphService;

    @Mock
    private RouteService routeService;

    @Test
    @Description("When DB has no data")
    public void createGraphUsingDataFromDbTest_NoDataInDb(){
        when(routeService.findAllRoutes()).thenReturn(Collections.emptyList());
        Graph graph = graphService.createGraphUsingDataFromDb();
        Assertions.assertNotNull(graph);
        Assertions.assertNotNull(graph.getNodeMap());
        Assertions.assertNotNull(graph.getNodes());
        Assertions.assertEquals(0,graph.getNodeMap().size());
        Assertions.assertEquals(0,graph.getNodes().size());
    }

    @Test
    @Description("When DB has some data")
    public void createGraphUsingDataFromDbTest_ValidDatainDb(){
        when(routeService.findAllRoutes()).thenReturn(getRouteObjects());
        Graph graph = graphService.createGraphUsingDataFromDb();
        Assertions.assertNotNull(graph);
        Assertions.assertNotNull(graph.getNodeMap());
        Assertions.assertNotNull(graph.getNodes());
        Assertions.assertEquals(4,graph.getNodeMap().size());
        Assertions.assertEquals(4,graph.getNodes().size());
    }

    private List<Route> getRouteObjects(){
        Route route1 = new Route("A","B",1.2);
        Route route2 = new Route("B","C",1.2);
        Route route3 = new Route("C","D",1.2);
        Route route4 = new Route("A","D",4.9);
        return Arrays.asList(route1,route2,route3,route4);
    }
}
