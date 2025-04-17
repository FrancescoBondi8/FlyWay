package com.progetto.flyway.util;

import java.time.LocalDateTime;

public class CheckFunction {


    /**
     * Verifica che l'orario di partenza sia prima dell'orario di arrivo.
     *
     * Questo metodo confronta due oggetti `LocalDateTime`, uno per l'orario di partenza e uno per l'orario di arrivo.
     *
     * @param departure l'orario di partenza del volo come oggetto {@link LocalDateTime}
     * @param arrival l'orario di arrivo del volo come oggetto {@link LocalDateTime}
     *
     * @return `true` se l'orario di arrivo è successivo all'orario di partenza, altrimenti `false`.
     */
    public static boolean checkDepartureTimeBeforeArrivalTime(LocalDateTime departure, LocalDateTime arrival){

        if(!arrival.isAfter(departure)) {
            return false;
        }
        return true;

    }


}
