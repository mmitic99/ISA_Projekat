package isa.FishingBookingApp.dto;

public class MarksDTO {
    private Long reservationEntitiesId;
    private Double avgMark;

    public MarksDTO() {
    }

    public MarksDTO(Long reservationEntitiesId, Double avgMark) {
        this.reservationEntitiesId = reservationEntitiesId;
        this.avgMark = avgMark;
    }

    public Long getReservationEntitiesId() {
        return reservationEntitiesId;
    }

    public void setReservationEntitiesId(Long reservationEntitiesId) {
        this.reservationEntitiesId = reservationEntitiesId;
    }

    public Double getAvgMark() {
        return avgMark;
    }

    public void setAvgMark(Double avgMark) {
        this.avgMark = avgMark;
    }
}
