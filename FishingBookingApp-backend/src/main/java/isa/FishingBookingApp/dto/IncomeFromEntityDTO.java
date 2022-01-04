package isa.FishingBookingApp.dto;

public class IncomeFromEntityDTO {
    private Long reservationEntitiesId;
    private Double income;
    private int numberOfReservations;

    public IncomeFromEntityDTO(Long reservationEntitiesId, Double income, int numberOfReservations) {
        this.reservationEntitiesId = reservationEntitiesId;
        this.income = income;
        this.numberOfReservations = numberOfReservations;
    }

    public Long getReservationEntitiesId() {
        return reservationEntitiesId;
    }

    public void setReservationEntitiesId(Long reservationEntitiesId) {
        this.reservationEntitiesId = reservationEntitiesId;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public int getNumberOfReservations() {
        return numberOfReservations;
    }

    public void setNumberOfReservations(int numberOfReservations) {
        this.numberOfReservations = numberOfReservations;
    }
}
