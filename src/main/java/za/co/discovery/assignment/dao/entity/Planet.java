package za.co.discovery.assignment.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Data
//@Table(indexes = {@Index(name = "planet",  columnList="name", unique = true)})
public class Planet {

    @NotBlank
    @Id
    @Column
    private String planetId;

    @NotBlank
    @Column
    private String planetName;

}
