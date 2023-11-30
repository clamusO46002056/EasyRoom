package org.example;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        //MAIN
        BufferedReader tastiera;
        tastiera = new BufferedReader(new InputStreamReader((System.in)));

        EasyRoom applicazione = EasyRoom.getInstance(); //Pattern GoF Singleton

        System.out.println("Sei un amministratore? true/false");
        //Pattern Gof Facade
        try{
            boolean admin = Boolean.parseBoolean(tastiera.readLine());
            if(admin){
                applicazioneAdmin(applicazione, tastiera);
            } else {
                applicazioneUtente(applicazione, tastiera);
            }
        } catch (IOException e){
            System.out.println("Errore nella scelta del ruolo " + e);
        }
    }
    public static void applicazioneAdmin(EasyRoom applicazione, BufferedReader tastiera){
        try{
            int scelta;
            int azione;
            while(true){
                System.out.println("\n");
                System.out.println("---------------------------EasyRoom (Admin)----------------------------------------");
                System.out.println("Inserisci un numero per eseguire una tra le seguenti operazioni");
                System.out.println("1) Inserisci un nuovo giocatore (UC1)");
                System.out.println("2) Effettua una prenotazione (UC2) ");
                System.out.println("3) Modifica o annulla una prenotazione (UC3)");
                System.out.println("4) Aggiungi o modifica una stanza (UC4)");
                System.out.println("5) Visualizza prenotazioni delle stanze (UC5)");
                System.out.println("6) Effettua il check-in di una prenotazione (UC6)");
                System.out.println("7) Effettua il check-out di una prenotazione (UC7)");
                System.out.println("8) Conta il numero di partite giocate in una stanza (UC8)");
                System.out.println("9) Gestione del magazzino (UC9)");
                System.out.println("0) Per terminare l'esecuzione");
                scelta=Integer.parseInt(tastiera.readLine());
                switch(scelta){
                    case 1:
                        applicazione.inserisciUtente();
                        break;
                    case 2:
                        applicazione.inserisciPrenotazione();
                        break;
                    case 3:
                        System.out.println("1)Annulla una prenotazione");
                        System.out.println("2)Modifica una prenotazione");
                        azione = Integer.parseInt(tastiera.readLine());
                        applicazione.stampaListaPrenotazioni();
                        switch (azione){
                            case 1:
                                float rimborso = applicazione.rimuoviPrenotazione();
                                System.out.println("Hai ottenuto un rimborso di euro " + rimborso);
                                break;
                            case 2:
                                applicazione.modificaPrenotazione();
                                break;
                            default:
                                return;
                        }
                        break;
                    case 4:
                        System.out.println("1)Inserisci una nuova stanza");
                        System.out.println("2)Modifica nome/prezzo di una stanza esistente");
                        azione = Integer.parseInt(tastiera.readLine());
                        applicazione.stampaListaStanze();
                        switch (azione){
                            case 1:
                                applicazione.inserisciStanza();
                                break;
                            case 2:
                                applicazione.modificaStanza();
                                break;
                            default:
                                return;
                        }
                        break;
                    case 5:
                        applicazione.visualizzaPrenotazioni();
                        break;
                    case 6:
                        applicazione.checkin();
                        break;
                    case 7:
                        applicazione.checkout();
                        break;
                    case 8:
                        System.out.println("Inserisci l'id della stanza di cui vuoi vedere il numero delle partite giocate");
                        int numeroStanza = Integer.parseInt(tastiera.readLine());
                        applicazione.contaPartite(numeroStanza);
                        break;
                    case 9:
                        applicazione.gestisciMagazzino();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Scelta non valida, riprovare");
                        break;
                }
            }
        }catch(Exception e){
            System.out.println("Si è verificato un errore nell'inserimento della scelta" + e);
        }
    }
    public static void applicazioneUtente(EasyRoom applicazione, BufferedReader tastiera){
        Observer observer = (idPrenotazione, codiciFiscali) -> {
            System.out.println("Prenotazione confermata con ID: " + idPrenotazione);
            System.out.println("Codici fiscale dei due partecipanti: " + codiciFiscali);
        };
        try{
            applicazione.addObserver(observer);
            int scelta;
            while(true){
                System.out.println("\n");
                System.out.println("---------------------------EasyRoom (User)----------------------------------------");
                System.out.println("Inserisci un numero per eseguire una tra le seguenti operazioni");
                System.out.println("1) Effettua una prenotazione (UC2) ");
                System.out.println("2) Modifica o annulla una prenotazione (UC3)");
                System.out.println("0) Per terminare l'esecuzione");
                scelta=Integer.parseInt(tastiera.readLine());
                switch(scelta){

                    case 1:
                        applicazione.inserisciPrenotazione();
                        break;
                    case 2:
                        System.out.println("1)Annulla una prenotazione");
                        System.out.println("2)Modifica una prenotazione");
                        int azione = Integer.parseInt(tastiera.readLine());
                        applicazione.stampaListaPrenotazioni();
                        switch (azione){
                            case 1:
                                float rimborso = applicazione.rimuoviPrenotazione();
                                System.out.println("Hai ottenuto un rimborso di euro " + rimborso);
                                break;
                            case 2:
                                applicazione.modificaPrenotazione();
                                break;
                            default:
                                return;
                        }
                        break;
                    case 0:
                        applicazione.removeObserver(observer);
                        return;
                    default:
                        System.out.println("Scelta non valida, riprovare");
                        break;
                }
            }
        }catch(Exception e){
            System.out.println("Si è verificato un errore nell'inserimento della scelta" + e);
        }
    }
}