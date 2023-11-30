import org.example.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;


public class TestEasyRoom {
    EasyRoom appTest = EasyRoom.getInstance();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    LinkedList<Giocatore> listaGiocatori;
    LinkedList<Magazzino> listaRichieste;
    LinkedList<Prenotazione> listaPrenotazioni;
    LinkedList<Partita> listaPartite;
    LinkedList<Stanza> listaStanze;

    @BeforeAll
    public static void inizioTest(){

    }
    @BeforeEach
    public void preTest(){
        listaGiocatori = appTest.getListaGiocatori();
        listaPrenotazioni = appTest.getListaPrenotazioni();
        listaRichieste = appTest.getListaRichieste();
        listaPartite = appTest.getListaPartiteGiocate();
        listaStanze = appTest.getListaStanze();
        Magazzino magazzino = new Magazzino();
        magazzino.loadMagazzino();
    }
    @AfterEach
    public void afterTest() {
    }

    @Test
    public void testVerificaValiditaGiocatore(){
        Giocatore giocatoreEsistente = appTest.getUtenteCodiceFiscale("fiscale1");
        String fiscaleAssente = "fiscaleFalso";

        //Verifica che un giocatore esista nel sistema (metodo verificaValiditaGiocatore)
        assertTrue(appTest.verificaValiditaGiocatore("fiscale1"));

        //Verifica che un giocatore non esiste nel sistema (metodo verificaValiditaGiocatore)
        assertFalse(appTest.verificaValiditaGiocatore(fiscaleAssente));

    }
    @Test
    public void testCheckDurata(){
        LocalTime inizio = LocalTime.parse("00:00", timeFormatter);
        LocalTime fine1 = LocalTime.parse("01:00", timeFormatter);
        LocalTime fine2 = LocalTime.parse("02:00", timeFormatter);
        System.out.println("Verifica durata legittima partita");
        assertTrue(appTest.checkDurata(inizio, fine1));
        System.out.println("Verifica durata legittima partita ");
        assertFalse(appTest.checkDurata(inizio, fine2));

    }
    @Test
    public void testCheckStanzaLibera(){
        int idPrenotazione = appTest.getMaxId(true);
        Stanza stanza = appTest.getStanzaId(1);
        Giocatore giocatore1 = appTest.getUtenteCodiceFiscale("fiscale1");
        Giocatore giocatore2 = appTest.getUtenteCodiceFiscale("fiscale2");
        LocalDate dataPartita = LocalDate.parse("02/01/2000", dateFormatter);
        LocalTime inizio = LocalTime.parse("00:00", timeFormatter);
        LocalTime fine = LocalTime.parse("01:00", timeFormatter);
        Attrezzatura attrezzatura = new Attrezzatura(1,4);
        Prenotazione prova= new Prenotazione(idPrenotazione,stanza,giocatore1, giocatore2, dataPartita, inizio, fine, attrezzatura, 500.f);
        listaPrenotazioni.add(prova);

        //Verifica disponibilità stanza
        LocalDate dataVerifica = LocalDate.parse("01/01/2000", dateFormatter);
        LocalDate dataVerificaOccupata = LocalDate.parse("02/01/2000", dateFormatter);
        assertTrue(appTest.checkStanzaLibera(1, dataVerifica, inizio));
        //Verifica stanza occupata
        assertFalse(appTest.checkStanzaLibera(1, dataVerificaOccupata, inizio));

    }

    @Test
    public void testUC1() {
        //Verifica inserimento di un giocatore
        String nome = "nome", cognome = "cognome", codiceFiscale ="prova", email="email";
        LocalDate dataNascita = LocalDate.parse("01/01/2000", dateFormatter);
        String inputSimulato = nome+"\n"+cognome+"\n"+codiceFiscale+"\n"+email+"\n"+dataNascita.format(dateFormatter)+"\n";
        InputStream inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        boolean esito = appTest.inserisciUtente();
        System.setIn(System.in);
        Giocatore nuovoGiocatore = appTest.getUtenteCodiceFiscale(codiceFiscale);
        if(!esito){
            assertFalse(esito);
        }else{
            assertTrue(listaGiocatori.contains(nuovoGiocatore));
            appTest.rimuoviGiocatoreDaFile(codiceFiscale);
        }
    }

