package org.example;

import java.util.Objects;

public class Stanza {
    private float prezzo;
    private int numeroStanza;
    private String nome;
    private long limiteMassimo = 90;
    public Stanza (){

    }
    public Stanza (int numeroStanza, String nome, float prezzo){
        this.numeroStanza = numeroStanza;
        this.nome = nome;
        this.prezzo = prezzo;
    }
    public int getNumero() {
        return numeroStanza;
    }
    public void setNumeroStanza(int numeroStanza) {
        this.numeroStanza = numeroStanza;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public float getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(float prezzo){
        this.prezzo=prezzo;
    }

    public long getLimiteMassimo() {
        return limiteMassimo;
    }

    public String toString(){
        return "ID stanza: " + numeroStanza + ", nome: " + nome + ", prezzo: " + prezzo;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stanza stanza = (Stanza) o;
        return numeroStanza == stanza.numeroStanza &&
                Float.compare(stanza.prezzo, prezzo) == 0 &&
                Objects.equals(nome, stanza.nome);
    }

}
