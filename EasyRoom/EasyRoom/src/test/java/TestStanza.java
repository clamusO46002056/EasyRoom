import org.example.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestStanza {
    EasyRoom testApp = EasyRoom.getInstance();
    LinkedList<Stanza> listaStanze;
    @BeforeEach
    void aggiungiEsempio(){
        Stanza stanzaProva = new Stanza(4, "Prova", 50.0f);
        listaStanze = testApp.getListaStanze();
        listaStanze.add(stanzaProva);
    }

    @AfterEach
    void afterTest (){
        Stanza stanzaDaEliminare = testApp.getStanzaId(4);
        listaStanze.remove(stanzaDaEliminare);
    }

    @Test
    void testGetNumero() {
        try{
            Stanza stanzaTest = testApp.getStanzaId(4);
            int numeroTest = 4;
            assertEquals(numeroTest, stanzaTest.getNumero());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetNome() {
        try{
            Stanza stanzaTest = testApp.getStanzaId(4);
            String nomeTest = "Prova";
            assertEquals(nomeTest, stanzaTest.getNome());
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
