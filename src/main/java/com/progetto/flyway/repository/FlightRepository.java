package com.progetto.flyway.repository;

import com.progetto.flyway.model.Aircraft;
import com.progetto.flyway.model.Flight;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {



    /**
     * Cerca i voli disponibili in base ai parametri forniti.
     * La query seleziona i voli in cui:
     * - La città di partenza corrisponde alla città di partenza fornita.
     * - La città di arrivo corrisponde alla città di arrivo fornita.
     * - La data del volo è uguale o successiva alla data fornita.
     * - Il numero di passeggeri prenotati sul volo + 1 (quello che sta cercando di ottenere il posto) è inferiore alla capacità massima di passeggeri dell'aereo.
     * - Il peso totale del bagaglio (peso del bagaglio attuale più il peso del bagaglio fornito) è inferiore alla capacità massima di carico dell'aeromobile.
     *
     * @param departureCity La città di partenza del volo.
     * @param arrivalCity La città di arrivo del volo.
     * @param flightDate La data del volo, che deve essere uguale o successiva a quella fornita.
     * @param baggageWeight Il peso del bagaglio che si intende portare, che verrà aggiunto al peso del bagaglio già presente nel volo.
     * @return Una lista di voli con oggetti Flight che soddisfano i criteri, compresi quelli con posti e capacità di carico disponibili.
     */
    @Query("SELECT f FROM Flight f " +
            "JOIN f.aircraft t " +
            "WHERE f.departureCity = :departureCity " +
            "AND f.arrivalCity = :arrivalCity " +
            "AND f.flightDate >= :flightDate " +
            "AND f.numberOfPassengers + 1 <= t.maxPassengers " +
            "AND f.weightBaggage + :baggageWeight <= t.maxCargoQuantity " +
            "ORDER BY f.flightDate ASC")
    List<Flight> searchFlights(@Param("departureCity") String departureCity,
                               @Param("arrivalCity") String arrivalCity,
                               @Param("flightDate") LocalDate flightDate,
                               @Param("baggageWeight") Integer baggageWeight);




    /**
     * Aggiorna il numero di passeggeri e il peso del bagaglio di un volo.
     *
     * Questa query esegue un aggiornamento nel database incrementando il numero di passeggeri e il peso del bagaglio per un volo
     * identificato dal suo ID.
     *
     * @param flightId       L'ID del volo da aggiornare.
     * @param numPassengers  Il numero di passeggeri da aggiungere al volo.
     * @param baggageWeight  Il peso del bagaglio da aggiungere al volo.
     * @return              La riga modificata
     *
     */
    @Modifying
    @Transactional
    @Query("UPDATE Flight f SET f.numberOfPassengers = f.numberOfPassengers + :numPassengers, f.weightBaggage = f.weightBaggage + :baggageWeight WHERE f.id = :flightId")
    int updateWeightOfFlight(@Param("flightId") Long flightId,
                            @Param("numPassengers") int numPassengers,
                            @Param("baggageWeight") int baggageWeight);





    /**
     * Aggiorna i dettagli di un volo esistente utilizzando i parametri forniti.
     * Il volo viene identificato tramite il suo ID.
     *
     * @param id ID del volo da aggiornare
     * @param departureCity nuova città di partenza
     * @param arrivalCity nuova città di arrivo
     * @param flightDate nuova data del volo
     * @param departureTime nuovo orario di partenza
     * @param arrivalTime nuovo orario di arrivo
     * @param aircraft nuovo aereo associato al volo
     */

    @Modifying
    @Transactional
    @Query("UPDATE Flight f SET f.departureCity = :departureCity, " +
            "f.arrivalCity = :arrivalCity, " +
            "f.flightDate = :flightDate, " +
            "f.departureTime = :departureTime, " +
            "f.arrivalTime = :arrivalTime, " +
            "f.aircraft = :aircraft " +
            "WHERE f.id = :id")
    void updateFlightDetails(@Param("id") Long id,
                             @Param("departureCity") String departureCity,
                             @Param("arrivalCity") String arrivalCity,
                             @Param("flightDate") LocalDate flightDate,
                             @Param("departureTime") LocalDateTime departureTime,
                             @Param("arrivalTime") LocalDateTime arrivalTime,
                             @Param("aircraft") Aircraft aircraft);


    Optional<Flight> findFlightById(Long id);

    @Query("SELECT f FROM Flight f WHERE f.flightDate >= :flightDate ORDER BY f.flightDate ASC")
    List<Flight> findAllFromDate(@Param("flightDate") LocalDate flightDate);
}