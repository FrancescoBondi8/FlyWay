Descrizione

Questo è un sistema di prenotazione voli che consente agli utenti di visualizzare i voli disponibili  e agli amministratori di aggiungere nuovi voli, modificare voli esistenti e cancellare voli.
Il sistema consente anche di effettuare prenotazioni di voli e visualizzare i dettagli relativi ai voli selezionati.
Il backend è costruito utilizzando Spring Boot e JPA con una base di dati relazionale, mentre l'interfaccia utente è gestita tramite JSP (JSTL).
Funzionalità principali

    Visualizzazione dei voli disponibili:

        Gli utenti possono cercare i voli disponibili a partire da una data specificata.

        I voli vengono filtrati in base alla città di partenza, città di arrivo e data di partenza e peso bagaglio .

        Vengono mostrati i voli con informazioni come la città di partenza, la città di arrivo, la data di partenza,
         l'ora di partenza e l'ora di arrivo peso bagaglio e posti ancora disponibili per quel volo.

    Prenotazione di un volo:

        Dopo aver selezionato un volo, l'utente può confermare la prenotazione.

        Viene verificato che il volo selezionato abbia ancora posti disponibili e che il peso del bagaglio sia inferiore alla capacità massima dell'aereo.

    Aggiunta di un nuovo volo:

        Gli amministratori possono aggiungere un nuovo volo, specificando la città di partenza, la città di arrivo, la data di partenza, l'ora di partenza, l'ora di arrivo e il tipo di aereo.

        Il sistema verifica che i dati inseriti siano validi (ad esempio, che l'ora di arrivo sia successiva a quella di partenza).

    Modifica di un volo esistente:

        Gli amministratori possono modificare i dettagli di un volo esistente, inclusi il tipo di aereo, l'ora di partenza, l'ora di arrivo e altre informazioni relative al volo.

        Prima di salvare le modifiche, viene verificato che il nuovo aereo selezionato abbia la capacità sufficiente per i passeggeri e il peso del bagaglio prenotato.

    Cancellazione di un volo:

        Gli amministratori possono cancellare un volo esistente fornendo l'ID del volo.

        Il sistema verifica che il volo esista prima di procedere con la cancellazione.


    Gestione delle credenziali di accesso:

        Gli utenti possono effettuare il login utilizzando il proprio nome utente e password.

        Gli utenti possono fare il logout dalla piattaforma.

Come funziona

    Flusso dell'applicazione:

        L'utente apre la pagina principale e può cercare voli disponibili.

        Una volta trovati i voli, può prenotare un volo o vedere i dettagli.

        Gli amministratori possono aggiungere, modificare o cancellare voli attraverso il menu di gestione.

    Struttura del database:

        Il sistema utilizza:

        La tabella Voli contiene informazioni come la città di partenza, la città di arrivo, la data e l'ora di partenza, e il tipo di aereo.

        La tabella Aerei contiene informazioni sul tipo di aereo, come la capacità massima di passeggeri e la quantità massima di carico.

        Tabella Aeroporti che contiene gli aeroporti disponibili

        Vi è anche una tabella Utenti che memorizza le credenziali degli amministratori

    Backend:

        L'applicazione è costruita utilizzando Spring Boot, che gestisce la logica del backend, inclusa la gestione delle prenotazioni e delle modifiche ai voli.

        Hibernate è utilizzato insieme a JPA (Java Persistence API) per la gestione delle entità, il mapping delle classi
        Java con le tabelle del database e le operazioni CRUD (Create, Read, Update, Delete).

    Frontend:

        L'interfaccia utente è realizzata con JSP (JSTL). Gli utenti possono interagire con il sistema attraverso moduli per cercare voli, prenotare un volo, e visualizzare i dettagli.

        Le informazioni sui voli vengono passate al frontend dal controller Spring.





Tecnologie utilizzate

    Spring Boot: Framework per il backend.

    Hibernate & JPA: Gestione delle entità e operazioni di persistenza dei dati.

    Maven: Sistema di build e gestione delle dipendenze.

    JSP: JavaServer Pages per la gestione del frontend.