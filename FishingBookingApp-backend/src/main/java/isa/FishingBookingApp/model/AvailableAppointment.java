package isa.FishingBookingApp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AvailableAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ReservationEntities entity;

    private LocalDateTime fromTime;

    private LocalDateTime toTime;

    public AvailableAppointment() {
    }

    public AvailableAppointment(ReservationEntities entity, LocalDateTime start, LocalDateTime end) {
        this.entity = entity;
        this.fromTime = start;
        this.toTime = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationEntities getEntity() {
        return entity;
    }

    public void setEntity(ReservationEntities entity) {
        this.entity = entity;
    }

    public LocalDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalDateTime start) {
        this.fromTime = start;
    }

    public LocalDateTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalDateTime end) {
        this.toTime = end;
    }
}
