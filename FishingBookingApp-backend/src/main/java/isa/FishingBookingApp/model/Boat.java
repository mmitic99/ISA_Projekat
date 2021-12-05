package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Set;

@Entity
public class Boat extends ReservationEntities {

    public Boat(){
        this.setType("boat");
    }

    public Boat(String name, Address address, String promotionalDescription, Set<SpecialReservation> preDefinedReservations, String rulesOfConduct, Set<AdditionalService> additionalServices, BoatOwner boatOwner) {
        super(name, address, promotionalDescription, preDefinedReservations, rulesOfConduct, additionalServices, "boat");
        this.boatOwner = boatOwner;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private BoatOwner boatOwner;
}
