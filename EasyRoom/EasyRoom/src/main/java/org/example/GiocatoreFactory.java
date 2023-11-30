package org.example;

import java.time.LocalDate;

public interface GiocatoreFactory {
    Giocatore CreateGiocatore(String nome, String cognome, String codiceFiscale, String email, LocalDate dataNascita);
}
