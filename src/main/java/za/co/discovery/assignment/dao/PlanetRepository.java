package za.co.discovery.assignment.dao;

import org.springframework.data.repository.CrudRepository;
import za.co.discovery.assignment.dao.entity.Planet;

public interface PlanetRepository extends CrudRepository<Planet,String> {

    Planet findByPlanetName(String name);
}
