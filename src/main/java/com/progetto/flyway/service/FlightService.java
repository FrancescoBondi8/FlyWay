package com.progetto.flyway.service;

import com.progetto.flyway.model.Aircraft;
import com.progetto.flyway.model.Flight;
import com.progetto.flyway.repository.FlightRepository;
import com.progetto.flyway.util.CheckFunction;
import com.progetto.flyway.exceptions.DatabaseUpdateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AircraftService aircraftService;




    /**
     * Questo metodo esegue una query nel repository per recuperare i voli che corrispondono alla città di partenza,
     * la città di arrivo, la data di partenza e il peso del bagaglio specificato dall'utente. La lista restituita
     * contiene tutti i voli che soddisfano i criteri.
     * Esegue controlli sulla validità dei dati prima di inviare la query
     *
     * @param departureCity La città di partenza per la ricerca dei voli.
     * @param arrivalCity La città di arrivo per la ricerca dei voli.
     * @param flightDate La data di partenza per la ricerca dei voli.
     * @param baggageWeight Il peso del bagaglio per il quale si cercano voli disponibili.
     * @return Una lista di oggetti {@link Flight} che rappresentano i voli disponibili.
     * @throws IllegalArgumentException Se c'è qualche campo del volo  nullo o non valido.
     */
    public List<Flight> findAvaiableFlights(String departureCity, String arrivalCity, LocalDate flightDate, Integer baggageWeight) throws IllegalArgumentException {

        if (departureCity == null || departureCity.isEmpty()) {
            throw new IllegalArgumentException("La città di partenza è obbligatoria");
        }

        if (arrivalCity == null || arrivalCity.isEmpty()) {
            throw new IllegalArgumentException("La città di arrivo è obbligatoria");
        }

        if (flightDate == null) {
            throw new IllegalArgumentException("La data del volo è obbligatoria");
        }

        if (baggageWeight != null && baggageWeight < 0) {
            throw new IllegalArgumentException("Il peso del bagaglio non può essere negativo");
        }

        List<Flight> results = flightRepository.searchFlights(departureCity, arrivalCity, flightDate, baggageWeight);

        //Filtra i voli per rimuovere quelli che hanno dati nulli
        results.stream()
                .filter(flight -> flight.getDepartureCity() != null &&
                        flight.getArrivalCity() != null &&
                        flight.getDepartureCity() != null &&
                        flight.getArrivalCity() != null &&
                        flight.getFlightDate() != null)
                .collect(Collectors.toList());

        return results;
    }



    /**
     * Aggiorna i dettagli di un volo, incrementando il numero di passeggeri e il peso del bagaglio.
     *
     * Questo metodo esegue una query nel repository aggiornando il numero di passeggeri (1) e il peso del bagaglio di un volo specificato dal suo ID.
     * Esegue dei controlli per verificare la validità dei dati e se l'iD è presente nel database.
     * @param flightId L'ID del volo da aggiornare.
     * @param baggageWeight Il peso del bagaglio da aggiungere al volo.
     * @return Il numero di righe modificate
     * @throws IllegalArgumentException Se l'ID del volo è nullo o non valido.
     */

    public int updateWeightOfFlight(Long flightId, Integer baggageWeight) throws DatabaseUpdateException, IllegalArgumentException {

            // Verifica che l'ID del volo sia valido
            if (flightId == null || flightId <= 0) {
                throw new IllegalArgumentException("ID del volo non valido.");
            }

            // Verifica che il peso del bagaglio sia valido
            if (baggageWeight == null || baggageWeight < 0) {
                throw new IllegalArgumentException("Peso del bagaglio non valido.");
            }

            // Verifica che il volo esista nel database
            Optional<Flight> flight = flightRepository.findById(flightId);
            if (!flight.isPresent()) {
                throw new DatabaseUpdateException("Volo non trovato con l'ID: " + flightId);
            }


            int updated = flightRepository.updateWeightOfFlight(flightId, 1, baggageWeight);
            if (updated == 1) {
                return 1;
            } else {
                throw new DatabaseUpdateException("Errore durante l'aggiornamento del peso del bagaglio");

            }
    }




    /**
     * Trova un volo in base al suo ID.
     *
     * Viene effettuata una verifica sull'ID del volo per assicurarsi che sia valido (non nullo e maggiore di zero).
     * Se l'ID non è valido, viene sollevata un'eccezione {@link IllegalArgumentException}.
     * Se il volo con l'ID specificato esiste nel database, viene restituito come un {@link Optional}.
     * Se il volo non viene trovato, viene restituito un {@link Optional#empty()}.
     *
     * @param flightId L'ID del volo da trovare.
     * @return Un {@link Optional} contenente il volo trovato, o {@link Optional#empty()} se il volo non esiste.
     * @throws IllegalArgumentException Se l'ID del volo è nullo o non valido.
     */

    public Optional<Flight> findFlightById(Long flightId) throws IllegalArgumentException {
        // Verifica che l'ID del volo sia valido
        if (flightId == null || flightId <= 0) {
            throw new IllegalArgumentException("ID del volo non valido.");
        }

        // Trova il volo tramite il repository
        Optional<Flight> flight = flightRepository.findFlightById(flightId);

        // Se il volo non esiste, restituisci Optional.empty
        if (flight.isEmpty()) {
            return Optional.empty();
        }

        // Se il volo è stato trovato, restituisci l'Optional contenente il volo
        return flight;
    }



    /**
     * Trova tutti i voli disponibili nel database.
     *
     * Se non ci sono voli nel database, viene restituita una lista vuota.
     *
     * @return Una lista di oggetti {@link Flight} contenente tutti i voli trovati nel database.
     */
    public List<Flight> findAll() {

        return flightRepository.findAll();

    }



    /**
     * Salva un nuovo volo nel database, eseguendo una serie di controlli sulla validità dei dati.
     * Se uno dei controlli fallisce, viene sollevata un'eccezione {@link IllegalArgumentException}.
     *
     * I controlli includono:
     * - Verifica che l'aereo sia valido (selezionato correttamente e esistente nel database).
     * - Verifica che le città di partenza e arrivo siano valide (non nulli o vuoti).
     * - Verifica che la data del volo non sia nel passato.
     * - Verifica che il numero di passeggeri e il peso del bagaglio non siano negativi.
     * - Verifica che il numero di passeggeri non superi la capacità massima dell'aereo.
     * - Verifica che il peso del bagaglio non superi la capacità massima di carico dell'aereo.
     * - Verifica che l'ora di arrivo sia successiva a quella di partenza.
     *
     * @param flight Il volo da salvare, contenente tutte le informazioni necessarie.
     * @return Il volo salvato nel database.
     * @throws IllegalArgumentException Se uno dei controlli fallisce, viene sollevata un'eccezione con un messaggio appropriato.
     */

    public Flight save(Flight flight) throws IllegalArgumentException {

        // Controllo che l'aereo sia valido
        if (flight.getAircraft() == null) {
            throw new IllegalArgumentException("Aereo non selezionato o non valido.");
        }

        // Verifica che l'aereo esista nel database
        if (!aircraftService.existsByAircraftType(flight.getAircraft().getAircraftType())) {
            throw new IllegalArgumentException("Il tipo di aereo selezionato non esiste.");
        }

        // Controllo sulla validità delle città di partenza e arrivo
        if (flight.getDepartureCity() == null || flight.getDepartureCity().isEmpty()) {
            throw new IllegalArgumentException("Città di partenza non valida.");
        }
        if (flight.getArrivalCity() == null || flight.getArrivalCity().isEmpty()) {
            throw new IllegalArgumentException("Città di arrivo non valida.");
        }

        // Controllo sulla validità della data del volo (la data non deve essere nel passato)
        if (flight.getFlightDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La data del volo non può essere nel passato.");
        }

        // Controllo sul numero di passeggeri e il peso del bagaglio
        if (flight.getNumberOfPassengers() < 0) {
            throw new IllegalArgumentException("Il numero di passeggeri non può essere negativo.");
        }
        if (flight.getWeightBaggage() < 0) {
            throw new IllegalArgumentException("Il peso del bagaglio non può essere negativo.");
        }

        // Controllo che il numero di passeggeri non superi la capacità massima dell'aereo
        if (flight.getNumberOfPassengers() > flight.getAircraft().getMaxPassengers()) {
            throw new IllegalArgumentException("Il numero di passeggeri supera la capacità massima dell'aereo.");
        }

        // Controllo che il peso del bagaglio non superi la capacità massima di carico dell'aereo
        if (flight.getWeightBaggage() > flight.getAircraft().getMaxCargoQuantity()) {
            throw new IllegalArgumentException("Il peso del bagaglio supera la capacità massima di carico dell'aereo.");
        }

        // Verifica che l'ora di arrivo sia successiva all'ora di partenza
        if (!CheckFunction.checkDepartureTimeBeforeArrivalTime(flight.getDepartureTime(), flight.getArrivalTime())) {
            throw new IllegalArgumentException("L'ora di arrivo deve essere successiva a quella di partenza.");
        }

        return flightRepository.save(flight);
    }





    /**
     * Elimina un volo dal database in base al suo ID.
     *
     * Prima di eseguire l'eliminazione, viene effettuato un controllo sull'ID del volo per verificare che sia valido (non nullo e maggiore di zero).
     * Se l'ID non è valido, viene sollevata un'eccezione {@link IllegalArgumentException}.
     * Se l'ID è valido, il volo viene eliminato utilizzando il repository.
     *
     * @param flightId L'ID del volo da eliminare.
     * @throws IllegalArgumentException Se l'ID del volo è nullo o non valido.
     */
    public void delete(Long flightId) {
        if (flightId == null || flightId <= 0) {
            throw new IllegalArgumentException("ID del volo non valido.");
        }
        flightRepository.deleteById(flightId);

    }



    /**
     * Aggiorna i dettagli di un volo esistente nel sistema, eseguendo una serie di controlli sui dati
     * prima di procedere con l'aggiornamento nel database.
     *
     * I controlli includono:
     * - Verifica che l'aereo sia selezionato correttamente e che esista nel database.
     * - Verifica che l'ora di arrivo sia successiva a quella di partenza.
     * - Verifica che il tipo di aereo selezionato possa contenere il numero di passeggeri e il peso del bagaglio prenotati.
     * - Verifica che le città di partenza e arrivo siano valide (non vuote o nulle).
     * - Verifica che la data e gli orari del volo siano validi.
     *
     * @param flightId Il ID del volo da aggiornare.
     * @param departureCity La città di partenza del volo.
     * @param arrivalCity La città di arrivo del volo.
     * @param departureDateTime L'ora di partenza del volo.
     * @param arrivalDateTime L'ora di arrivo del volo.
     * @param flightDate La data del volo.
     * @param aircraft L'aereo da associare al volo.
     * @throws IllegalArgumentException Se uno dei controlli fallisce, viene sollevata un'eccezione con il messaggio appropriato.
     */

    public void updateFlight(Long flightId, String departureCity, String arrivalCity, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, LocalDate flightDate, Aircraft aircraft) {

        if (aircraft == null || !aircraftService.existsByAircraftType(aircraft.getAircraftType())) {
            throw new IllegalArgumentException("Aereo non selezionato o non valido.");
        }

        if(!CheckFunction.checkDepartureTimeBeforeArrivalTime(departureDateTime, arrivalDateTime)){
            throw new IllegalArgumentException("L'ora di arrivo deve essere successiva a quella di partenza");
        }

        Flight oldflight = flightRepository.findById(flightId).orElse(null);

        if(aircraft.getMaxPassengers() < oldflight.getNumberOfPassengers() ) {
            throw new IllegalArgumentException("Il tipo di aereo scelto può contenere meno passeggeri di quelli prenotati. Operazione Annullata!");
        }

        if(aircraft.getMaxCargoQuantity() < oldflight.getWeightBaggage()){
            throw new IllegalArgumentException("Il tipo di aereo scelto può sostenere meno Kg di bagagli rispetto a quelli già prenotati. Operazione Annullata!");
        }


        if (departureCity == null || departureCity.isEmpty()) {
            throw new IllegalArgumentException("Città di partenza non valida.");
        }

        if (arrivalCity == null || arrivalCity.isEmpty()) {
            throw new IllegalArgumentException("Città di arrivo non valida.");
        }

        if (flightDate == null || departureDateTime == null || arrivalDateTime == null) {
            throw new IllegalArgumentException("Data e orari del volo non validi.");
        }

        flightRepository.updateFlightDetails(flightId,departureCity,arrivalCity,flightDate,departureDateTime,arrivalDateTime,aircraft);
    }




    public List<Flight> findAllFromDate(LocalDate flightDate) {
        return flightRepository.findAllFromDate(flightDate);
    }
}
