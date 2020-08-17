package za.co.discovery.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.assignment.service.ShortestPathService;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
public class ShortestPathController {

    private ShortestPathService shortestPathService;

    @Autowired
    public ShortestPathController(ShortestPathService shortestPathService){
        this.shortestPathService = shortestPathService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "path/shortest", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getShortestPath(
            @NotBlank @RequestParam(value = "source", required = true) String source,
            @NotBlank @RequestParam(value = "destination", required = true) String destination) {
        return new ResponseEntity<>(shortestPathService.getShortestPathBetweenPlanets(source, destination),
                HttpStatus.OK);

    }
}
