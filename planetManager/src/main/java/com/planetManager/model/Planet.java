package com.planetManager.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Planet extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private Long surfaceArea;

    @NotNull
    private Long mass;

    @NotNull
    private Long distanceFromSun;

    private Integer averageSurfaceTemperature;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planet")
    private List<Satellite> satellites;
}
