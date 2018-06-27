package com.inzynierka.morski.inzynierka;

/**
 * Created by Asdf on 2016-02-25.
 */
public enum RentStatus {
    AKTYWNE("aktywne"),
    ZAKONCZONE("zakonczone"),
    CZESCIOWO_ZAKONCZONE("czesciowo zakonczone");
    public final String wartosc;

    RentStatus(String wartosc){
        this.wartosc=wartosc;
    }
}

