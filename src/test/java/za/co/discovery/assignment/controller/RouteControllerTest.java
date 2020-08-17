package za.co.discovery.assignment.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import za.co.discovery.assignment.dao.entity.Route;
import za.co.discovery.assignment.exception.ErrorRedirector;
import za.co.discovery.assignment.service.RouteService;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(RouteController.class)
@ContextConfiguration(classes = {RouteController.class, ErrorRedirector.class})
@ExtendWith(MockitoExtension.class)
public class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteService routeService;

    @Test
    public void createRouteTest(){
        Route route = new Route("A","B",1.2);
        when(routeService.createRoute(route)).thenReturn(route);
        RouteController routeController = new RouteController(routeService);
        Assertions.assertEquals(HttpStatus.CREATED,routeController.createRoute(route).getStatusCode());
    }

    @Test
    public void deleteRouteTest(){
        Route route = new Route("A","B",1.2);
        RouteController routeController = new RouteController(routeService);
        Assertions.assertEquals(HttpStatus.NO_CONTENT,routeController.deleteRoute(1L).getStatusCode());
    }

    @Test
    public void updateRouteTest(){
        Route route = new Route("A","B",1.2);
        when(routeService.updateRoute(route)).thenReturn(route);
        RouteController routeController = new RouteController(routeService);
        Assertions.assertEquals(HttpStatus.OK,routeController.updateRoute(route).getStatusCode());
    }

    @Test
    public void getRouteTest(){
        Route route = new Route("A","B",1.2);
        when(routeService.getRoute(1L)).thenReturn(Optional.of(route));
        RouteController routeController = new RouteController(routeService);
        Assertions.assertEquals(HttpStatus.OK,routeController.getRoute(1L).getStatusCode());
    }

    @Test
    public void getRouteTest_NoMatchingData(){
        when(routeService.getRoute(1L)).thenReturn(Optional.empty());
        RouteController routeController = new RouteController(routeService);
        Assertions.assertEquals(HttpStatus.NOT_FOUND,routeController.getRoute(1L).getStatusCode());
    }

}
