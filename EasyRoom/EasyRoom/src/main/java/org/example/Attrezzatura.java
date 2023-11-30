package org.example;

import java.util.Objects;

public class Attrezzatura {
    private int numeroTorce;
    private int numeroIndizi;

    public Attrezzatura (int numeroTorce, int numeroIndizi){
        this.numeroTorce = numeroTorce;
        this.numeroIndizi = numeroIndizi;
    }

    public int getNumeroTorce(){
        return numeroTorce;
    }

    public int getNumeroIndizi(){
        return  numeroIndizi;
    }

    public void setNumeroTorce(int numeroTorce) {
        this.numeroTorce = numeroTorce;
    }

    public void setNumeroIndizi(int numeroIndizi) {
        this.numeroIndizi = numeroIndizi;
    }
    @Override
    public String toString(){
        return "numero torce richieste: " + numeroTorce + ", numero indizi richiesti: " + numeroIndizi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attrezzatura attrezzatura = (Attrezzatura) o;
        return Objects.equals(numeroTorce, attrezzatura.numeroTorce) &&
                Objects.equals(numeroIndizi, attrezzatura.numeroIndizi);
    }
}
