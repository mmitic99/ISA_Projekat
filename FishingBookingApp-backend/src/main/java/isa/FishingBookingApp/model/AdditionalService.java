package isa.FishingBookingApp.model;

import javax.persistence.*;

@Entity
public class AdditionalService {

    public AdditionalService() {
        
    }

    public AdditionalService(ReservationEntities reservationEntity, String name, String description, double price) {
        this.reservationEntity = reservationEntity;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ReservationEntities reservationEntity;

    @Column(nullable = false)
    private String name;

    private String description;

    private double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationEntities getReservationEntity() {
        return reservationEntity;
    }

    public void setReservationEntity(ReservationEntities reservationEntity) {
        this.reservationEntity = reservationEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
