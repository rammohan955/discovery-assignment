package za.co.discovery.assignment.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import za.co.discovery.assignment.exception.ErrorRedirector;
import za.co.discovery.assignment.service.ShortestPathService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(ShortestPathController.class)
@ContextConfiguration(classes = {ShortestPathController.class, ErrorRedirector.class})
@ExtendWith(MockitoExtension.class)
public class ShortestPathControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortestPathService shortestPathService;

    @Test
    public void getShortestPathTest(){
        when(shortestPathService.getShortestPathBetweenPlanets("Earth","Mars")).thenReturn(Arrays.asList("Earth","Moon"));
        ShortestPathController shortestPathController = new ShortestPathController(shortestPathService);
        ResponseEntity<List<String>> responseEntity = shortestPathController.getShortestPath("Earth","Mars");
        Assertions.assertEquals(Arrays.asList("Earth","Moon"),responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }


}
