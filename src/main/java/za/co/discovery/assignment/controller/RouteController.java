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
import za.co.discovery.assignment.dao.entity.Route;
import za.co.discovery.assignment.service.RouteService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
public class RouteController {

    private RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService){
        this.routeService = routeService;
    }

    @PostMapping(value = "route", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Route> createRoute(@Valid @RequestBody Route routeObj) {
        return new ResponseEntity<>(routeService.createRoute(routeObj), HttpStatus.CREATED);
    }

    @PutMapping(value = "route", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Route> updateRoute(@Valid @RequestBody Route routeObj) {
        return new ResponseEntity<>(routeService.updateRoute(routeObj), HttpStatus.OK);
    }

    @GetMapping(value = "route", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Route> getRoute(@NotNull @Min(1) @RequestParam(value = "routeId", required = true)  Long routeId) {
        Optional<Route> routeOptional = routeService.getRoute(routeId);
        if (routeOptional.isPresent()) {
            return new ResponseEntity<>(routeOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "route", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Route> deleteRoute(@NotNull @Min(1) @RequestParam(value = "routeId", required = true) Long routeId) {
        routeService.deleteRoute(routeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
