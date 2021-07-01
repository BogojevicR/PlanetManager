package com.planetManager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "planet")
    @JsonManagedReference
    @Setter(AccessLevel.NONE)
    @Valid
    private List<Satellite> satellites = new ArrayList<>();

    public void addSatellite(Satellite satellite) {
        this.satellites.add(satellite);
        satellite.setPlanet(this);
    }

    public void removeSatellite(Satellite satellite) {
        this.satellites.remove(satellite);
        satellite.setPlanet(null);
    }


    public void setSatellites(List<Satellite> satellites) {
        for (Satellite satellite : this.satellites) {
            satellite.setPlanet(null);
        }
        this.satellites = new ArrayList<>();
        for (Satellite newSatellite : satellites) {
            this.addSatellite(newSatellite);
        }
    }
}
