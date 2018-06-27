package com.inzynierka.morski.inzynierka.DataBase.rent;

/**
 * Created by Asdf on 2016-01-04.
 */
public class Rent {

    private long id;
    private long clientId;
    private String dateRent;
    private String dateReturn;
    private String dateTrueReturn;
    private int price;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getDateRent() {
        return dateRent;
    }

    public void setDateRent(String dateRent) {
        this.dateRent = dateRent;
    }

    public String getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(String dateReturn) {
        this.dateReturn = dateReturn;
    }

    public String getDateTrueReturn() {
        return dateTrueReturn;
    }

    public void setDateTrueReturn(String dateTrueReturn) {
        this.dateTrueReturn = dateTrueReturn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
