package za.co.discovery.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.dao.RouteRepository;
import za.co.discovery.assignment.dao.entity.Route;

import java.util.Optional;

/**
 *  This Service is responsible for managing {@link Route}
 */
@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public Route createRoute(Route routeObj){
        return routeRepository.save(routeObj);
    }

    public Route updateRoute(Route routeObj){
        return routeRepository.save(routeObj);
    }

    public Optional<Route> getRoute(Long routeId){
        return routeRepository.findById(routeId);
    }

    public void deleteRoute(Long routeId){
        routeRepository.deleteById(routeId);
    }

    public Iterable<Route> findAllRoutes(){
        return routeRepository.findAll();
    }
}
