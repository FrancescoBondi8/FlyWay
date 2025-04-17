package com.progetto.flyway.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MenuController {

    /**
     * Mostra la pagina del menu amministratore attraverso una richiesta GET.
     *
     * @return il nome della vista "menu".
     */
    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

}