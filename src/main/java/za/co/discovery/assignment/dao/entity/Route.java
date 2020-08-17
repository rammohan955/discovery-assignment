package za.co.discovery.assignment.dao.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
public class Route {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String source;

    @NotBlank
    private String destination;

    @NotNull
    private Double distance;

    protected Route(){

    }

    public Route(String source, String destination, Double distance){
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }
}
