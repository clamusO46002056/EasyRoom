import org.example.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
public class TestMagazzino {
    EasyRoom testApp = EasyRoom.getInstance();
    LinkedList<Magazzino> listaRichieste;
    DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate dataProva = LocalDate.parse("01/01/2024", customFormatter);

    @BeforeEach
    void aggiungiEsempio(){
        Magazzino magazzinoProva = new Magazzino(dataProva, 10, 5);
        listaRichieste = testApp.getListaRichieste();
        listaRichieste.add(magazzinoProva);
    }

    @AfterEach
    void afterTest (){
        Magazzino magazzinoDaEliminare = testApp.getRichiesta(dataProva, 10, 5);
        listaRichieste.remove(magazzinoDaEliminare);
    }

    @Test
    void testGetNumeroIndizi() {
        try{
            Magazzino magazzinoTest = testApp.getRichiesta(dataProva, 10, 5);
            int numeroTest = 5;
            assertEquals(numeroTest, magazzinoTest.getNumeroIndizi());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }@Test
    void testGetNumeroTorce() {
        try{
            Magazzino magazzinoTest = testApp.getRichiesta(dataProva, 10, 5);
            int numeroTest = 10;
            assertEquals(numeroTest, magazzinoTest.getNumeroTorce());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetDataPartita() {
        try{
            Magazzino magazzinoTest = testApp.getRichiesta(dataProva, 10, 5);
            LocalDate dataPartita = LocalDate.parse("01/01/2024", customFormatter);
            assertEquals(dataPartita, magazzinoTest.getDataPartita());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test //Attenzione: prevista lettura da file magazzino.txt per il valore attuale, controllare quello prima di impostare il valore corretto per il test
    void testGetMaxIndizi() {
        try{
            Magazzino magazzinoTest = testApp.getRichiesta(dataProva, 10, 5);
            int numeroTest = 15;
            assertEquals(numeroTest, magazzinoTest.getMaxIndizi());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetMaxIndizi() {
        try{
            Magazzino magazzinoTest = testApp.getRichiesta(dataProva, 10, 5);
            int numeroTest = 15;
            magazzinoTest.setMaxIndizi(numeroTest);
            assertEquals(numeroTest, magazzinoTest.getMaxIndizi());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test //Attenzione: prevista lettura da file magazzino.txt per il valore attuale, controllare quello prima di impostare il valore corretto per il test
    void testGetMaxTorce() {
        try{
            Magazzino magazzinoTest = testApp.getRichiesta(dataProva, 10, 5);
            int numeroTest = 50;
            assertEquals(numeroTest, magazzinoTest.getMaxTorce());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetMaxTorce() {
        try{
            Magazzino magazzinoTest = testApp.getRichiesta(dataProva, 10, 5);
            int numeroTest = 15;
            magazzinoTest.setMaxTorce(numeroTest);
            assertEquals(numeroTest, magazzinoTest.getMaxTorce());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test //Attenzione: prevista lettura da file magazzino.txt per il valore attuale, controllare quello prima di impostare il valore corretto per il test
    void testGetCostoSingolaAttrezzatura() {
        try{
            Magazzino magazzinoTest = testApp.getRichiesta(dataProva, 10, 5);
            float numeroTest = 0.5f;
            assertEquals(numeroTest, magazzinoTest.getCostoSingolaAttrezzatura());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetCostoSingolaAttrezzatura() {
        try{
            Magazzino magazzinoTest = testApp.getRichiesta(dataProva, 10, 5);
            float numeroTest = 0.8f;
            magazzinoTest.setCostoSingolaAttrezzatura(numeroTest);
            assertEquals(numeroTest, magazzinoTest.getCostoSingolaAttrezzatura());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }



    @Test
    void testSetNome() {
        try{
            Stanza stanzaTest = testApp.getStanzaId(4);
            String nomeTest = "ProvaNuovo";
            stanzaTest.setNome(nomeTest);
            assertEquals(nomeTest, stanzaTest.getNome());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetPrezzo() {
        try{
            Stanza stanzaTest = testApp.getStanzaId(4);
            float prezzoTest = 50.0f;
            assertEquals(prezzoTest, stanzaTest.getPrezzo());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetPrezzo() {
        try{
            float prezzoTest = 100.0f;
            Stanza stanzaTest = testApp.getStanzaId(4);
            stanzaTest.setPrezzo(prezzoTest);
            assertEquals(prezzoTest, stanzaTest.getPrezzo());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetLimiteMassimo() {
        try{
            long limiteTest = 90;
            Stanza stanzaTest = testApp.getStanzaId(4);
            assertEquals(limiteTest, stanzaTest.getLimiteMassimo());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
}
