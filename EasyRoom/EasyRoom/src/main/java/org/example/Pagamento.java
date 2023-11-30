package org.example;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

class PagamentoStanza implements StrategiaStanza{
    @Override
    public float costoStanza(Stanza stanzaPrenotata, float durata) {
        float costoStanza = stanzaPrenotata.getPrezzo() * durata;
        return costoStanza;
    }
}

class PagamentoAttrezzatura implements StrategiaAttrezzatura{
    @Override
    public float costoAttrezzatura(Attrezzatura attrezzaturaRichiesta, Magazzino magazzino) {
        float costoAttrezzatura = (attrezzaturaRichiesta.getNumeroIndizi() + attrezzaturaRichiesta.getNumeroTorce()) * magazzino.getCostoSingolaAttrezzatura();
        return costoAttrezzatura;
    }
}


