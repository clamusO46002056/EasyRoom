import org.example.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
La documentazione di jUnit riporta che è preferibile non inserire il modificatore public per i vari test

It is generally recommended to omit the public modifier for test classes, test methods, and lifecycle methods unless
there is a technical reason for doing so – for example, when a test class is extended by a test class in another package.
Another technical reason for making classes and methods public is to simplify testing on the module path when using
the Java Module System.
 */

public class TestGiocatore {
    EasyRoom testApp = EasyRoom.getInstance();
    LinkedList<Giocatore> listaGiocatori;
    DefaultGiocatoreFactory Factory = new DefaultGiocatoreFactory();
    @BeforeEach
    void aggiungiEsempio(){
        Giocatore giocatoreProva = Factory.CreateGiocatore("Nome", "Cognome", "fiscale", "email@email.com", LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        listaGiocatori = testApp.getListaGiocatori();
        listaGiocatori.add(giocatoreProva);
    }

    @AfterEach
    void afterTest (){
        Giocatore giocatoreDaEliminare = testApp.getUtenteCodiceFiscale("fiscale");
        listaGiocatori.remove(giocatoreDaEliminare);
    }

    @Test
    void testGetNome() {
        try{
            Giocatore giocatoreTest = testApp.getUtenteCodiceFiscale("fiscale");
            String nomeTest = "Nome";
            assertEquals(nomeTest, giocatoreTest.getNome());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetCognome() {
        try{
            Giocatore giocatoreTest = testApp.getUtenteCodiceFiscale("fiscale");
            String cognomeTest = "Cognome";
            assertEquals(cognomeTest, giocatoreTest.getCognome());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetCodiceFiscale() {
        try{
            Giocatore giocatoreTest = testApp.getUtenteCodiceFiscale("fiscale");
            String fiscaleTest = "fiscale";
            assertEquals(fiscaleTest, giocatoreTest.getCodiceFiscale());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetEmail() {
        try{
            Giocatore giocatoreTest = testApp.getUtenteCodiceFiscale("fiscale");
            String emailTest = "email@email.com";
            assertEquals(emailTest, giocatoreTest.getEmail());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testGetDataNascita() {
        try{
            Giocatore giocatoreTest = testApp.getUtenteCodiceFiscale("fiscale");
            LocalDate testData = LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            assertEquals(testData, giocatoreTest.getDataNascita());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
}
