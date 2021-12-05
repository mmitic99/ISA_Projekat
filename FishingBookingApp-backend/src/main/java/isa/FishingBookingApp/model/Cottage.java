package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Set;

@Entity
public class Cottage extends ReservationEntities {

    public Cottage() {
        this.setType("cottage");
    }

    public Cottage(String name, Address address, String promotionalDescription, Set<SpecialReservation> preDefinedReservations, String rulesOfConduct, Set<AdditionalService> additionalServices, CottageOwner cottageOwner, int numberOfRooms, int bedsPerRoom, double price) {
        super(name, address, promotionalDescription, preDefinedReservations, rulesOfConduct, additionalServices, "cottage");
        this.cottageOwner = cottageOwner;
        this.numberOfRooms = numberOfRooms;
        this.bedsPerRoom = bedsPerRoom;
        this.price = price;
    }

    @ManyToOne
    private CottageOwner cottageOwner;

    private int numberOfRooms;

    private int bedsPerRoom;

    private double price;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
