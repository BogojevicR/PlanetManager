package com.planetManager.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Satellite extends BaseEntity {

    @NotEmpty(message = "Please provide a name")
    private String name;

    @NotNull(message = "Please provide a surfaceArea")
    private Long surfaceArea;

    @NotNull(message = "Please provide a mass")
    private Long mass;

    private Boolean naturalSatellite;

    @ManyToOne
    @JoinColumn(name = "planet_id", referencedColumnName = "id")
    @JsonBackReference
    private Planet planet;
}