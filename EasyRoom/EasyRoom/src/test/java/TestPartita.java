import org.example.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
public class TestPartita {
    EasyRoom testApp = EasyRoom.getInstance();
    LinkedList<Partita> listaPartite;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    @BeforeEach
    void aggiungiEsempio(){
        int idProva = -1;
        Giocatore giocatore1 = new Giocatore("Nome1", "Cognome1", "fiscaleprimo", "email1@email.com", LocalDate.parse("01/01/2000", dateFormatter));
        Giocatore giocatore2 = new Giocatore("Nome2", "Cognome2", "fiscalesecondo", "email2@email.com", LocalDate.parse("01/01/2000", dateFormatter));
        Stanza stanzaProva = new Stanza(-1, "Prova", 50.0f);
        LocalDate dataProva = LocalDate.parse("28/11/2024", dateFormatter);
        LocalTime tempoInizioProva = LocalTime.parse("11:00", timeFormatter);
        LocalTime tempoFineProva = LocalTime.parse("12:00", timeFormatter);
        Attrezzatura attrezzaturaProva = new Attrezzatura(2, 5);
        float costoProva = 50.50f;
        Prenotazione prenotazioneProva = new Prenotazione(idProva,stanzaProva, giocatore1, giocatore2, dataProva, tempoInizioProva, tempoFineProva, attrezzaturaProva, costoProva);
        LocalTime tempoCheckinProva = LocalTime.parse("13:00", timeFormatter);
        LocalTime tempoCheckoutProva = LocalTime.parse("14:00", timeFormatter);
        boolean provaOccupata = false;
        Partita partitaProva = new Partita(prenotazioneProva, tempoCheckinProva, tempoCheckoutProva);
        listaPartite = testApp.getListaPartiteGiocate();
        listaPartite.add(partitaProva);
    }

    @AfterEach
    void afterTest (){
        Partita partitaDaEliminare = testApp.getPartitaId(-1);
        listaPartite.remove(partitaDaEliminare);
    }

    @Test
    void testGetTempoCheckin() {
        try{
            Partita partitaTest = testApp.getPartitaId(-1);
            LocalTime tempoInizioCheckin = LocalTime.parse("13:00", timeFormatter);
            assertEquals(tempoInizioCheckin, partitaTest.getTempoCheckin());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetTempoCheckin() {
        try{
            Partita partitaTest = testApp.getPartitaId(-1);
            LocalTime tempoInizioCheckin = LocalTime.parse("13:30", timeFormatter);
            partitaTest.setTempoCheckin(tempoInizioCheckin);
            assertEquals(tempoInizioCheckin, partitaTest.getTempoCheckin());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetTempoCheckout() {
        try{
            Partita partitaTest = testApp.getPartitaId(-1);
            LocalTime tempoInizioCheckout = LocalTime.parse("14:00", timeFormatter);
            assertEquals(tempoInizioCheckout, partitaTest.getTempoCheckout());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetTempoCheckout() {
        try{
            Partita partitaTest = testApp.getPartitaId(-1);
            LocalTime tempoInizioCheckout = LocalTime.parse("14:30", timeFormatter);
            partitaTest.setTempoCheckin(tempoInizioCheckout);
            assertEquals(tempoInizioCheckout, partitaTest.getTempoCheckin());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetIsOccupata(){
        try{
            Partita partitaTest = testApp.getPartitaId(-1);
            boolean provaOccupata = false;
            assertEquals(provaOccupata, partitaTest.getIsOccupata());
        }catch (Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetIsOccupata(){
        try{
            Partita partitaTest = testApp.getPartitaId(-1);
            boolean provaOccupata = true;
            partitaTest.setIsOccupata(provaOccupata);
            assertEquals(provaOccupata, partitaTest.getIsOccupata());
        }catch (Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
}
