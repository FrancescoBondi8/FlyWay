package com.progetto.flyway.controller;

import com.progetto.flyway.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;



    /**
     * Visualizza il modulo di login.
     *
     * Questo metodo gestisce la richiesta GET per visualizzare il modulo di login.
     * Restituisce la vista "login" che contiene il form per l'inserimento delle credenziali.
     *
     * @return il nome della vista "login".
     */

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // → /WEB-INF/views/login.jsp
    }




    /**
     * Gestisce il processo di login dell'utente.
     *
     * Questo metodo gestisce la richiesta POST per l'autenticazione dell'utente. Verifica se le credenziali
     * (nome utente e password) sono corrette confrontando i valori inseriti con quelli memorizzati nel db.
     * Se le credenziali sono corrette, l'utente viene autenticato e la sessione viene aggiornata con l'utente loggato.
     * In caso contrario, viene restituito un messaggio di errore e il form di login viene mostrato nuovamente.
     *
     * @param username il nome utente inserito dall'utente nel modulo di login.
     * @param password la password inserita dall'utente nel modulo di login.
     * @param session la sessione HTTP per memorizzare l'utente autenticato.
     * @param model il modello utilizzato per passare i dati alla vista (in caso di errore).
     * @return una stringa che rappresenta il nome della vista da restituire. Se le credenziali sono corrette, viene
     *         effettuato un redirect alla pagina del menu. In caso di errore, viene restituita la vista "login".
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        var userOpt = userService.findByUsername(username);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            session.setAttribute("loggedUser", userOpt.get());
            return "redirect:/menu";
        } else {
            model.addAttribute("error", "Credenziali non valide");
            return "login";
        }
    }



    /**
     * Gestisce il logout dell'utente.
     *
     * Questo metodo gestisce la richiesta GET per effettuare il logout dell'utente. La sessione viene invalidata,
     * rimuovendo così l'utente autenticato, e l'utente viene reindirizzato alla pagina principale (home).
     *
     * @param session la sessione HTTP che deve essere invalidata per effettuare il logout.
     * @return una stringa che rappresenta il reindirizzamento alla pagina principale.
     */

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}



