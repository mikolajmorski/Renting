package com.inzynierka.morski.inzynierka.DataBase.rentedItems;

/**
 * Created by Asdf on 2016-01-04.
 */
public class RentedItems {

    private long id;
    private long rentId;
    private long costumeId;
    private int position;

    public long getRentId() {
        return rentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRentId(long rentId) {
        this.rentId = rentId;
    }

    public long getCostumeId() {
        return costumeId;
    }

    public void setCostumeId(long costumeId) {
        this.costumeId = costumeId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
