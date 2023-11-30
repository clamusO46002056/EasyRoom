package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Prenotazione {
    DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private int idPrenotazione;
    private Stanza stanzaScelta;
    private LocalDate dataPrenotazione;
    private Giocatore giocatore1;
    private Giocatore giocatore2;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private float costo;
    private Attrezzatura attrezzaturaRichiesta;


    public Prenotazione(int idPrenotazione,Stanza stanzaScelta, Giocatore giocatore1, Giocatore giocatore2, LocalDate dataPrenotazione, LocalTime oraInizio, LocalTime oraFine, Attrezzatura attrezzaturaRichiesta ,float costo){
        this.idPrenotazione = idPrenotazione;
        this.stanzaScelta = stanzaScelta;
        this.giocatore1 = giocatore1;
        this.giocatore2 = giocatore2;
        this.dataPrenotazione = dataPrenotazione;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.attrezzaturaRichiesta = attrezzaturaRichiesta;
        this.costo = costo;
    }

    public int getIdPrenotazione() {
        return idPrenotazione;
    }
    public Stanza getIdStanza() {
        return stanzaScelta;
    }
    public void setStanzaScelta(Stanza stanzaScelta) {
        this.stanzaScelta = stanzaScelta;
    }
    public void setIdPrenotazione(int idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
    }
    public Giocatore getGiocatore1() {
        return giocatore1;
    }
    public Giocatore getGiocatore2() {
        return giocatore2;
    }
    public LocalDate getDataPrenotazione(){
        return dataPrenotazione;
    }
    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }
    public LocalTime getOraFine() {
        return oraFine;
    }
    public void setOraFine(LocalTime oraFine) {
        this.oraFine = oraFine;
    }
    public void setOraInizio(LocalTime oraInizio) {
        this.oraInizio = oraInizio;
    }
    public LocalTime getOraInizio() {
        return oraInizio;
    }
    public Attrezzatura getAttrezzaturaRichiesta() {
        return attrezzaturaRichiesta;
    };
    public void setAttrezzaturaRichiesta(Attrezzatura attrezzaturaRichiesta) {
        this.attrezzaturaRichiesta = attrezzaturaRichiesta;
    }
    public float getCosto() {
        return costo;
    }
    public void setCosto(float costo) {
        this.costo = costo;
    }

    @Override
    public String toString(){
        return "Id prenotazione: " + idPrenotazione+ ", " + stanzaScelta + ", giocatori: " +giocatore1.getCodiceFiscale() + " - " + giocatore2.getCodiceFiscale() + " in data " + dataPrenotazione.format(customFormatter) + " " + oraInizio + "-" + oraFine + ", attezzatura: " + attrezzaturaRichiesta + ", per un totale di euro " + costo;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prenotazione that = (Prenotazione) o;
        return idPrenotazione == that.idPrenotazione &&
                attrezzaturaRichiesta == that.attrezzaturaRichiesta &&
                Float.compare(that.costo, costo) == 0 &&
                Objects.equals(stanzaScelta, that.stanzaScelta) &&
                Objects.equals(giocatore1, that.giocatore1) &&
                Objects.equals(giocatore2, that.giocatore2) &&
                Objects.equals(dataPrenotazione, that.dataPrenotazione) &&
                Objects.equals(oraInizio, that.oraInizio) &&
                Objects.equals(oraFine, that.oraFine);
    }
}
