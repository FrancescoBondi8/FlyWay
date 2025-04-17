package com.progetto.flyway.service;

import com.progetto.flyway.model.Aircraft;
import com.progetto.flyway.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    // Trova tutti gli aerei
    public List<Aircraft> findAll() {
        return aircraftRepository.findAll();
    }


    public boolean existsByAircraftType(String aircraftType) {
        return aircraftRepository.existByAircraftType(aircraftType);
    }
}
