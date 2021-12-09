package isa.FishingBookingApp.dto;

public class AdditionalServiceDTO {
    private Long id;
    private Long reservationEntityId;
    private String name;
    private String description;
    private double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getReservationEntityId() {
        return reservationEntityId;
    }

    public void setReservationEntityId(Long reservationEntityId) {
        this.reservationEntityId = reservationEntityId;
    }
}
