package com.progetto.flyway.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
// OncePerRequestFilter è una classe di Spring pensata  per creare filtri che si attivano una volta per ogni richiesta
public class SessionFilter extends OncePerRequestFilter {

    @Override
    // intercetta ogni richiesta HTTP
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Ottiene il path della richiesta corrente (es: "/menu", "/css/style.css", ecc.)
        String path = request.getRequestURI();

        // ROTTE PUBBLICHE (non richiedono autenticazione)
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Recupera la sessione
        HttpSession session = request.getSession(false);
        // Controlla se la sessione è valida e contiene l'attributo "loggedUser"
        boolean loggedIn = session != null && session.getAttribute("loggedUser") != null;

        // Se NON loggato, reindirizza alla pagina di login
        if (!loggedIn) {
            response.sendRedirect("/login");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return path.equals("/") ||
                path.equals("/home") ||
                path.equals("/errorPage") ||
                path.equals("/login") ||
                path.equals("/flights/search") ||
                path.equals("/flights/bookFlight")||
                path.startsWith("/css") ||
                path.startsWith("/js");
    }
}
