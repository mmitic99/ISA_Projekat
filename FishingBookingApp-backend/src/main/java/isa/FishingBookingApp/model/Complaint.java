package isa.FishingBookingApp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private ReservationEntities reservationEntities;

    private String explain;

    private LocalDateTime creationDateTime;

    public Complaint() {
    }

    public Complaint(String explain, User user, ReservationEntities reservationEntities, LocalDateTime creationDateTime) {
        this.explain = explain;
        this.user = user;
        this.reservationEntities = reservationEntities;
        this.creationDateTime = creationDateTime;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
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
