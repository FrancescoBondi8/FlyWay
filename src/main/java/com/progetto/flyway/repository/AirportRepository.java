package com.progetto.flyway.repository;

import com.progetto.flyway.model.Airport;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    @Query("SELECT DISTINCT a.city FROM Airport a WHERE a.city IS NOT NULL ORDER BY a.city ASC")
    List<String> findDistinctCityByCityIsNotNullOrderByCityAsc();

}
