package com.inzynierka.morski.inzynierka.Exeptions;

/**
 * Created by Asdf on 2015-12-28.
 */
public class NoCostumeInDataBaseException extends Exception {
    public NoCostumeInDataBaseException(String label){
        super("Nie mozna znalesc kostiumu w bazie danych o kodzie: " + label);
    }
}
