package com.progetto.flyway.repository;

import com.progetto.flyway.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AircraftRepository extends JpaRepository<Aircraft, String> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Aircraft a WHERE a.aircraftType = :aircraftType")
    boolean existByAircraftType(String aircraftType);
}
