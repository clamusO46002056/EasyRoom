import org.example.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
public class TestPrenotazione {
    EasyRoom testApp = EasyRoom.getInstance();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    LinkedList<Prenotazione> listaPrenotazioni;
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
        listaPrenotazioni = testApp.getListaPrenotazioni();
        listaPrenotazioni.add(prenotazioneProva);
    }

    @AfterEach
    void afterTest (){
        Prenotazione prenotazioneDaEliminare = testApp.getPrenotazioneId(-1);
        listaPrenotazioni.remove(prenotazioneDaEliminare);
    }

    @Test
    void testGetIdPrenotazione() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            int idTest = -1;
            assertEquals(idTest, prenotazioneTest.getIdPrenotazione());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetIdStanza() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            Stanza stanzaTest = new Stanza(-1, "Prova", 50.0f);
            assertEquals(stanzaTest, prenotazioneTest.getIdStanza());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetStanzaScelta() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            Stanza stanzaTest = new Stanza(-5, "ProvaSet", 100.0f);
            prenotazioneTest.setStanzaScelta(stanzaTest);
            assertEquals(stanzaTest, prenotazioneTest.getIdStanza());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetGiocatore1() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            Giocatore giocatore1Test = new Giocatore("Nome1", "Cognome1", "fiscaleprimo", "email1@email.com", LocalDate.parse("01/01/2000", dateFormatter));
            assertEquals(giocatore1Test, prenotazioneTest.getGiocatore1());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetGiocatore2() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            Giocatore giocatore2Test = new Giocatore("Nome2", "Cognome2", "fiscalesecondo", "email2@email.com", LocalDate.parse("01/01/2000", dateFormatter));
            assertEquals(giocatore2Test, prenotazioneTest.getGiocatore2());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetDataPrenotazione() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            LocalDate dataProva = LocalDate.parse("28/11/2024", dateFormatter);
            assertEquals(dataProva, prenotazioneTest.getDataPrenotazione());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetDataPrenotazione() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            LocalDate dataProva = LocalDate.parse("29/11/2024", dateFormatter);
            prenotazioneTest.setDataPrenotazione(dataProva);
            assertEquals(dataProva, prenotazioneTest.getDataPrenotazione());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetOraInizio() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            LocalTime tempoInizioProva = LocalTime.parse("11:00", timeFormatter);
            assertEquals(tempoInizioProva, prenotazioneTest.getOraInizio());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetOraInizio() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            LocalTime tempoInizioProva = LocalTime.parse("11:30", timeFormatter);
            prenotazioneTest.setOraInizio(tempoInizioProva);
            assertEquals(tempoInizioProva, prenotazioneTest.getOraInizio());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetOraFine() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            LocalTime tempoFineProva = LocalTime.parse("12:00", timeFormatter);
            assertEquals(tempoFineProva, prenotazioneTest.getOraFine());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetOraFine() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            LocalTime tempoFineProva = LocalTime.parse("12:30", timeFormatter);
            prenotazioneTest.setOraFine(tempoFineProva);
            assertEquals(tempoFineProva, prenotazioneTest.getOraFine());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetAttrezzatura() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            Attrezzatura attrezzaturaTest = new Attrezzatura(2,5);
            assertEquals(attrezzaturaTest, prenotazioneTest.getAttrezzaturaRichiesta());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetCosto() {
        try{
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            float costoTest = 50.50f;
            assertEquals(costoTest, prenotazioneTest.getCosto());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetCosto() {
        try{
            float costoTest = 100.0f;
            Prenotazione prenotazioneTest = testApp.getPrenotazioneId(-1);
            prenotazioneTest.setCosto(costoTest);
            assertEquals(costoTest, prenotazioneTest.getCosto());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
}
