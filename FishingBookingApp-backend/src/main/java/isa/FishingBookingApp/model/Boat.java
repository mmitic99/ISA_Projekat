package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Boat extends ReservationEntities {

    @ManyToOne(fetch = FetchType.LAZY)
    private BoatOwner boatOwner;
}