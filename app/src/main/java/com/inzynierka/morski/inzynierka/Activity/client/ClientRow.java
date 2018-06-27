package com.inzynierka.morski.inzynierka.Activity.client;

/**
 * Created by Asdf on 2015-12-29.
 */
public class ClientRow {
    public int icon;
    public String title;
    public boolean checkBox;
    public ClientRow(){
        super();
    }

    public ClientRow(int icon, String title, boolean checkBox) {
        super();
        this.icon = icon;
        this.title = title;
        this.checkBox = checkBox;
    }
}
