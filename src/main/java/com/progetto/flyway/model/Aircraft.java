package com.progetto.flyway.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "aereo") // nome della tabella nel DB (in italiano)
public class Aircraft {

    @Id
    @Column(name = "tipo_aereo", columnDefinition = "CHAR(5)")
    @Getter @Setter
    private String aircraftType;

    @Column(name = "num_pass", nullable = false)
    private int maxPassengers;

    @Column(name = "qta_merci", nullable = false)
    private int maxCargoQuantity;

    public Aircraft() {}

}