    @Test
    public void testUC2(){
        //Verifica inserimento prenotazione no richiesta attrezzatura
        String inputSimulatoCorrettoNoAttrezzatura = "fiscale1\nfiscale2\n5\n01/01/2000\n00:00\n01:00\nfalse\n";
        InputStream inputFittizio = new ByteArrayInputStream(inputSimulatoCorrettoNoAttrezzatura.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.inserisciPrenotazione();
        System.setIn(System.in);
        int idPrenotazione = appTest.getMaxId(true);
        Prenotazione prenotazioneDaVerificare = appTest.getPrenotazioneId(idPrenotazione);
        assertTrue(listaPrenotazioni.contains(prenotazioneDaVerificare));
        appTest.rimuoviPrenotazioneDaFile(prenotazioneDaVerificare);
        listaPrenotazioni.remove(prenotazioneDaVerificare);
        Magazzino richiesta;

        //Verifica inserimento prenotazione con richiesta attrezzatura
        inputSimulatoCorrettoNoAttrezzatura = "fiscale1\nfiscale2\n5\n01/01/2000\n00:00\n01:00\ntrue\n1\n2\n";
        inputFittizio = new ByteArrayInputStream(inputSimulatoCorrettoNoAttrezzatura.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.inserisciPrenotazione();
        System.setIn(System.in);
        idPrenotazione = appTest.getMaxId(true);
        prenotazioneDaVerificare = appTest.getPrenotazioneId(idPrenotazione);
        assertTrue(listaPrenotazioni.contains(prenotazioneDaVerificare));
        richiesta = appTest.getRichiesta(prenotazioneDaVerificare.getDataPrenotazione(), prenotazioneDaVerificare.getAttrezzaturaRichiesta().getNumeroTorce(), prenotazioneDaVerificare.getAttrezzaturaRichiesta().getNumeroIndizi());
        listaPrenotazioni.remove(prenotazioneDaVerificare);
        listaRichieste.remove(richiesta);
        appTest.rimuoviPrenotazioneDaFile(prenotazioneDaVerificare);
        appTest.rimuoviRichiestaDaFile(richiesta);

        //Verifica inserimento prenotazione con richiesta attrezzatura non disponibili e continuo della procedura
        inputSimulatoCorrettoNoAttrezzatura = "fiscale1\nfiscale2\n5\n01/01/2000\n00:00\n01:00\ntrue\n100\n200\n1\n";
        inputFittizio = new ByteArrayInputStream(inputSimulatoCorrettoNoAttrezzatura.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.inserisciPrenotazione();
        System.setIn(System.in);
        idPrenotazione = appTest.getMaxId(true);
        prenotazioneDaVerificare = appTest.getPrenotazioneId(idPrenotazione);
        assertTrue(listaPrenotazioni.contains(prenotazioneDaVerificare));
        listaPrenotazioni.remove(prenotazioneDaVerificare);
        appTest.rimuoviPrenotazioneDaFile(prenotazioneDaVerificare);

        //Verifica fallimento inserimento nuova prenotazione
        String inputSimulatoErrato;
            //Caso codiceFiscale 1 o 2 non presente nel sistema
        //inputSimulatoErrato = "fiscale100\nfiscale2\n5\n01/01/2000\n00:00\n01:00\ntrue\n100\n200\n1\n";
            //Caso eccesso richiesta attrezzatura con annullamento dell'operazione
        inputSimulatoErrato = "fiscale1\nfiscale2\n5\n01/01/2000\n00:00\n01:00\ntrue\n100\n200\n0\n";
        inputFittizio = new ByteArrayInputStream(inputSimulatoErrato.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.inserisciPrenotazione();
        System.setIn(System.in);
        //Nel caso in cui la prenotazione fosse stata creata l'Id massimo sarebbe stato incrementato
        //Considerando l'id massimo + 1 vado a ricercare la prenotazione che sarebbe stata generata
        idPrenotazione = appTest.getMaxId(true)+1;
        prenotazioneDaVerificare = appTest.getPrenotazioneId(idPrenotazione);
        assertFalse(listaPrenotazioni.contains(prenotazioneDaVerificare));
    }

    @Test
    public void testUC3(){
        //Creo una prenotazione esempio da modificare/annullare
        String inputSimulatoCorrettoAttrezzatura = "fiscale1\nfiscale2\n5\n01/01/2000\n00:00\n01:00\ntrue\n1\n2\n";
        InputStream inputFittizio = new ByteArrayInputStream(inputSimulatoCorrettoAttrezzatura.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.inserisciPrenotazione();
        System.setIn(System.in);
        int idPrenotazione = appTest.getMaxId(true);
        Prenotazione prenotazioneDaVerificare = appTest.getPrenotazioneId(idPrenotazione);
        assertTrue(listaPrenotazioni.contains(prenotazioneDaVerificare));

        //Verifica fallimento modifica prenotazione (esempio partita > 90 min)
        int oldTorce = prenotazioneDaVerificare.getAttrezzaturaRichiesta().getNumeroTorce();
        int oldIndizi = prenotazioneDaVerificare.getAttrezzaturaRichiesta().getNumeroIndizi();
        int numeroStanza = 6, numeroTorce = 5, numeroIndizi =8;
        LocalDate dataPrenotazione = LocalDate.parse("12/03/2023", dateFormatter);
        LocalTime oraInizio = LocalTime.parse("01:00", timeFormatter);
        LocalTime oraFine = LocalTime.parse("03:00", timeFormatter);
        String inputSimulato = idPrenotazione + "\n" + numeroStanza + "\n" + dataPrenotazione.format(dateFormatter) + "\n"+oraInizio+"\n"+oraFine+"\n" + numeroTorce + "\n" + numeroIndizi + "\n";
        inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.modificaPrenotazione();
        System.setIn(System.in);
        if( oldTorce + oldIndizi >0){
            Magazzino oldRichiesta = new Magazzino(prenotazioneDaVerificare.getDataPrenotazione(), oldTorce, oldIndizi);
            listaRichieste.add(oldRichiesta);
        }
        Prenotazione prenotazioneModificata = appTest.getPrenotazioneId(idPrenotazione);
        assertTrue(listaPrenotazioni.contains(prenotazioneModificata));
        assertEquals(idPrenotazione, prenotazioneModificata.getIdPrenotazione());
        assertNotEquals(numeroStanza, prenotazioneModificata.getIdStanza().getNumero());
        assertNotEquals(dataPrenotazione, prenotazioneModificata.getDataPrenotazione());
        assertNotEquals(oraInizio, prenotazioneModificata.getOraInizio());
        assertNotEquals(oraFine, prenotazioneModificata.getOraFine());
        assertNotEquals(numeroTorce, prenotazioneModificata.getAttrezzaturaRichiesta().getNumeroTorce());
        assertNotEquals(numeroIndizi, prenotazioneModificata.getAttrezzaturaRichiesta().getNumeroIndizi());

        //Verifica modifica prenotazione
        numeroStanza = 6; numeroTorce = 5; numeroIndizi =8;
        dataPrenotazione = LocalDate.parse("12/03/2023", dateFormatter);
        oraInizio = LocalTime.parse("00:00", timeFormatter);
        oraFine = LocalTime.parse("01:00", timeFormatter);
        inputSimulato = idPrenotazione + "\n" + numeroStanza + "\n" + dataPrenotazione.format(dateFormatter) + "\n"+oraInizio+"\n"+oraFine+"\n" + numeroTorce + "\n" + numeroIndizi + "\n";
        inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.modificaPrenotazione();
        System.setIn(System.in);
        prenotazioneModificata = appTest.getPrenotazioneId(idPrenotazione);
        assertTrue(listaPrenotazioni.contains(prenotazioneModificata));
        assertEquals(idPrenotazione, prenotazioneModificata.getIdPrenotazione());
        assertEquals(numeroStanza, prenotazioneModificata.getIdStanza().getNumero());
        assertEquals(dataPrenotazione, prenotazioneModificata.getDataPrenotazione());
        assertEquals(oraInizio, prenotazioneModificata.getOraInizio());
        assertEquals(oraFine, prenotazioneModificata.getOraFine());
        assertEquals(numeroTorce, prenotazioneModificata.getAttrezzaturaRichiesta().getNumeroTorce());
        assertEquals(numeroIndizi, prenotazioneModificata.getAttrezzaturaRichiesta().getNumeroIndizi());

        //Verifica annullamento prenotazione
        inputSimulato = idPrenotazione + "\n";
        inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.rimuoviPrenotazione();
        System.setIn(System.in);
        assertFalse(listaPrenotazioni.contains(prenotazioneModificata));

    }

    @Test
    public void testUC4(){
        try {
            //Verifica inserimento di una stanza
            String nomeStanza = "Test";
            float prezzoStanza = 500.51f;
            String inputSimulato = nomeStanza +"\n" + prezzoStanza + "\n";
            InputStream inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
            System.setIn(inputFittizio);
            appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
            appTest.inserisciStanza();
            System.setIn(System.in);
            int idStanza = appTest.getMaxId(false);
            Stanza nuovaStanza = appTest.getStanzaId(idStanza);
            assertTrue(listaStanze.contains(nuovaStanza));
            assertEquals(nomeStanza, nuovaStanza.getNome());
            assertEquals(prezzoStanza, nuovaStanza.getPrezzo());

            //Verifica modifica stanza
            nomeStanza = "TestModifica";
            prezzoStanza = 27.49f;
            inputSimulato = idStanza +"\n" +nomeStanza +"\n" + prezzoStanza + "\n";
            inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
            System.setIn(inputFittizio);
            appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
            appTest.modificaStanza();
            System.setIn(System.in);
            Stanza stanzaModificata = appTest.getStanzaId(idStanza);
            assertTrue(listaStanze.contains(stanzaModificata));
            assertEquals(nomeStanza, stanzaModificata.getNome());
            assertEquals(prezzoStanza, stanzaModificata.getPrezzo());
            appTest.rimuoviStanzaDaFile(stanzaModificata);
        } catch (Exception e) {
            fail("Errore: " + e.getMessage());
        }
    }

    @Test
    public void testUC5(){
        //Verifica stampa: Caso tutte le stanze
        String inputSimulato = "true\n";
        InputStream inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.visualizzaPrenotazioni();
        System.setIn(System.in);

        //Verifica stampa: Caso singola stanza
        int idStanza = 3;
        inputSimulato = "false\n"+idStanza+"\n";
        inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.visualizzaPrenotazioni();
        System.setIn(System.in);
    }

    @Test
    public void testUC6(){
        //Creo una prenotazione esempio da modificare/annullare
        String inputSimulatoCorretto = "fiscale1\nfiscale2\n5\n01/01/2000\n00:00\n01:00\nfalse\n";
        InputStream inputFittizio = new ByteArrayInputStream(inputSimulatoCorretto.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.inserisciPrenotazione();
        System.setIn(System.in);
        int idPrenotazione = appTest.getMaxId(true);
        Prenotazione prenotazioneDaVerificare = appTest.getPrenotazioneId(idPrenotazione);
        assertTrue(listaPrenotazioni.contains(prenotazioneDaVerificare));

       //Verifica fallimento check-in, codici fiscali non combaciano, prenotazione non esiste
        LocalTime tempoCorrente=LocalTime.now();
            //Caso codici fiscali errati
        //String inputSimulato = idPrenotazione + "\nfiscale4\nfiscale5\n";
            //Caso prenotazione non esistente
        String inputSimulato = "-1\nfiscale1\nfiscale1\n";
        inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.checkin();
        System.setIn(System.in);
        Partita partitaCorrente = appTest.getPartitaId(idPrenotazione);
        assertFalse(listaPartite.contains(partitaCorrente));
        assertNull(partitaCorrente);

        //Verifica check-in prenotazione
        tempoCorrente=LocalTime.now();
        inputSimulato = idPrenotazione + "\nfiscale1\nfiscale2\n";
        inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.checkin();
        System.setIn(System.in);
        partitaCorrente = appTest.getPartitaId(idPrenotazione);
        assertTrue(listaPartite.contains(partitaCorrente));
        assertTrue(partitaCorrente.getIsOccupata());
        assertTrue(tempoCorrente.until(partitaCorrente.getTempoCheckin(), ChronoUnit.MINUTES) <1 );
        appTest.rimuoviPrenotazioneDaFile(prenotazioneDaVerificare);
    }

    @Test
    public void testUC7(){
        //Creo una prenotazione esempio da modificare/annullare
        String inputSimulatoCorrettoAttrezzatura = "fiscale1\nfiscale2\n5\n01/01/2000\n08:00\n09:00\ntrue\n4\n3\n";
        InputStream inputFittizio = new ByteArrayInputStream(inputSimulatoCorrettoAttrezzatura.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.inserisciPrenotazione();
        System.setIn(System.in);
        int idPrenotazione = appTest.getMaxId(true);
        Prenotazione prenotazioneDaVerificare = appTest.getPrenotazioneId(idPrenotazione);
        assertTrue(listaPrenotazioni.contains(prenotazioneDaVerificare));
        //Eseguo il check-in per la prenotazione creata
        LocalTime tempoCorrente=LocalTime.now();
        String inputSimulato = idPrenotazione + "\nfiscale1\nfiscale2\n";
        inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.checkin();
        System.setIn(System.in);
        Partita partitaCorrente = appTest.getPartitaId(idPrenotazione);
        assertTrue(listaPartite.contains(partitaCorrente));
        assertTrue(partitaCorrente.getIsOccupata());
        assertTrue(tempoCorrente.until(partitaCorrente.getTempoCheckin(), ChronoUnit.MINUTES) <1 );

        //Verifico l'operazione di check-out
        Prenotazione prenotazioneVerificata = appTest.getPrenotazioneId(idPrenotazione);
        inputSimulato = idPrenotazione + "\n";
        inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
        System.setIn(inputFittizio);
        appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
        appTest.checkout();
        System.setIn(System.in);
        partitaCorrente = appTest.getPartitaId(idPrenotazione);
        assertTrue(listaPartite.contains(partitaCorrente));
        assertFalse(partitaCorrente.getIsOccupata());
        assertTrue(tempoCorrente.until(partitaCorrente.getTempoCheckout(), ChronoUnit.MINUTES) <1 );
    }

    @Test
    public void testUC8(){

    }

    @Test
    public void testUC9(){
        try{
            int torceNuovo = 79;
            int indiziNuovo = 33;
            float costoNuovo = 0.66f;
            Magazzino magazzino = new Magazzino();
            int torceVecchio = magazzino.getMaxTorce();
            int indiziVecchio = magazzino.getMaxIndizi();
            float costoVecchio = magazzino.getCostoSingolaAttrezzatura();

            //Modifica numero massimo torce
            String inputSimulato = "1\n"+torceNuovo+"\n";
            InputStream inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
            System.setIn(inputFittizio);
            appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
            appTest.gestisciMagazzino();
            System.setIn(System.in);
            magazzino.loadMagazzino();
            assertEquals(torceNuovo, magazzino.getMaxTorce());
            assertNotEquals(torceVecchio, magazzino.getMaxTorce());

            //Modifica numero massimo indizi
            inputSimulato = "2\n"+indiziNuovo+"\n";
            inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
            System.setIn(inputFittizio);
            appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
            appTest.gestisciMagazzino();
            System.setIn(System.in);
            magazzino.loadMagazzino();
            assertEquals(indiziNuovo, magazzino.getMaxIndizi());
            assertNotEquals(indiziVecchio, magazzino.getMaxIndizi());

            //Modifica costo singola attrezzatura
            inputSimulato = "3\n"+costoNuovo+"\n";
            inputFittizio = new ByteArrayInputStream(inputSimulato.getBytes());
            System.setIn(inputFittizio);
            appTest.tastiera = new BufferedReader(new InputStreamReader((System.in)));
            appTest.gestisciMagazzino();
            System.setIn(System.in);
            magazzino.loadMagazzino();
            assertEquals(costoNuovo, magazzino.getCostoSingolaAttrezzatura());
            assertNotEquals(costoVecchio, magazzino.getCostoSingolaAttrezzatura());

            //Ripristino
            String riga = torceVecchio + "," + indiziVecchio + "," + costoVecchio;
            BufferedWriter magazzinoDaScrivere = new BufferedWriter(new FileWriter("magazzino.txt"));
            magazzinoDaScrivere.write(riga);
            magazzinoDaScrivere.newLine();
            magazzinoDaScrivere.close();
        }catch (IOException e){
            System.out.println(e);
        }

    }
}
