package za.co.discovery.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.assignment.dao.entity.Planet;
import za.co.discovery.assignment.service.PlanetService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
public class PlanetController {

    private PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService)
    {
        this.planetService = planetService;
    }

    @PostMapping(value = "planet", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Planet> createPlanet(@Valid @RequestBody Planet planet) {
        return new ResponseEntity<>(planetService.createPlanet(planet), HttpStatus.CREATED);
    }

    @PutMapping(value = "planet", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Planet> updatePlanet(@Valid @RequestBody Planet planet) {
        return new ResponseEntity<>(planetService.updatePlanet(planet), HttpStatus.OK);
    }

    @GetMapping(value = "planet", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Planet> getPlanet(
            @NotNull @Min(1) @RequestParam(value = "planetId", required = true) String planetId) {
        Optional<Planet> routeOptional = planetService.getPlanet(planetId);
        if (routeOptional.isPresent()) {
            return new ResponseEntity<>(routeOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "planet", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Planet> deletePlanet(
            @NotNull @Min(1) @RequestParam(value = "planetId", required = true) String planetId) {
        planetService.deletePlanet(planetId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
