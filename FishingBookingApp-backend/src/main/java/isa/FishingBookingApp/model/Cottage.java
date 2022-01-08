package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Cottage extends ReservationEntities {

    public Cottage() {
        this.setType("cottage");
    }

    public Cottage(String name, Address address, String promotionalDescription, String rulesOfConduct, CottageOwner cottageOwner, int numberOfRooms, int bedsPerRoom, double price) {
        super(name, address, promotionalDescription, rulesOfConduct, "cottage", price);
        this.cottageOwner = cottageOwner;
        this.numberOfRooms = numberOfRooms;
        this.bedsPerRoom = bedsPerRoom;
    }

    @ManyToOne
    private CottageOwner cottageOwner;

    private int numberOfRooms;

    private int bedsPerRoom;

    public CottageOwner getCottageOwner() {
        return cottageOwner;
    }

    public void setCottageOwner(CottageOwner cottageOwner) {
        this.cottageOwner = cottageOwner;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getBedsPerRoom() {
        return bedsPerRoom;
    }

    public void setBedsPerRoom(int bedsPerRoom) {
        this.bedsPerRoom = bedsPerRoom;
    }

}
