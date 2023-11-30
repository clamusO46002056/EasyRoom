package org.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Partita extends Prenotazione{
    LocalTime tempoCheckin;
    LocalTime tempoCheckout;
    boolean isOccupata;
    public Partita(Prenotazione prenotazioneDaVerificare, LocalTime tempoCheckin, LocalTime tempoCheckout){
        super(prenotazioneDaVerificare.getIdPrenotazione(), prenotazioneDaVerificare.getIdStanza(), prenotazioneDaVerificare.getGiocatore1(), prenotazioneDaVerificare.getGiocatore2(), prenotazioneDaVerificare.getDataPrenotazione(), prenotazioneDaVerificare.getOraInizio(), prenotazioneDaVerificare.getOraFine(), prenotazioneDaVerificare.getAttrezzaturaRichiesta(), prenotazioneDaVerificare.getCosto());
        this.tempoCheckin = tempoCheckin;
        this.tempoCheckout = tempoCheckout;
        this.isOccupata = false;
    }
    public LocalTime getTempoCheckin(){
        return tempoCheckin;
    }
    public void setTempoCheckin(LocalTime tempoCheckin) {
        this.tempoCheckin = tempoCheckin;
    }
    public LocalTime getTempoCheckout(){
        return tempoCheckout;
    }
    public void setTempoCheckout(LocalTime tempoCheckout) {
        this.tempoCheckout = tempoCheckout;
    }
    public boolean getIsOccupata(){
        return isOccupata;
    }
    public void setIsOccupata(boolean isOccupata){
        this.isOccupata = isOccupata;
    }

    public String toString(){
        return super.toString() + ", stanza occupata: " + isOccupata +", orario check-in: " + tempoCheckin + ", orario check-out: " + tempoCheckout;
    }
}
