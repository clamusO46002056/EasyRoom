package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Magazzino {
    DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private int numeroTorce;
    private int numeroIndizi;
    private LocalDate dataPartita;
    private float costoSingolaAttrezzatura;
    private int maxTorce = 40;
    private int maxIndizi = 30;
    public Magazzino(){
        loadMagazzino();
    }
    public Magazzino (LocalDate dataPartita, int torceRichieste, int indiziRichiesti){
        this.numeroTorce=torceRichieste;
        this.numeroIndizi=indiziRichiesti;
        this.dataPartita=dataPartita;
        loadMagazzino();
    }
    public int getNumeroIndizi() {
        return numeroIndizi;
    }
    public int getNumeroTorce() {
        return numeroTorce;
    }
    public void setNumeroIndizi(int numeroIndizi) {
        this.numeroIndizi = numeroIndizi;
    }
    public void setNumeroTorce(int numeroTorce) {
        this.numeroTorce = numeroTorce;
    }
    public LocalDate getDataPartita() {
        return dataPartita;
    }
    public void setDataPartita(LocalDate dataPartita) {
        this.dataPartita = dataPartita;
    }
    public int getMaxIndizi() {
        return maxIndizi;
    }
    public int getMaxTorce() {
        return maxTorce;
    }
    public void setMaxIndizi(int maxIndizi) {
        this.maxIndizi = maxIndizi;
    }
    public void setMaxTorce(int maxTorce) {
        this.maxTorce = maxTorce;
    }
    public void setCostoSingolaAttrezzatura(float costoSingolaAttrezzatura) {
        this.costoSingolaAttrezzatura = costoSingolaAttrezzatura;
    }
    public float getCostoSingolaAttrezzatura() {
        return costoSingolaAttrezzatura;
    }
    public void loadMagazzino(){
        try {
            BufferedReader magazzinoLetto = new BufferedReader(new FileReader("magazzino.txt"));
            String linea;
            while ((linea = magazzinoLetto.readLine()) != null) {
                if(!linea.isEmpty()){
                    String[] datiMagazzino = linea.split(",");
                    maxTorce = Integer.parseInt(datiMagazzino[0]);
                    maxIndizi = Integer.parseInt(datiMagazzino[1]);
                    costoSingolaAttrezzatura = Float.parseFloat(datiMagazzino[2]);
                }
            }
            magazzinoLetto.close();
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nella lettura del magazzino");
        }
    }
    @Override
    public String toString(){
        return "Data prenotazione: " + dataPartita.format(customFormatter) + ", numero torce: " + numeroTorce + ", numero indizi: " + numeroIndizi;
    }
}
