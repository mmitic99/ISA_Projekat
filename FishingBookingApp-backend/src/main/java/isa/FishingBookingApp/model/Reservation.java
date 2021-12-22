package isa.FishingBookingApp.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Reservation {

    public Reservation() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    private ReservationEntities reservationEntity;

    private LocalDateTime start;

    private double durationInHours;

    private int maxPeople;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AdditionalService> additionalServices = new HashSet<>();

    private double price;

    private boolean deleted;

    public Reservation(User user, ReservationEntities reservationEntity, LocalDateTime start, double durationInHours, int maxPeople, double price) {
        this.user = user;
        this.reservationEntity = reservationEntity;
        this.start = start;
        this.durationInHours = durationInHours;
        this.maxPeople = maxPeople;
        this.price = price;
        this.deleted = false;
    }

    public Reservation(User user, ReservationEntities reservationEntity, LocalDateTime start, double durationInHours, int maxPeople, double price, Set<AdditionalService> additionalServices) {
        this.user = user;
        this.reservationEntity = reservationEntity;
        this.start = start;
        this.durationInHours = durationInHours;
        this.maxPeople = maxPeople;
        this.price = price;
        this.additionalServices = additionalServices;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReservationEntities getReservationEntity() {
        return reservationEntity;
    }

    public void setReservationEntity(ReservationEntities reservationEntity) {
        this.reservationEntity = reservationEntity;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public double getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(double durationInHours) {
        this.durationInHours = durationInHours;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Set<AdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
