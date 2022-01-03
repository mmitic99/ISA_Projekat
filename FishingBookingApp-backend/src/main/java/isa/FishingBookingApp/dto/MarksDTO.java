package isa.FishingBookingApp.dto;

public class MarksDTO {
    private Long reservationEntitiesId;
    private Double avgMark;
    private int numberOfMarks;

    public MarksDTO() {
    }

    public MarksDTO(Long reservationEntitiesId, Double avgMark) {
        this.reservationEntitiesId = reservationEntitiesId;
        this.avgMark = avgMark;
    }

    public MarksDTO(Long reservationEntitiesId, Double avgMark, int numberOfMarks) {
        this.reservationEntitiesId = reservationEntitiesId;
        this.avgMark = avgMark;
        this.numberOfMarks = numberOfMarks;
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

    public int getNumberOfMarks() {
        return numberOfMarks;
    }

    public void setNumberOfMarks(int numberOfMarks) {
        this.numberOfMarks = numberOfMarks;
    }
}
