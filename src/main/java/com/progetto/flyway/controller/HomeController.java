package com.progetto.flyway.controller;



import com.progetto.flyway.service.AirportService;
import com.progetto.flyway.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

//per restituire pagine JSP
@Controller
public class HomeController {

    @Autowired
    private FlightService flightService;
    @Autowired
    private AirportService airportService;


    /**
     * Metodo che gestisce la richiesta GET per la home page.
     * Recupera le città di partenza e di arrivo distinte e in ordine alfabetico dal database e le passa alla jsp home.
     *
     * @param model Il modello che contiene gli attributi da passare alla jsp.
     * @return home.jsp .
     */
    @GetMapping("/")
    public String home(Model model) {

        List<String> cityNames = airportService.getAllCityNames();

        model.addAttribute("cities", cityNames);
        return "home";
    }
}
