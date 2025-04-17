package com.progetto.flyway.controller;

import com.progetto.flyway.exceptions.DatabaseUpdateException;
import com.progetto.flyway.model.Aircraft;
import com.progetto.flyway.model.Flight;
import com.progetto.flyway.service.FlightService;
import com.progetto.flyway.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


//controller per la gestione dei voli
@Controller
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private AircraftService aircraftService;

    // Ricerca voli dal form
    /**
     * Questo metodo utilizza il servizio flightService che gestisce la ricerca dei voli in base ai parametri forniti dall'utente.
     *
     * @param departureCity La città di partenza del volo.
     * @param arrivalCity La città di arrivo del volo.
     * @param flightDate La data del volo.
     * @param weightBaggage Il peso del bagaglio richiesto.
     * @param model Oggetto utilizzato per aggiungere gli attributi da passare alla vista.
     * @return Il nome della vista da renderizzare: "searchResults".
     */
    @PostMapping("/search")
    public String searchFlights(@RequestParam String departureCity, @RequestParam String arrivalCity, @RequestParam LocalDate flightDate,
                                @RequestParam Integer weightBaggage, Model model) {


        try {
            List<Flight> results = flightService.findAvaiableFlights(departureCity, arrivalCity, flightDate, weightBaggage);

            //aggiungo a model sia i voli restituiti dalla query che il peso del bagaglio inserito dall'utente
            model.addAttribute("flights", results);
            model.addAttribute("weightBaggage", weightBaggage);
            return "showAvaibleFlights";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e);
            return "home";
        }

    }


    /**
     * Gestisce la prenotazione di un volo, aggiornando il numero di passeggeri e il peso del bagaglio.
     *
     * Questo metodo utilizza il servizio`flightService` per aggiornare i dettagli del volo (numero di passeggeri e peso del bagaglio).
     * Inoltre recupera anche il riepilogo dei dati del volo sempre dall'ID
     * @param flightId L'ID del volo che l'utente sta cercando di prenotare.
     * @param weightBaggage Il peso del bagaglio da aggiornare per il volo selezionato.
     * @param model che contiene gli attributi da passare alla vista
     * @return il nome della vista a cui l'utente verrà reindirizzato:
     *         "/confirmationPage" in caso di successo, "/errorPage" in caso di errore.
     */
    @PostMapping("/bookFlight")
    public String bookFlight(@RequestParam Long flightId, @RequestParam Integer weightBaggage, Model model) {
        try {

            // Tentativo di aggiornare il peso del bagaglio per il volo
            if (flightService.updateWeightOfFlight(flightId, weightBaggage) == 1) {
                Optional<Flight> flight = flightService.findFlightById(flightId);

                if(flight.isPresent()) {
                    Flight fl = flight.get();
                    model.addAttribute("flight", fl);
                    model.addAttribute("weightBaggage", weightBaggage);
                    return "/confirmationPage";
                }
            }

            // Se il volo non esiste o l'aggiornamento del peso del bagaglio non è riuscito
            model.addAttribute("error", "Impossibile prenotare il volo. Riprova");
            return "/errorPage";

        } catch (IllegalArgumentException e) {
            // Cattura eccezioni specifiche (es. ID non valido, peso non valido, ecc.)
            model.addAttribute("error", "Errore nei dati: " + e.getMessage());
            return "/errorPage";

        } catch (DatabaseUpdateException e) {
            // Cattura eccezioni specifiche relative al database, se esistono
            model.addAttribute("error", "Errore nel database: " + e.getMessage());
            return "/errorPage";

        }
    }









    //FUNZIONALITA AMMINISTRATORE



    /**
     * Mostra il form per aggiungere un nuovo volo.
     *
     * @param model il modello che contiene gli attributi da passare alla vista.
     * @return il nome della vista "addFlightForm" per aggiungere un volo.
     */
    @GetMapping("/addFlight")
    public String addFlight(Model model) {
        model.addAttribute("aircraftList", aircraftService.findAll());
        return "addFlightForm";
    }




    /**
     * Gestisce la richiesta POST per aggiungere un nuovo volo.
     * I valori di ingresso sono passati dalla vista
     * @param departureCity la città di partenza del volo.
     * @param arrivalCity la città di arrivo del volo.
     * @param flightDate la data del volo.
     * @param departureTime l'orario di partenza del volo.
     * @param arrivalTime l'orario di arrivo del volo.
     * @param aircraft l'aereo che verrà utilizzato per il volo.
     * @param model il modello che contiene gli attributi da passare alla vista.
     * @return il nome della vista "menu" se il volo è stato aggiunto correttamente,
     *         altrimenti mostra un messaggio di errore.
     */
    @PostMapping("/addFlight")
    public String addFlight(@RequestParam String departureCity,
                            @RequestParam String arrivalCity,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime departureTime,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime arrivalTime,
                            @RequestParam Aircraft aircraft,
                            Model model) {

        //conversione delle date in formato valido per essere elaborato
        LocalDateTime departureDateTime = LocalDateTime.of(flightDate, departureTime);
        LocalDateTime arrivalDateTime = LocalDateTime.of(flightDate, arrivalTime);


        Flight flight = new Flight(flightDate,departureCity,departureDateTime,arrivalCity,arrivalDateTime,aircraft);

        try{
            flightService.save(flight);
            model.addAttribute("success", "Volo aggiunto correttamente");
        }catch(IllegalArgumentException e){
            model.addAttribute("error", e);
        }

        return "menu";
    }




    /**
     * Mostra il form per modificare i dettagli di un volo.
     *
     * @return la vista del form di modifica volo.
     */
    @GetMapping("/editFlight")
    public String editFlight() {

        return "/editFlightForm";
    }




    /**
     * Gestisce la richiesta POST per visualizzare i dettagli di un volo da modificare.
     *
     * @param flightId l'ID del volo da modificare.
     * @param model il modello che contiene gli attributi da passare alla vista.
     * @return la vista del form di modifica volo con i dati del volo precompilati.
     */
    @PostMapping("/editFlight")
    public String editFlight(@RequestParam String flightId, Model model) {
        try {
            Long id = Long.parseLong(flightId);
            Optional<Flight> optional = flightService.findFlightById(id);

            // Se il volo è presente, aggiungi gli attributi al modello
            if (optional.isPresent()) {
                Flight flight = optional.get();
                model.addAttribute("aircraftList", aircraftService.findAll());
                model.addAttribute("flight", flight);
                model.addAttribute("success", "OK");
                return "/editFlightForm";

            } else {
                // Se il volo non esiste
                model.addAttribute("error", "Volo non trovato.");
                return "/editFlightForm";
            }

        }catch (NumberFormatException e) {
            model.addAttribute("error", "ID del volo non valido.");
            return "/editFlightForm";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Errore durante il recupero del volo: " + e.getMessage());
            return "/editFlightForm";
        }
    }





    /**
     * Gestisce la richiesta POST per salvare le modifiche apportate a un volo esistente.
     * I dati di ingresso sono passati dalla vista
     * @param departureCity la città di partenza del volo.
     * @param arrivalCity la città di arrivo del volo.
     * @param flightDate la data del volo.
     * @param departureTime l'orario di partenza del volo.
     * @param arrivalTime l'orario di arrivo del volo.
     * @param flightId l'ID del volo da aggiornare.
     * @param aircraft l'aereo che verrà utilizzato per il volo.
     * @param model il modello che contiene gli attributi da passare alla vista.
     * @return la vista del menu se il volo è stato aggiornato correttamente, altrimenti torna al form di modifica con errore.
     */
    @PostMapping("/editFlightSave")
    public String editFlight(@RequestParam String departureCity, @RequestParam String arrivalCity,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime departureTime,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime arrivalTime,
                             @RequestParam Long flightId,
                             @RequestParam Aircraft aircraft, Model model) {


        //conversione in formato valido per essere elaborato
        LocalDateTime departureDateTime = LocalDateTime.of(flightDate, departureTime);
        LocalDateTime arrivalDateTime = LocalDateTime.of(flightDate, arrivalTime);


        try{
            flightService.updateFlight(flightId,departureCity,arrivalCity,departureDateTime,arrivalDateTime,flightDate,aircraft);
            model.addAttribute("success", "Volo modificato correttamente");
            return "/menu";
        }catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "/editFlightForm";
        }

    }




    /**
     * Mostra il form per eliminare un volo.
     *
     * @return la vista per eliminare un volo.
     */

    @GetMapping("/deleteFlight")
    public String deleteFlight() {
        return "/deleteFlight";
    }




    /**
     * Gestisce la richiesta POST per visualizzare i dettagli di un volo da eliminare.
     *
     * @param flightId l'ID del volo da eliminare.
     * @param model il modello che contiene gli attributi da passare alla vista.
     * @return la vista del form di eliminazione volo con i dettagli del volo precompilati.
     */

    @PostMapping("/deleteFlight")
    public String deleteFlight(@RequestParam String flightId, Model model) {

        try {
            Long id = Long.parseLong(flightId);
            Optional<Flight> optional = flightService.findFlightById(id);

            // Se il volo è presente, aggiungi gli attributi al modello e mostralo nella pagina di cancellazione
            if (optional.isPresent()) {
                Flight flight = optional.get();
                model.addAttribute("flight", flight);
                model.addAttribute("success", "OK");
                return "/deleteFlight";

            } else {
                // Se il volo non è trovato
                model.addAttribute("error", "Volo non trovato.");
                return "/editFlightForm";
            }

        } catch (NumberFormatException e) {
            model.addAttribute("error", "ID del volo non valido.");
            return "/editFlightForm";

        } catch (IllegalArgumentException e) {
            // Gestisce tutte le eccezioni generali e restituisce un messaggio di errore
            model.addAttribute("error", "Errore durante il recupero del volo: " + e.getMessage());
            return "/editFlightForm";

        }
    }



    /**
     * Gestisce la richiesta POST per salvare l'eliminazione di un volo.
     *
     * @param flightId l'ID del volo da eliminare.
     * @param model il modello che contiene gli attributi da passare alla vista.
     * @return la vista del menu con il messaggio di successo dopo l'eliminazione.
     */

    @PostMapping("/deleteFlightSave")
    public String deleteFlightSave(@RequestParam Long flightId,Model model) {
        try {
            flightService.delete(flightId);
            model.addAttribute("success", "Volo eliminato correttamente");
            return "/menu";
        }catch(IllegalArgumentException e ){
                model.addAttribute("error", "ID non valido:" + e.getMessage());
                return "/deleteFlight";
        }
    }



    /**
     * Mostra la lista dei voli a partire da una data specifica
     * (la data iniziale è una data fittizia, può essere cambiata con quella odierna).
     *
     * @param flightDate la data da cui iniziare a cercare i voli.
     * @param model il modello che contiene gli attributi da passare alla vista.
     * @return la vista con la lista dei voli disponibili a partire dalla data specificata.
     */

    @GetMapping("/listOfFlights")
    public String listOfFlights(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate,
                                Model model) {
        if (flightDate == null) {
            flightDate = LocalDate.of(2017, 1, 1);
        }
        List<Flight> flights = flightService.findAllFromDate(flightDate);
        model.addAttribute("flightDate", flightDate);
        model.addAttribute("flights", flights);
        return "/listOfFlights";
    }


}
