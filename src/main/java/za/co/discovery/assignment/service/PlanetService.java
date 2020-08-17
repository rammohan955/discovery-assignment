package za.co.discovery.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.dao.PlanetRepository;
import za.co.discovery.assignment.dao.entity.Planet;

import java.util.Optional;

/**
 *  This Service is responsible for managing {@link Planet}
 */

@Service
public class PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    public Planet createPlanet(Planet planet){
        return planetRepository.save(planet);
    }

    public Planet updatePlanet(Planet planet){
        return planetRepository.save(planet);
    }

    public Optional<Planet> getPlanet(String planetId){
        return planetRepository.findById(planetId);
    }

    public void deletePlanet(String planetId){
        planetRepository.deleteById(planetId);
    }

    public Planet getPlanetByName(String planetName){
        return planetRepository.findByPlanetName(planetName);
    }

    public Iterable<Planet> getAllPlanets(){
        return planetRepository.findAll();
    }
}
