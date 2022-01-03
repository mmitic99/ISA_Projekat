package isa.FishingBookingApp.model;

import javax.persistence.*;

@Entity
public class ReportForReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Reservation reservation;

    private String description;

    private String type;

    private boolean customerAppeared;

    private boolean requestForPenalty;

    private boolean approved;

    public ReportForReservation() { }

    public ReportForReservation(Reservation reservation, String description, String type, boolean customerAppeared, boolean requestForPenalty) {
        this.reservation = reservation;
        this.description = description;
        this.type = type;
        this.customerAppeared = customerAppeared;
        this.requestForPenalty = requestForPenalty;
        if (requestForPenalty && customerAppeared) {
            this.approved = false;
        }else {
            this.approved = true;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCustomerAppeared() {
        return customerAppeared;
    }

    public void setCustomerAppeared(boolean customerAppeared) {
        this.customerAppeared = customerAppeared;
    }

    public boolean isRequestForPenalty() {
        return requestForPenalty;
    }

    public void setRequestForPenalty(boolean requestForPenalty) {
        this.requestForPenalty = requestForPenalty;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
