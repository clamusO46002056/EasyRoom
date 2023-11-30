package org.example;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Objects;

public class EasyRoom {
    public BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DefaultGiocatoreFactory giocatoreFactory;
    private LinkedList<Giocatore> listaGiocatori;
    private LinkedList<Stanza> listaStanze;
    private LinkedList<Prenotazione> listaPrenotazioni;
    private LinkedList<Magazzino> listaRichieste;
    private LinkedList<Partita> listaPartiteGiocate;
    private static EasyRoom easyroom;
    private LinkedList<Observer> observers = new LinkedList<>();
    public EasyRoom() {
        this.listaGiocatori = new LinkedList<>();
        this.listaStanze = new LinkedList<>();
        this.listaPrenotazioni = new LinkedList<>();
        this.listaRichieste = new LinkedList<>();
        this.listaPartiteGiocate = new LinkedList<>();
        this.giocatoreFactory = new DefaultGiocatoreFactory();
        loadUtentiRegistrati();
        loadStanze();
        loadPrenotazioni();
        loadRichieste();
        loadPartite();
    }
    //Pattern GoF Singleton
    public static EasyRoom getInstance(){
        if (easyroom==null) easyroom = new EasyRoom();
        return easyroom;
    }

    //Pattern GoF Observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    public void notifyObservers(int idPrenotazione, LinkedList<String> codiciFiscali) {
        for (Observer observer : observers) {
            observer.update(idPrenotazione, codiciFiscali);
        }
    }
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    //Caricare in memoria i dati persistenti
    public void loadUtentiRegistrati() { // Creo in memoria la LinkedList dei giocatori già registrati
        listaGiocatori.clear();
        try {
            BufferedReader elencoGiocatoriLetto = new BufferedReader(new FileReader("listagiocatori.txt"));
            String linea;
            while ((linea = elencoGiocatoriLetto.readLine()) != null) {
                if(!linea.isEmpty()){
                    String[] datiGiocatore = linea.split(",");
                    if (datiGiocatore.length >= 5) {
                        String nome = datiGiocatore[0];
                        String cognome = datiGiocatore[1];
                        String codiceFiscale = datiGiocatore[2];
                        String email = datiGiocatore[3];
                        LocalDate dataNascita = LocalDate.parse(datiGiocatore[4], dateFormatter);
                        //Pattern GoF Factory
                        listaGiocatori.add(giocatoreFactory.CreateGiocatore(nome, cognome, codiceFiscale, email, dataNascita));
                        //listaGiocatori.add(new Giocatore(nome, cognome, codiceFiscale, email, dataNascita));
                    }
                }

            }
            elencoGiocatoriLetto.close();
            System.out.println("Elenco giocatori caricato in memoria");
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'inserimento");
        }
    }
    public void loadStanze() {
        try {
            BufferedReader elencoStanzeLetto = new BufferedReader(new FileReader("listastanze.txt"));
            String linea;
            while ((linea = elencoStanzeLetto.readLine()) != null) {
                if(!linea.isEmpty()){
                    String[] datiStanza = linea.split(",");
                    if (datiStanza.length >= 2) {
                        int idStanza = Integer.parseInt(datiStanza[0]);
                        String nome = datiStanza[1];
                        float prezzo = Float.parseFloat(datiStanza[2]);
                        listaStanze.add(new Stanza(idStanza, nome, prezzo));
                    }
                }
            }
            elencoStanzeLetto.close();
            System.out.println("Elenco stanze caricato in memoria");
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'inserimento");
        }
    }
    public void loadPrenotazioni() {
        try {
            BufferedReader elencoPrenotazioniLetto = new BufferedReader(new FileReader("listaprenotazioni.txt"));
            String linea;
            while ((linea = elencoPrenotazioniLetto.readLine()) != null) {
                if(!linea.isEmpty()){
                    String[] datiPrenotazione = linea.split(",");
                    if (datiPrenotazione.length >= 9) {
                        int idPrenotazione = Integer.parseInt(datiPrenotazione[0]);
                        int idStanza = Integer.parseInt(datiPrenotazione[1]);
                        String codiceFiscale1 = datiPrenotazione[2];
                        String codiceFiscale2 = datiPrenotazione[3];
                        LocalDate dataPrenotazione = LocalDate.parse(datiPrenotazione[4], dateFormatter);
                        LocalTime tempoInizio = LocalTime.parse(datiPrenotazione[5], timeFormatter);

                        LocalTime tempoFine = LocalTime.parse(datiPrenotazione[6], timeFormatter);
                        Attrezzatura richiestaAttrezzatura = new Attrezzatura(Integer.parseInt(datiPrenotazione[7]),Integer.parseInt(datiPrenotazione[8]));

                        float prezzo = Float.parseFloat(datiPrenotazione[9]);
                        Giocatore Utente1 = getUtenteCodiceFiscale(codiceFiscale1);
                        Giocatore Utente2 = getUtenteCodiceFiscale(codiceFiscale2);
                        Stanza stanzaPrenotata = getStanzaId(idStanza);
                        listaPrenotazioni.add(new Prenotazione(idPrenotazione, stanzaPrenotata, Utente1, Utente2, dataPrenotazione, tempoInizio, tempoFine, richiestaAttrezzatura, prezzo));
                    }
                }

            }
            elencoPrenotazioniLetto.close();
            System.out.println("Elenco prenotazioni caricato in memoria");
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'inserimento");
        }
    }
    public void loadRichieste(){
        try {
            BufferedReader elencoRichiesteLetto = new BufferedReader(new FileReader("listarichieste.txt"));
            String linea;
            while ((linea = elencoRichiesteLetto.readLine()) != null) {
                if(!linea.isEmpty()){
                    String[] datiRichieste = linea.split(",");
                    if (datiRichieste.length >= 2) {
                        LocalDate dataPrenotazione = LocalDate.parse(datiRichieste[0], dateFormatter);
                        int numeroTorce = Integer.parseInt(datiRichieste[1]);
                        int numeroIndizi = Integer.parseInt(datiRichieste[2]);
                        listaRichieste.add(new Magazzino(dataPrenotazione, numeroTorce, numeroIndizi));
                    }
                }
            }
            elencoRichiesteLetto.close();
            System.out.println("Elenco richieste caricato in memoria");
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'inserimento delle richieste");
        }
    }
    public void loadPartite(){
        try {
            BufferedReader elencoPartiteLetto = new BufferedReader(new FileReader("listapartite.txt"));
            String linea;
            while ((linea = elencoPartiteLetto.readLine()) != null) {
                if(!linea.isEmpty()){
                    String[] datiPartita = linea.split(",");
                    if (datiPartita.length >= 12) {
                        int idPrenotazione = Integer.parseInt(datiPartita[0]);
                        int idStanza = Integer.parseInt(datiPartita[1]);
                        String codiceFiscale1 = datiPartita[2];
                        String codiceFiscale2 = datiPartita[3];
                        LocalDate dataPrenotazione = LocalDate.parse(datiPartita[4], dateFormatter);
                        LocalTime tempoInizio = LocalTime.parse(datiPartita[5], timeFormatter);

                        LocalTime tempoFine = LocalTime.parse(datiPartita[6], timeFormatter);
                        Attrezzatura richiestaAttrezzatura = new Attrezzatura(Integer.parseInt(datiPartita[7]),Integer.parseInt(datiPartita[8]));

                        float prezzo = Float.parseFloat(datiPartita[9]);
                        boolean isOccupata = Boolean.parseBoolean(datiPartita[10]);
                        LocalTime tempoCheckin = LocalTime.parse(datiPartita[11], timeFormatter);
                        LocalTime tempoCheckout = LocalTime.parse(datiPartita[12], timeFormatter);
                        Giocatore Utente1 = getUtenteCodiceFiscale(codiceFiscale1);
                        Giocatore Utente2 = getUtenteCodiceFiscale(codiceFiscale2);
                        Stanza stanzaPrenotata = getStanzaId(idStanza);
                        Prenotazione prenotazione = new Prenotazione(idPrenotazione, stanzaPrenotata, Utente1, Utente2, dataPrenotazione, tempoInizio, tempoFine, richiestaAttrezzatura, prezzo);
                        Partita partitaDaAggiungere = new Partita(prenotazione, tempoCheckin, tempoCheckout);
                        partitaDaAggiungere.setIsOccupata(isOccupata);
                        listaPartiteGiocate.add(partitaDaAggiungere);
                    }
                }

            }
            elencoPartiteLetto.close();
            System.out.println("Elenco partite caricato in memoria");
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'inserimento");
        }
    }

    //UC1
    public boolean inserisciUtente() {
         try {
             String nome;
             String cognome;
             String codiceFiscale;
             String email;
             String supporto;
             boolean giaPresente;
             System.out.println("Inserisci il nome del giocatore");
             nome = tastiera.readLine();
             System.out.println("Inserisci il cognome del giocatore");
             cognome = tastiera.readLine();
             System.out.println("Inserisci il codice fiscale del giocatore");
             codiceFiscale = tastiera.readLine();
             System.out.println("Inserisci la email del giocatore");
             email = tastiera.readLine();
             System.out.println("Inserisci la data di nascita del giocatore nel formato dd/MM/yyyy");
             supporto = tastiera.readLine();
             LocalDate dataNascita = LocalDate.parse(supporto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
             giaPresente = verificaValiditaGiocatore(codiceFiscale);
             if(giaPresente){
                 System.out.println("Il giocatore è già presente nel sistema");
                 return false;
             }
             //Pattern GoF Factory
             Giocatore nuovoGiocatore = giocatoreFactory.CreateGiocatore(nome, cognome, codiceFiscale, email, dataNascita);
             //Se non è presente lo aggiungo al file e ricreo la lista dei giocatori
             salvaGiocatoreSuFile(nuovoGiocatore);
             listaGiocatori.clear();
             loadUtentiRegistrati();

         } catch (IOException e) {
             System.out.println("Si è verificato un errore nell'inserimento");
         }
        return true;
     }
    //UC2
    public void inserisciPrenotazione(){
        try{
            boolean esitoDurata = false;
            boolean stanzaLibera = false;
            int idStanza = -1;
            LocalDate dataPrenotazione = LocalDate.parse("01/01/1999", dateFormatter);
            LocalTime tempoInizio = LocalTime.parse("00:00", timeFormatter);
            LocalTime tempoFine = LocalTime.parse("00:00", timeFormatter);
            //Inserisco il primo utente e verifico la sua presenza nel sistema
            System.out.println("Inserisci il codice fiscale del primo partecipante");
            String codiceFiscale1 = tastiera.readLine();
            if(!verificaValiditaGiocatore(codiceFiscale1)){
                System.out.println("Il codice fiscale " + codiceFiscale1 + " non è associato a nessun giocatore del sistema");
                return;
            }
            //Inserisco il secondo utente e verifico la sua presenza nel sistema
            System.out.println("Inserisci il codice fiscale del secondo partecipante");
            String codiceFiscale2 = tastiera.readLine();
            if(!verificaValiditaGiocatore(codiceFiscale2)){
                System.out.println("Il codice fiscale " + codiceFiscale2 + " non è associato a nessun giocatore del sistema");
                return;
            }
            //Verifico che la partita non duri più del dovuto e che la stanza sia libera per quello slot temporale
            //Altrimenti chiedo all'Utente di inserire dei dati fino a quando non sono corretti
            while(!esitoDurata || !stanzaLibera){
                System.out.println("Inserisci il numero della stanza che vuoi prenotare");
                stampaListaStanze();
                idStanza = Integer.parseInt(tastiera.readLine());
                System.out.println("Inserisci la data nel formato gg/MM/yyyy");
                dataPrenotazione = LocalDate.parse(tastiera.readLine(), dateFormatter);
                System.out.println("Inserisci l'orario di inizio della partita nel formato HH:mm");
                tempoInizio = LocalTime.parse(tastiera.readLine(), timeFormatter);
                System.out.println("Inserisci l'orario di fine della partita nel formato HH:mm");
                tempoFine = LocalTime.parse(tastiera.readLine(), timeFormatter);
                esitoDurata = checkDurata(tempoInizio, tempoFine);
                stanzaLibera = checkStanzaLibera(idStanza, dataPrenotazione,tempoInizio);
            }

            //Gestione noleggio attrezzatura
            System.out.println("Vuoi noleggiare dell'attrezzatura o comprare degli indizi? true/false");
            boolean richiestaAttrezzatura = Boolean.parseBoolean(tastiera.readLine());
            Attrezzatura attrezzaturaNoleggiata = new Attrezzatura(0,0);
            if(richiestaAttrezzatura){
                System.out.println("Inserisci il numero di torce che vuoi noleggiare");
                int numeroTorce = Integer.parseInt(tastiera.readLine());
                System.out.println("Inserisci il numero di indizi che vuoi acquistare");
                int numeroIndizi = Integer.parseInt(tastiera.readLine());
                boolean attrezzaturaDisponibile = checkAttrezzatura(dataPrenotazione, numeroTorce, numeroIndizi);
                if(!attrezzaturaDisponibile){
                    System.out.println("Inserisci 1 per continuare con la prenotazione senza prenotare attrezzatura, altrimenti un qualsiasi numero per annullare");
                    if(Integer.parseInt(tastiera.readLine())!=1){
                        return;
                    }
                } else {
                    attrezzaturaNoleggiata.setNumeroTorce(numeroTorce);
                    attrezzaturaNoleggiata.setNumeroIndizi(numeroIndizi);
                    Magazzino richiesta = new Magazzino(dataPrenotazione, numeroTorce, numeroIndizi);
                    salvaRichiestaSuFile(richiesta);
                    listaRichieste.clear();
                    loadRichieste();
                }
            }
            //I dati sono validi ed esiste uno slot temporale corretto
            Stanza stanzaPrenotata = getStanzaId(idStanza);
            Giocatore primoGiocatore = getUtenteCodiceFiscale(codiceFiscale1);
            Giocatore secondoGiocatore = getUtenteCodiceFiscale(codiceFiscale2);
            float durataPartita = tempoInizio.until(tempoFine, ChronoUnit.HOURS);
            float prezzo = prospettoPrezzo(stanzaPrenotata, attrezzaturaNoleggiata, durataPartita);

            int idPrenotazione = (getMaxId(true))+1;
            Prenotazione nuovaPrenotazione = new Prenotazione(idPrenotazione, stanzaPrenotata, primoGiocatore, secondoGiocatore, dataPrenotazione, tempoInizio, tempoFine, attrezzaturaNoleggiata, prezzo);
            listaPrenotazioni.add(nuovaPrenotazione);
            salvaPrenotazioneSuFile(nuovaPrenotazione);
            listaPrenotazioni.clear();
            loadPrenotazioni();

            System.out.println("Il costo della prenotazione è: " + nuovaPrenotazione.getCosto());

            LinkedList<String> codiciFiscali = new LinkedList<>();
            codiciFiscali.add(nuovaPrenotazione.getGiocatore1().getCodiceFiscale());
            codiciFiscali.add(nuovaPrenotazione.getGiocatore2().getCodiceFiscale());
            notifyObservers(nuovaPrenotazione.getIdPrenotazione(), codiciFiscali);

        }catch (IOException e){
            System.out.println("Si è verificato un errore nell'inserimento: " + e);
        }
    }
    //UC3
    public void modificaPrenotazione(){
        try{
            boolean validita = true;
            System.out.println("Inserisci l'id della prenotazione che vuoi modificare");
            int idPrenotazioneScelta = Integer.parseInt(tastiera.readLine());
            Prenotazione prenotazioneDaModificare = getPrenotazioneId(idPrenotazioneScelta);
            if(prenotazioneDaModificare==null){
                System.out.println("La prenotazione scelta non esiste");
                return;
            }
            System.out.println("Prenotazione scelta:");
            System.out.println(prenotazioneDaModificare);
            Magazzino richiestaDaModificare = getRichiesta(prenotazioneDaModificare.getDataPrenotazione(), prenotazioneDaModificare.getAttrezzaturaRichiesta().getNumeroTorce(), prenotazioneDaModificare.getAttrezzaturaRichiesta().getNumeroIndizi());

            //Rimuovo i dati per considerare la prenotazione attuale nulla
            listaPrenotazioni.remove(prenotazioneDaModificare);
            if(richiestaDaModificare!=null)
                listaRichieste.remove(richiestaDaModificare);

            //Inserisco i nuovi dati
            System.out.println("Inserisci l'id della stanza");
            int stanzaNuovo = Integer.parseInt(tastiera.readLine());
            System.out.println("Inserisci la nuova data dd/MM/yyyy");
            LocalDate dataNuovo = LocalDate.parse(tastiera.readLine(), dateFormatter);
            System.out.println("Inserisci l'ora di inizio HH:mm, durata massima partita 90 min");
            LocalTime inizioNuovo = LocalTime.parse(tastiera.readLine(), timeFormatter);
            System.out.println("Inserisci l'ora di fine HH:mm, durata massima partita 90 min");
            LocalTime fineNuovo = LocalTime.parse(tastiera.readLine(), timeFormatter);
            System.out.println("Inserisci il numero di torce");
            int torceNuovo = Integer.parseInt(tastiera.readLine());
            System.out.println("Inserisci il numero di indizi");
            int indiziNuovo = Integer.parseInt(tastiera.readLine());
            float durataNuova = inizioNuovo.until(fineNuovo, ChronoUnit.HOURS);
            //Verifico che le modifiche alla prenotazione siano valide
            if(!checkDurata(inizioNuovo, fineNuovo)){
                System.out.println("La durata deve essere massimo 90 minuti, ritorno al menu");
                validita = false;
            }
            if(!checkStanzaLibera(stanzaNuovo, dataNuovo, inizioNuovo)){
                System.out.println("Impossibile modificare la prenotazione, la stanza scelta nel periodo inserito è occupata");
                validita = false;
            }
            if(!checkAttrezzatura(dataNuovo, torceNuovo, indiziNuovo)){
                System.out.println("Impossibile modificare la prenotazione, attrezzature non disponibili");
                validita = false;
            }

            if(validita){
                //Elimino i dati vecchi dal file
                rimuoviPrenotazioneDaFile(prenotazioneDaModificare);
                if(richiestaDaModificare!=null)
                    rimuoviRichiestaDaFile(richiestaDaModificare);
                //A questo punto la prenotazione dovrebbe poter essere modificata e aggiornata
                richiestaDaModificare = new Magazzino(dataNuovo, torceNuovo, indiziNuovo);
                prenotazioneDaModificare.setStanzaScelta(getStanzaId(stanzaNuovo));
                prenotazioneDaModificare.setDataPrenotazione(dataNuovo);
                prenotazioneDaModificare.setOraInizio(inizioNuovo);
                prenotazioneDaModificare.setOraFine(fineNuovo);
                prenotazioneDaModificare.getAttrezzaturaRichiesta().setNumeroTorce(torceNuovo);
                prenotazioneDaModificare.getAttrezzaturaRichiesta().setNumeroIndizi(indiziNuovo);
                float prezzoNuovo = prospettoPrezzo(getStanzaId(stanzaNuovo), prenotazioneDaModificare.getAttrezzaturaRichiesta(), durataNuova);
                prenotazioneDaModificare.setCosto(prezzoNuovo);
                salvaPrenotazioneSuFile(prenotazioneDaModificare);
                if(torceNuovo+indiziNuovo>0){
                    listaRichieste.add(richiestaDaModificare);
                    salvaRichiestaSuFile(richiestaDaModificare);
                }
                LinkedList<String> codiciFiscali = new LinkedList<>();
                codiciFiscali.add(prenotazioneDaModificare.getGiocatore1().getCodiceFiscale());
                codiciFiscali.add(prenotazioneDaModificare.getGiocatore2().getCodiceFiscale());
                notifyObservers(prenotazioneDaModificare.getIdPrenotazione(), codiciFiscali);
            }
            listaPrenotazioni.add(prenotazioneDaModificare);
        }catch(IOException e){
            System.out.println(e);
        }
    }
    //UC3
    public float rimuoviPrenotazione(){
        try{
            System.out.println("Inserisci l'id della prenotazione che vuoi annullare");
            int idPrenotazioneScelta = Integer.parseInt(tastiera.readLine());
            Prenotazione prenotazioneDaEliminare = getPrenotazioneId(idPrenotazioneScelta);
            if(prenotazioneDaEliminare==null){
                System.out.println("La prenotazione scelta non esiste");
                return 0.0f;
            }
            Magazzino richiestaDaEliminare = getRichiesta(prenotazioneDaEliminare.getDataPrenotazione(), prenotazioneDaEliminare.getAttrezzaturaRichiesta().getNumeroTorce(), prenotazioneDaEliminare.getAttrezzaturaRichiesta().getNumeroIndizi());
            float rimborso = prenotazioneDaEliminare.getCosto();
            LocalDate dataPrenotazione= prenotazioneDaEliminare.getDataPrenotazione();
            LocalTime inizioPrenotazione = prenotazioneDaEliminare.getOraInizio();
            if(!Objects.isNull(richiestaDaEliminare)){
                listaRichieste.remove(richiestaDaEliminare);
                rimuoviRichiestaDaFile(richiestaDaEliminare);
            }
            listaPrenotazioni.remove(prenotazioneDaEliminare);
            rimuoviPrenotazioneDaFile(prenotazioneDaEliminare);
            if(LocalTime.now().until(inizioPrenotazione, ChronoUnit.HOURS)<24){
                rimborso = 0.5f * rimborso;
            }
            return rimborso;


        }catch (IOException e){
            System.out.println(e);
            return 0.0f;
        }
    }
    //UC4
    public void inserisciStanza(){
        try {
            int numeroStanza;
            String nome;
            float prezzo;
            numeroStanza = getMaxId(false) + 1;
            System.out.println("Inserisci il nome della stanza");
            nome = tastiera.readLine();
            System.out.println("Inserisci il prezzo/h della stanza");
            prezzo = Float.parseFloat(tastiera.readLine());
            Stanza nuovaStanza = new Stanza(numeroStanza,nome,prezzo);
            //Aggiungo al file e ricreo la lista delle stanze
            salvaStanzaSuFile(nuovaStanza);
            listaStanze.clear();
            loadStanze();

        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'inserimento dei dati");
        }
    }
    //UC4
    public void modificaStanza(){
        try{
            System.out.println("Inserisci il numero della stanza che vuoi modificare");
            int numeroStanzaScelta = Integer.parseInt(tastiera.readLine());
            Stanza stanzaDaModificare = getStanzaId(numeroStanzaScelta);
            if(stanzaDaModificare==null){
                System.out.println("La stanza scelta non esiste");
                return;
            }
            System.out.println("Stanza scelta: " + stanzaDaModificare);

            //Rimuovo i dati per considerare la stanza attuale nulla
            listaStanze.remove(stanzaDaModificare);
            //Inserisco i nuovi dati
            System.out.println("Inserisci il nuovo nome della stanza");
            String nomeNuovo = tastiera.readLine();
            System.out.println("Inserisci il nuovo prezzo/h");
            float prezzoNuovo = Float.parseFloat(tastiera.readLine());

            rimuoviStanzaDaFile(stanzaDaModificare);
            stanzaDaModificare.setNome(nomeNuovo);
            stanzaDaModificare.setPrezzo(prezzoNuovo);

            salvaStanzaSuFile(stanzaDaModificare);
            listaStanze.add(stanzaDaModificare);
        }catch(IOException e){
            System.out.println(e);
        }
    }
    //UC5
    public void visualizzaPrenotazioni(){
        try {
            boolean singolaStanza;
            System.out.println("Vuoi visualizzare le prenotazioni di tutte le stanze? true/false");
            singolaStanza = Boolean.parseBoolean(tastiera.readLine());
            if(singolaStanza){
                stampaListaPrenotazioni();
            } else{
                System.out.println("Inserisci il numero di stanza di cui vuoi vedere le prenotazioni");
                int stanzaScelta = Integer.parseInt(tastiera.readLine());
                stampaListaPrenotazioni(stanzaScelta);
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }
    //UC6
    public void checkin(){
        try{
            stampaListaPrenotazioni();
            System.out.println("Inserisci l'ID della prenotazione di cui vuoi eseguire il check-in");
            int idPrenotazione = Integer.parseInt(tastiera.readLine());
            Prenotazione prenotazioneDaVerificare = getPrenotazioneId(idPrenotazione);
            if(prenotazioneDaVerificare==null){
                System.out.println("La prenotazione scelta non esiste, ritorno al menu");
                return;
            }
            //Effettuo la verifica dei dati inseriti per concedere l'accesso alla stanza
            System.out.println("Inserisci il codice fiscale del giocatore 1");
            String fiscale1 = tastiera.readLine();
            System.out.println("Inserisci il codice fiscale del giocatore 2");
            String fiscale2 = tastiera.readLine();
            String fiscalePrenotazione1 = prenotazioneDaVerificare.getGiocatore1().getCodiceFiscale();
            String fiscalePrenotazione2 = prenotazioneDaVerificare.getGiocatore2().getCodiceFiscale();
            if(!(fiscale1.equalsIgnoreCase(fiscalePrenotazione1)) && !(fiscale2.equalsIgnoreCase(fiscalePrenotazione2))){
                System.out.println("I giocatori non corrispondono, ritorno al menu");
                return;
            }
            //I giocatori sono verificati, possono entrare nella stanza
            LocalTime orarioCheckin = LocalTime.parse(LocalTime.now().format(timeFormatter), timeFormatter);
            Partita partitaCorrente = new Partita(prenotazioneDaVerificare, orarioCheckin, orarioCheckin);
            partitaCorrente.setIsOccupata(true);
            System.out.println(partitaCorrente);
            listaPartiteGiocate.add(partitaCorrente);

        }catch(IOException e){
            System.out.println("Errore nell'inserimento dei dati durante il check-in: " + e);
        }

    }
    //UC7
    public void checkout(){
        try{
            stampaListaPartiteInCorso();
            System.out.println("Inserisci l'ID della prenotazione di cui vuoi eseguire il check-out");
            int idPrenotazione = Integer.parseInt(tastiera.readLine());
            Partita partitaDaVerificare = getPartitaId(idPrenotazione);
            if(partitaDaVerificare==null){
                System.out.println("La partita scelta non esiste, ritorno al menu");
                return;
            }
            LocalTime orarioCheckout = LocalTime.parse(LocalTime.now().format(timeFormatter), timeFormatter);
            partitaDaVerificare.setTempoCheckout(orarioCheckout);
            partitaDaVerificare.setIsOccupata(false);
            //Elimino la partita dalla lista delle prenotazioni e l'eventuale richiesta di attrezzatura
            Prenotazione prenotazioneDaRimuovere = getPrenotazioneId(idPrenotazione);
            listaPrenotazioni.remove(prenotazioneDaRimuovere);
            rimuoviPrenotazioneDaFile(prenotazioneDaRimuovere);

            Magazzino richiestaDaEliminare = getRichiesta(prenotazioneDaRimuovere.getDataPrenotazione(), prenotazioneDaRimuovere.getAttrezzaturaRichiesta().getNumeroTorce(), prenotazioneDaRimuovere.getAttrezzaturaRichiesta().getNumeroIndizi());
            if(!Objects.isNull(richiestaDaEliminare)){
                listaRichieste.remove(richiestaDaEliminare);
                rimuoviRichiestaDaFile(richiestaDaEliminare);
            }
            //Memorizzo la partita giocata su file di testo (persistenza dei dati)
            salvaPartitaSuFile(partitaDaVerificare);

        }catch(IOException e){
            System.out.println("Errore nell'inserimento dei dati durante il check-in: " + e);
        }
    }
    //UC8
    public void contaPartite(int numeroStanza){
        int numeroPartite=0;
        Stanza stanzaScelta = getStanzaId(numeroStanza);
        if(stanzaScelta == null){
            System.out.println("La stanza scelta non esiste!");
            return;
        }
        for(Partita partitaCorrente : listaPartiteGiocate){
            if(numeroStanza == partitaCorrente.getIdStanza().getNumero()){
                numeroPartite++;
            }
        }
        System.out.println("Il numero totale di partite giocate nella stanza numero " + numeroStanza + " è "+numeroPartite);
    }
    //UC9
    public void gestisciMagazzino(){
        try{
            System.out.println("Gestione magazzino:");
            System.out.println("1) Modifica numero massimo torce disponibili");
            System.out.println("2) Modifica numero massimo indizi disponibili");
            System.out.println("3) Modifica prezzo della singola attrezzatura");
            System.out.println("4) Visualizza per un determinato giorno l'attrezzatura libera");
            Magazzino magazzino = new Magazzino();
            int torceNuovo = magazzino.getMaxTorce();
            int indiziNuovo = magazzino.getMaxIndizi();
            float prezzoNuovo = magazzino.getCostoSingolaAttrezzatura();
            int scelta = Integer.parseInt(tastiera.readLine());
            switch (scelta){
                case 1:
                    System.out.println("Inserisci il nuovo numero massimo di torce");
                    torceNuovo = Integer.parseInt(tastiera.readLine());
                    magazzino.setMaxTorce(torceNuovo);
                    break;
                case 2:
                    System.out.println("Inserisci il nuovo numero massimo di indizi");
                    indiziNuovo = Integer.parseInt(tastiera.readLine());
                    magazzino.setMaxIndizi(indiziNuovo);
                    break;
                case 3:
                    System.out.println("Inserisci il nuovo costo del singolo pezzo di attrezzatura");
                    prezzoNuovo = Float.parseFloat(tastiera.readLine());
                    magazzino.setCostoSingolaAttrezzatura(prezzoNuovo);
                    break;
                case 4:
                    System.out.println("Inserisci la data nel formato dd/MM/yyyy");
                    LocalDate dataScelta = LocalDate.parse(tastiera.readLine(), dateFormatter);
                    Attrezzatura attrezzaturaLibera = calcolaAttrezzaturaLibera(dataScelta, magazzino);
                    int torceDisponibili = attrezzaturaLibera.getNumeroTorce();
                    int indiziDisponibili = attrezzaturaLibera.getNumeroIndizi();
                    System.out.println("Il numero disponibile di torce è: " + torceDisponibili + " ,il numero di indizi disponibili è: " +indiziDisponibili);
                    break;
                default:
                    System.out.println("Scelta non valida, ritorno al menu");
                    return;
            }
            String riga = torceNuovo + "," + indiziNuovo + "," + prezzoNuovo;
            BufferedWriter magazzinoDaScrivere = new BufferedWriter(new FileWriter("magazzino.txt"));
            magazzinoDaScrivere.write(riga);
            magazzinoDaScrivere.newLine();
            magazzinoDaScrivere.close();
            magazzino.loadMagazzino();
        }catch(IOException e){
            System.out.println("Errore nell'inserimento nel magazzino: "+e);
        }
    }

    //Stampare a schermo elementi in memoria
    public void stampaListaGiocatori() {
        for (Giocatore giocatoreCorrente : listaGiocatori)
            System.out.println(giocatoreCorrente);
    }
    public void stampaListaStanze() {
        for (Stanza stanzaCorrente : listaStanze)
            System.out.println(stanzaCorrente);
    }
    public void stampaListaPrenotazioni(){
        for (Prenotazione prenotazioneCorrente : listaPrenotazioni)
            System.out.println(prenotazioneCorrente);
    }
    public void stampaListaPrenotazioni(int numeroStanza){
        for (Prenotazione prenotazioneCorrente : listaPrenotazioni){
            if(numeroStanza == prenotazioneCorrente.getIdStanza().getNumero()){
                System.out.println(prenotazioneCorrente);
            }
        }
    }
    public void stampaListaRichieste(){
        for (Magazzino richiestaCorrente : listaRichieste)
            System.out.println(richiestaCorrente);
    }
    public void stampaListaPartite(){
        for (Partita partitaCorrente : listaPartiteGiocate){
            System.out.println(partitaCorrente);
        }
    }
    public void stampaListaPartiteInCorso(){
        for (Partita partitaCorrente : listaPartiteGiocate){
            if(partitaCorrente.getTempoCheckin().equals(partitaCorrente.getTempoCheckout())){
                System.out.println(partitaCorrente);
            }
        }
    }

    //Ricerca dati
    public Giocatore getUtenteCodiceFiscale(String codiceFiscale){
        for(Giocatore giocatoreCorrente : listaGiocatori){
            if(Objects.equals(giocatoreCorrente.getCodiceFiscale(), codiceFiscale)){
                return giocatoreCorrente;
            }
        }
        return null;
    }
    public Stanza getStanzaId(int idStanza){
        for(Stanza stanzaCorrente : listaStanze){
            if(Objects.equals(stanzaCorrente.getNumero(), idStanza)){
                return stanzaCorrente;
            }
        }
        return null;
    }
    public Prenotazione getPrenotazioneId (int idPrenotazione){
        for(Prenotazione prenotazioneCorrente : listaPrenotazioni){
            if(Objects.equals(prenotazioneCorrente.getIdPrenotazione(), idPrenotazione)){
                return prenotazioneCorrente;
            }
        }
        return null;
    }
    public Magazzino getRichiesta (LocalDate dataPrenotazione, int numeroTorce, int numeroIndizi){
        for(Magazzino richiestaCorrente : listaRichieste){
            if(dataPrenotazione.isEqual(richiestaCorrente.getDataPartita()) && (numeroTorce == richiestaCorrente.getNumeroTorce()) && (numeroIndizi== richiestaCorrente.getNumeroIndizi())){
                return richiestaCorrente;
            }
        }
        return null;
    }
    public Partita getPartitaId (int idPartita){
        for(Partita partitaCorrente : listaPartiteGiocate){
            if(Objects.equals(partitaCorrente.getIdPrenotazione(), idPartita)){
                return partitaCorrente;
            }
        }
        return null;
    }

    //Persistenza dei dati
    private void salvaGiocatoreSuFile(Giocatore giocatoreDaSalvare) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("listagiocatori.txt", true));
            String riga = giocatoreDaSalvare.getNome() + "," + giocatoreDaSalvare.getCognome() + "," + giocatoreDaSalvare.getCodiceFiscale() + "," + giocatoreDaSalvare.getEmail() + "," + (giocatoreDaSalvare.getDataNascita()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            writer.newLine();
            writer.write(riga);
            writer.close();
            System.out.println("Giocatore salvato su file con successo.");
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nel file");
        }
    }
    public void rimuoviGiocatoreDaFile(String codiceFiscale){
        LinkedList<String> righeDaScrivere = new LinkedList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("listagiocatori.txt"));
            String rigaLetta;
            while ((rigaLetta = reader.readLine()) != null) {
                if(!rigaLetta.isEmpty()){
                    String[] campi = rigaLetta.split(",");
                    String codiceFiscaleLetto = campi[2];
                    if (!codiceFiscaleLetto.equals(codiceFiscale)) {
                        righeDaScrivere.add(rigaLetta);
                    }
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("listagiocatori.txt"));
            for (String rigaCorrente : righeDaScrivere) {
                writer.write(rigaCorrente);
                writer.newLine();
            }
            writer.close();
            loadUtentiRegistrati();
        }catch (IOException e){
            System.out.println("Si è verificato un errore nella rimozione del giocatore dal sistema");
        }
    }
    public void salvaPrenotazioneSuFile(Prenotazione prenotazioneDaSalvare){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("listaprenotazioni.txt", true));
            String riga = prenotazioneDaSalvare.getIdPrenotazione() + "," + prenotazioneDaSalvare.getIdStanza().getNumero() + "," + prenotazioneDaSalvare.getGiocatore1().getCodiceFiscale() + "," + prenotazioneDaSalvare.getGiocatore2().getCodiceFiscale() + "," + prenotazioneDaSalvare.getDataPrenotazione().format(dateFormatter) + "," + prenotazioneDaSalvare.getOraInizio().format(timeFormatter) + "," + prenotazioneDaSalvare.getOraFine().format(timeFormatter) + "," + prenotazioneDaSalvare.getAttrezzaturaRichiesta().getNumeroTorce() + "," + prenotazioneDaSalvare.getAttrezzaturaRichiesta().getNumeroIndizi() + "," + prenotazioneDaSalvare.getCosto();
            writer.newLine();
            writer.write(riga);
            writer.close();
            System.out.println("Prenotazione salvata su file con successo.");
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nel file");
        }
    }
    public void rimuoviPrenotazioneDaFile(Prenotazione prenotazioneDaRimuovere) {
        LinkedList<String> righeDaMantenere = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("listaprenotazioni.txt"));
            String riga;
            while ((riga = reader.readLine()) != null) {
                if(!riga.isEmpty()){
                    String[] campi = riga.split(",");
                    int idPrenotazione = Integer.parseInt(campi[0]);
                    if (idPrenotazione != prenotazioneDaRimuovere.getIdPrenotazione()) {
                        righeDaMantenere.add(riga);
                    }
                }

            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("listaprenotazioni.txt"));
            for (String rigaDaScrivere : righeDaMantenere) {
                writer.write(rigaDaScrivere);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Errore durante la rimozione della prenotazione dal file: " + e);

        }
    }
    public void rimuoviRichiestaDaFile (Magazzino richiestaDaRimuovere){
        LinkedList<String> righeDaMantenere = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("listarichieste.txt"));
            String riga;
            LocalDate dataRichiesta = richiestaDaRimuovere.getDataPartita();
            int torceRichiesta = richiestaDaRimuovere.getNumeroTorce();
            int indiziRichiesta = richiestaDaRimuovere.getNumeroIndizi();
            while ((riga = reader.readLine()) != null) {
                if(!riga.isEmpty()){
                    String[] campi = riga.split(",");
                    LocalDate dataFile = LocalDate.parse(campi[0], dateFormatter );
                    int torceFile = Integer.parseInt(campi[1]);
                    int indiziFile = Integer.parseInt(campi[2]);
                    if(torceRichiesta==torceFile && indiziRichiesta==indiziFile && dataRichiesta.isEqual(dataFile)){
                        System.out.println("Corrispondenza trovata");
                    }else{
                        righeDaMantenere.add(riga);
                    }
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("listarichieste.txt"));
            for (String rigaDaScrivere : righeDaMantenere) {
                writer.write(rigaDaScrivere);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Errore durante la rimozione della prenotazione dal file: " + e);

        }
    }
    private void salvaRichiestaSuFile(Magazzino richiestaDaSalvare){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("listarichieste.txt", true));
            String riga = richiestaDaSalvare.getDataPartita().format(dateFormatter) + "," + richiestaDaSalvare.getNumeroTorce() + "," + richiestaDaSalvare.getNumeroIndizi();
            writer.newLine();
            writer.write(riga);
            writer.close();
            System.out.println("Richiesta salvata su file con successo.");
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nel file");
        }
    }
    private void salvaStanzaSuFile(Stanza stanzaDaSalvare){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("listastanze.txt", true));
            String riga = stanzaDaSalvare.getNumero()+","+stanzaDaSalvare.getNome() +","+ stanzaDaSalvare.getPrezzo();
            writer.newLine();
            writer.write(riga);
            writer.close();
            System.out.println("Richiesta salvata su file con successo.");
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nel file");
        }
    }
    public void rimuoviStanzaDaFile(Stanza stanzaDaRimuovere){
        LinkedList<String> righeDaMantenere = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("listastanze.txt"));
            String riga;
            int numeroStanza = stanzaDaRimuovere.getNumero();
            String nomeStanza = stanzaDaRimuovere.getNome();
            float prezzoStanza = stanzaDaRimuovere.getPrezzo();
            while ((riga = reader.readLine()) != null) {
                if(!riga.isEmpty()){
                    String[] campi = riga.split(",");
                    int numeroFile = Integer.parseInt(campi[0]);
                    String nomeFile = campi[1];
                    float prezzoFile = Float.parseFloat(campi[2]);

                    if(numeroStanza==numeroFile && nomeStanza.equals(nomeFile) && prezzoStanza==prezzoFile){
                        System.out.println("Corrispondenza trovata");
                    }else{
                        righeDaMantenere.add(riga);
                    }
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("listastanze.txt"));
            for (String rigaDaScrivere : righeDaMantenere) {
                writer.write(rigaDaScrivere);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Errore durante la rimozione della stanza dal file: " + e);

        }
    }
    public void salvaPartitaSuFile(Partita partitaDaSalvare){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("listapartite.txt", true));
            String riga = partitaDaSalvare.getIdPrenotazione() + "," + partitaDaSalvare.getIdStanza().getNumero() + "," + partitaDaSalvare.getGiocatore1().getCodiceFiscale() + "," + partitaDaSalvare.getGiocatore2().getCodiceFiscale() + "," + partitaDaSalvare.getDataPrenotazione().format(dateFormatter) + "," + partitaDaSalvare.getOraInizio().format(timeFormatter) + "," + partitaDaSalvare.getOraFine().format(timeFormatter) + "," + partitaDaSalvare.getAttrezzaturaRichiesta().getNumeroTorce() + "," + partitaDaSalvare.getAttrezzaturaRichiesta().getNumeroIndizi() + "," + partitaDaSalvare.getCosto()+","+partitaDaSalvare.getIsOccupata()+","+partitaDaSalvare.getTempoCheckin().format(timeFormatter)+","+partitaDaSalvare.getTempoCheckout().format(timeFormatter);
            writer.newLine();
            writer.write(riga);
            writer.close();
            System.out.println("Prenotazione salvata su file con successo.");
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nel file");
        }
    }
    public void rimuoviPartitaDaFile(Partita partitaDaRimuovere) {
        LinkedList<String> righeDaMantenere = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("listapartite.txt"));
            String riga;
            while ((riga = reader.readLine()) != null) {
                if(!riga.isEmpty()){
                    String[] campi = riga.split(",");
                    int idPrenotazione = Integer.parseInt(campi[0]);
                    if (idPrenotazione != partitaDaRimuovere.getIdPrenotazione()) {
                        righeDaMantenere.add(riga);
                    }
                }

            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("listapartite.txt"));
            for (String rigaDaScrivere : righeDaMantenere) {
                writer.write(rigaDaScrivere);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Errore durante la rimozione della partita dal file: " + e);

        }
    }

    //Logica metodi
    public boolean verificaValiditaGiocatore(String codiceFiscale){
        boolean esito = true;
        Giocatore giocatoreCorrente = getUtenteCodiceFiscale(codiceFiscale);
        if(Objects.isNull(giocatoreCorrente))
            esito = false;
        return esito;
    }
    public boolean checkDurata(LocalTime inizio, LocalTime fine){
        boolean esito = true;
        Stanza stanza = new Stanza();
        long limite = stanza.getLimiteMassimo();
        long differenza = inizio.until(fine, ChronoUnit.MINUTES);
        if (differenza>limite){
            esito=false;
            System.out.println("Una stanza può essere prenotata per " + limite + " minuti consecutivi");
        }
        return esito;
    }
    public boolean checkStanzaLibera(int stanzaPrenotazione, LocalDate dataPrenotazione, LocalTime inizioPrenotazione){
        boolean esito = true;
        for(Prenotazione prenotazioneSalvata : listaPrenotazioni){
            if((prenotazioneSalvata.getIdStanza().getNumero() == stanzaPrenotazione) && prenotazioneSalvata.getDataPrenotazione().isEqual(dataPrenotazione)){
                if(inizioPrenotazione.isBefore(prenotazioneSalvata.getOraFine())){
                    System.out.println("La stanza non è libera nell'intervallo di tempo scelto");
                    esito = false;
                }
            }
        }
        return esito;
    }
    public boolean checkAttrezzatura(LocalDate giornoPartita, int torceRichieste, int indiziRichiesti){
        Magazzino magazzino = new Magazzino();
        boolean esito = true;
        int torceImpegnate = 0;
        int indiziImpegnati = 0;
        int totaleTorce;
        int totaleIndizi;
        for(Magazzino richiestaCorrente: listaRichieste){
            if((richiestaCorrente.getDataPartita().isEqual(giornoPartita))&&(richiestaCorrente.getDataPartita().isAfter(LocalDate.now()))){
                torceImpegnate = torceImpegnate + richiestaCorrente.getNumeroTorce();
                indiziImpegnati = indiziImpegnati + richiestaCorrente.getNumeroIndizi();
            }
        }
        totaleTorce = torceImpegnate + torceRichieste;
        totaleIndizi = indiziImpegnati + indiziRichiesti;
        if((totaleTorce > magazzino.getMaxTorce()) || (totaleIndizi > magazzino.getMaxIndizi())){
            System.out.println("Non sono disponibili sufficienti torce/indizi!");
            esito = false;
        }
        return esito;
    }
    public Attrezzatura calcolaAttrezzaturaLibera(LocalDate giornoPartita, Magazzino magazzino){
        int torceImpegnate = 0;
        int indiziImpegnati = 0;
        int maxTorce = magazzino.getMaxTorce();
        int maxIndizi = magazzino.getMaxIndizi();
        int torceRimanenti = 0;
        int indiziRimanenti = 0;
        for(Magazzino richiestaCorrente: listaRichieste){
            if(richiestaCorrente.getDataPartita().isEqual(giornoPartita)){
                torceImpegnate = torceImpegnate + richiestaCorrente.getNumeroTorce();
                indiziImpegnati = indiziImpegnati + richiestaCorrente.getNumeroIndizi();
            }
        }
        torceRimanenti=maxTorce-torceImpegnate;
        indiziRimanenti=maxIndizi-indiziImpegnati;
        Attrezzatura attrezzaturaDisponibile = new Attrezzatura(torceRimanenti, indiziRimanenti);
        return attrezzaturaDisponibile;
    }
    public float prospettoPrezzo(Stanza stanzaPrenotata, Attrezzatura attrezzaturaNoleggiata, float durataPartita){
        //Pattern Gof Strategy
        PagamentoStanza pagamentoStanza = new PagamentoStanza();
        float prezzoStanza = pagamentoStanza.costoStanza(stanzaPrenotata, durataPartita );
        Magazzino magazzino = new Magazzino();
        PagamentoAttrezzatura pagamentoAttrezzatura = new PagamentoAttrezzatura();
        float prezzoAttrezzatura = pagamentoAttrezzatura.costoAttrezzatura(attrezzaturaNoleggiata, magazzino);
        return prezzoStanza + prezzoAttrezzatura;
    }
    public int getMaxId(boolean isPrenotazione){
        int idMax=0;
        int idCorrente;
        if(!isPrenotazione){
            for (Stanza stanzaCorrente : listaStanze){
                idCorrente = stanzaCorrente.getNumero();
                if(idCorrente>=idMax){
                    idMax=idCorrente;
                }
            }
        }else{
            for(Prenotazione prenotazioneCorrente : listaPrenotazioni){
                idCorrente = prenotazioneCorrente.getIdPrenotazione();
                if(idCorrente>=idMax){
                    idMax=idCorrente;
                }
            }
            for(Partita partitaCorrente : listaPartiteGiocate){
                idCorrente = partitaCorrente.getIdPrenotazione();
                if(idCorrente>=idMax){
                    idMax=idCorrente;
                }
            }
        }
        return idMax;
    }

    //Funzioni per test
    public LinkedList<Giocatore> getListaGiocatori(){
        return listaGiocatori;
    }
    public LinkedList<Stanza> getListaStanze() {
        return listaStanze;
    }
    public LinkedList<Prenotazione> getListaPrenotazioni(){
        return listaPrenotazioni;
    }
    public LinkedList<Partita> getListaPartiteGiocate(){
        return listaPartiteGiocate;
    }
    public LinkedList<Magazzino> getListaRichieste(){return listaRichieste;}
}

