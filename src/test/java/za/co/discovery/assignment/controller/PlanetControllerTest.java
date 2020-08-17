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
import za.co.discovery.assignment.dao.entity.Planet;
import za.co.discovery.assignment.exception.ErrorRedirector;
import za.co.discovery.assignment.service.PlanetService;
import za.co.discovery.assignment.service.PlanetService;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(PlanetController.class)
@ContextConfiguration(classes = {PlanetController.class, ErrorRedirector.class})
@ExtendWith(MockitoExtension.class)
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanetService planetService;

    @Test
    public void createPlanetTest(){
        Planet planet = new Planet();
        when(planetService.createPlanet(planet)).thenReturn(planet);
        PlanetController planetController = new PlanetController(planetService);
        Assertions.assertEquals(HttpStatus.CREATED,planetController.createPlanet(planet).getStatusCode());
    }

    @Test
    public void deletePlanetTest(){
        PlanetController planetController = new PlanetController(planetService);
        Assertions.assertEquals(HttpStatus.NO_CONTENT,planetController.deletePlanet("A").getStatusCode());
    }

    @Test
    public void updatePlanetTest(){
        Planet planet = new Planet();
        when(planetService.updatePlanet(planet)).thenReturn(planet);
        PlanetController planetController = new PlanetController(planetService);
        Assertions.assertEquals(HttpStatus.OK,planetController.updatePlanet(planet).getStatusCode());
    }

    @Test
    public void getPlanetTest(){
        Planet planet = new Planet();
        planet.setPlanetName("Earth");
        planet.setPlanetId("A");
        when(planetService.getPlanet(planet.getPlanetId())).thenReturn(Optional.of(planet));
        PlanetController planetController = new PlanetController(planetService);
        Assertions.assertEquals(HttpStatus.OK,planetController.getPlanet("A").getStatusCode());
    }

    @Test
    public void getPlanetTest_NoMatchingData(){
        when(planetService.getPlanet("A")).thenReturn(Optional.empty());
        PlanetController planetController = new PlanetController(planetService);
        Assertions.assertEquals(HttpStatus.NOT_FOUND,planetController.getPlanet("A").getStatusCode());
    }

}
