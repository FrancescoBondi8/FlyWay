package com.progetto.flyway.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "aeroporti")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_aeroporto")
    private Long id;
    @Column(name = "citta")
    private String city;
    @Column(name = "nazione")
    private String nation;
    @Column(name = "num_piste")
    private Integer numberRunWays ;

    public Airport(){

    }

}
