package com.progetto.flyway.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;


@Entity
@Table(name = "voli")

public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_volo")
    @Getter
    private Long id;

    @Column(name = "giorno")
    @Getter @Setter
    private LocalDate flightDate;

    @Column(name = "citta_partenza")
    @Getter
    @Setter
    private String departureCity;

    @Column(name = "ora_partenza")
    @Getter
    @Setter
    private LocalDateTime departureTime;

    @Column(name = "citta_arrivo")
    @Getter
    @Setter
    private String arrivalCity;

    @Column(name = "ora_arrivo")
    @Getter
    @Setter
    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "tipo_aereo", referencedColumnName = "tipo_aereo", nullable = false)
    @Getter @Setter
    private Aircraft aircraft;

    @Column(name = "passeggeri")
    @Getter @Setter
    private int numberOfPassengers;

    @Column(name = "merci")
    @Getter @Setter
    private int weightBaggage;

    //Attributi di utilità

    @Transient
    private String departureTimeFormatted;

    @Transient
    private String arrivalTimeFormatted;

    @Transient
    private String flightDateFormatted;

    @Transient
    private int availableSeats;

    @Transient
    private int availableBaggageWeight;


    public Flight() {

    }

    public Flight(LocalDate flightDate, String departureCity, LocalDateTime departureTime  ,String arrivalCity, LocalDateTime arrivalTime, Aircraft aircraft ) {
        this.flightDate = flightDate;
        this.departureCity = departureCity;
        this.departureTime = departureTime;
        this.arrivalCity = arrivalCity;
        this.arrivalTime = arrivalTime;
        this.aircraft = aircraft;
        this.numberOfPassengers = 0;
        this.weightBaggage = 0;
    }



    /**
     * Restituisce la data del volo formattata come "gg/MM/aaaa".
     *
     * Questo metodo formatta la data del volo (flightDate) in una stringa con il formato "dd/MM/yyyy". Se la data del volo è nulla,
     * il metodo restituirà null.
     *
     * @return la data del volo formattata come "dd/MM/yyyy" oppure null se la data del volo è nulla.
     */
    public String getFlightDateFormatted() {
        if (flightDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return flightDate.format(formatter);
        }
        return null;
    }



    /**
     * Restituisce l'orario di partenza formattato come "HH:mm" (ore e minuti).
     *
     * Questo metodo formatta l'orario di partenza in una stringa con il formato "HH:mm". Se l'orario di partenza è nullo,
     * il metodo restituirà null.
     *
     * @return l'orario di partenza formattato come "HH:mm" oppure null se l'orario di partenza è nullo.
     */
    public String getDepartureTimeFormatted() {
        if (departureTime != null) {
            return departureTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        return null;
    }




    /**
     * Restituisce l'orario di arrivo formattato come "HH:mm" (ore e minuti).
     *
     * Questo metodo formatta l'orario di arrivo in una stringa con il formato "HH:mm". Se l'orario di arrivo è nullo,
     * il metodo restituirà null.
     *
     * @return l'orario di arrivo formattato come "HH:mm" oppure null se l'orario di arrivo è nullo.
     */
    public String getArrivalTimeFormatted() {
        if (arrivalTime != null) {
            return arrivalTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        return null;
    }


    /**
     * Restituisce il numero di posti disponibili sul volo.
     *
     * Questo metodo calcola il numero di posti disponibili sottraendo il numero di passeggeri già prenotati dal numero massimo
     * di passeggeri consentito per l'aereo. La logica si basa sul fatto che la relazione tra il volo e l'aereo è stata stabilita
     * attraverso una join nel metodo `searchFlight` in `FlightRepository`, che ha creato l'oggetto `aircraft`.
     *
     * @return il numero di posti disponibili sul volo.
     */

    public int getAvailableSeats() {
        return aircraft.getMaxPassengers() - this.numberOfPassengers;
    }




    /**
     * Restituisce il peso di bagaglio disponibile.
     *
     * Questo metodo calcola il peso di bagaglio disponibile sottraendo il peso del bagaglio già prenotato dal peso massimo consentito
     * per l'aereo. La logica si basa sul fatto che la relazione tra il volo e l'aereo è stata stabilita attraverso una join nel metodo
     * `searchFlight` in `FlightRepository`, che ha creato l'oggetto `aircraft`.
     *
     * @return il peso di bagaglio disponibile per il volo.
     */
    public int getAvailableBaggageWeight() {
        return aircraft.getMaxCargoQuantity() - this.weightBaggage;
    }

}
