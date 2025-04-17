package com.progetto.flyway.service;

import com.progetto.flyway.model.Airport;
import com.progetto.flyway.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;

    // Metodo che restituisce tutte le città degli aeroporti ordinate alfabeticamente
    public List<String> getAllCityNames() {
        return airportRepository.findDistinctCityByCityIsNotNullOrderByCityAsc();
    }

}
