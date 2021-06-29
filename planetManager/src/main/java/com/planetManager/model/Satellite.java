package com.planetManager.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Satellite extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private Long surfaceArea;

    @NotNull
    private Long mass;

    private Boolean naturalSatellite;

    @ManyToOne
    @JoinColumn(name="planet_id", nullable = false)
    private Planet planet;
}