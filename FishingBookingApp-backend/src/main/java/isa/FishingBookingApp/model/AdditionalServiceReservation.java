package isa.FishingBookingApp.model;

import javax.persistence.*;

@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"additional_service_id", "reservation_id"}))
public class AdditionalServiceReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    AdditionalService additionalService;

    @OneToOne
    Reservation reservation;

    public AdditionalServiceReservation() {
    }

    public AdditionalServiceReservation(AdditionalService additionalService, Reservation reservation) {
        this.additionalService = additionalService;
        this.reservation = reservation;
    }

    public AdditionalService getAdditionalService() {
        return additionalService;
    }

    public void setAdditionalService(AdditionalService additionalService) {
        this.additionalService = additionalService;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
