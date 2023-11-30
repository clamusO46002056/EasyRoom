package org.example;

import java.util.LinkedList;

public interface Observer {
    void update(int idPrenotazione, LinkedList<String> codiciFiscali);
}
