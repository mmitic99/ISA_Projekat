package isa.FishingBookingApp.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"reservation_id"})
)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String explain;

    @NotNull
    private LocalDateTime creationDateTime;

    @Min(1)
    @Max(10)
    private int mark;

    @OneToOne
    private Reservation reservation;

    private boolean approved;

    public Review() {
        approved = false;
    }

    public Review(String explain, LocalDateTime creationDateTime, int mark, Reservation reservation) throws Exception {
        this.explain = explain;
        this.mark = mark;
        this.reservation = reservation;
        setCreationDateTime(creationDateTime);
        approved = false;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) throws Exception {
        if (reservation.getStart().plusHours(Double.valueOf(reservation.getDurationInHours()).longValue()).isBefore(creationDateTime)) {
            this.creationDateTime = creationDateTime;
        }
        else{
            //throw new Exception("Creation date and time must be after reservation finished");
            throw new Exception("Datum kreiranja mora biti posle zavrsetka rezervacije");
        }
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
