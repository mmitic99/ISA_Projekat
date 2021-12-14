package isa.FishingBookingApp.model;

import javax.persistence.*;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    User user;

    @OneToOne
    ReservationEntities reservationEntities;

    public Subscription() {
    }

    public Subscription(User user, ReservationEntities reservationEntities) {
        this.user = user;
        this.reservationEntities = reservationEntities;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReservationEntities getReservationEntities() {
        return reservationEntities;
    }

    public void setReservationEntities(ReservationEntities reservationEntities) {
        this.reservationEntities = reservationEntities;
    }
}
