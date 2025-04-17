package com.progetto.flyway.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;

// Indica che questa classe intercetta eccezioni globali provenienti dai controller
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            DateTimeParseException.class
    })

    public String handleInvalidParams(Exception ex, Model model) {
        model.addAttribute("error", "Parametri non validi. Verifica i dati inseriti.");
        return "errorPage";
    }


    // catch-all fallback
    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("error", "Si è verificato un errore inatteso.");
        return "errorPage";
    }


}
