package org.example;

import java.time.LocalTime;

interface StrategiaStanza {
    float costoStanza(Stanza stanzaPrenotata, float durata);
}

interface StrategiaAttrezzatura{
    float costoAttrezzatura(Attrezzatura attrezzaturaRichiesta, Magazzino magazzino);
}
