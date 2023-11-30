import org.example.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
public class TestAttrezzatura {
    EasyRoom testApp = EasyRoom.getInstance();
    Attrezzatura attrezzaturaProva = new Attrezzatura(15, 10);

    @BeforeEach
    void aggiungiEsempio(){
    }

    @AfterEach
    void afterTest (){
    }

    @Test
    void testGetNumeroIndizi() {
        try{
            int numeroTest = 10;
            assertEquals(numeroTest, attrezzaturaProva.getNumeroIndizi());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }@Test
    void testGetNumeroTorce() {
        try{
            int numeroTest = 15;
            assertEquals(numeroTest, attrezzaturaProva.getNumeroTorce());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetNumeroIndizi() {
        try{
            int numeroTest = 30;
            attrezzaturaProva.setNumeroIndizi(numeroTest);
            assertEquals(numeroTest, attrezzaturaProva.getNumeroIndizi());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
    @Test
    void testSetNumeroTorce() {
        try{
            int numeroTest = 40;
            attrezzaturaProva.setNumeroTorce(numeroTest);
            assertEquals(numeroTest, attrezzaturaProva.getNumeroTorce());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
}