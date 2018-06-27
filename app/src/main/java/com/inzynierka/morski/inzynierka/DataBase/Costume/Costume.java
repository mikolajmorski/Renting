package com.inzynierka.morski.inzynierka.DataBase.Costume;

/**
 * Created by Asdf on 2015-11-07.
 */
public class Costume {
    private long id;
    private String name;
    private String label;

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {

        return label;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id=id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
