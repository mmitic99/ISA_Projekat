package isa.FishingBookingApp.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class SpecialReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_creator_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationEntities reservationEntity;

    private LocalDateTime start;

    private double durationInHours;

    private int maxPeople;

    @ManyToMany(fetch = FetchType.EAGER)
    //@JoinTable(joinColumns = @JoinColumn(name = "special_reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "additional_services_id", referencedColumnName = "id"))
    private Set<AdditionalService> additionalServices;

    private double price;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    public SpecialReservation() {

    }

    public SpecialReservation(User user, ReservationEntities reservationEntity, LocalDateTime start, double durationInHours, int maxPeople, Set<AdditionalService> additionalServices, double price, LocalDateTime validFrom, LocalDateTime validTo) {
        this.user = user;
        this.reservationEntity = reservationEntity;
        this.start = start;
        this.durationInHours = durationInHours;
        this.maxPeople = maxPeople;
        this.additionalServices = additionalServices;
        this.price = price;
        this.validFrom = validFrom;
        this.validTo = validTo;
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

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }
}
