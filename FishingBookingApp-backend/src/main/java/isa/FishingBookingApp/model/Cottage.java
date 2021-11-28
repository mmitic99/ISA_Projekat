package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cottage extends ReservationEntities {

    public Cottage() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private CottageOwner cottageOwner;

    private int roomNumber;

    private int bedNumber;

    private double price;

    public CottageOwner getCottageOwner() {
        return cottageOwner;
    }

    public void setCottageOwner(CottageOwner cottageOwner) {
        this.cottageOwner = cottageOwner;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
