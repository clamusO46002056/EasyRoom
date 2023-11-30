package org.example;

import java.time.LocalDate;

public class DefaultGiocatoreFactory implements GiocatoreFactory{
    @Override
    public Giocatore CreateGiocatore(String nome, String cognome, String codiceFiscale, String email, LocalDate dataNascita) {
        return new Giocatore(nome, cognome, codiceFiscale,email,dataNascita);
    }
}
