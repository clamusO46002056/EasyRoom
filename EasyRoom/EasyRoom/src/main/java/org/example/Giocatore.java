package org.example;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Objects;

public class Giocatore {
    DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String email;
    private LocalDate dataNascita;

    public Giocatore (String nome, String cognome, String codiceFiscale, String email, LocalDate dataNascita){

        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.dataNascita = dataNascita;
        this.email = email;
    }
    public String getNome(){
        return nome;
    }
    public String getCognome(){
        return cognome;
    }
    public String getCodiceFiscale(){
        return codiceFiscale;
    }

    public LocalDate getDataNascita(){
        return dataNascita;
    }

    public String getEmail(){
        return email;
    }
    @Override
    public String toString(){
        return nome + " " + cognome + " " + codiceFiscale + " "  + email + " " + dataNascita.format(customFormatter);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Giocatore giocatore = (Giocatore) o;
        return Objects.equals(nome, giocatore.nome) &&
                Objects.equals(cognome, giocatore.cognome) &&
                Objects.equals(codiceFiscale, giocatore.codiceFiscale) &&
                Objects.equals(email, giocatore.email) &&
                Objects.equals(dataNascita, giocatore.dataNascita);
    }
}

