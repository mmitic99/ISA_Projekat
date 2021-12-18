package isa.FishingBookingApp.dto;

public class CancelReservationDTO {
    private Long reservationId;
    private String mailAddress;

    public CancelReservationDTO() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public CancelReservationDTO(Long reservationId, String mailAddress) {
        this.reservationId = reservationId;
        this.mailAddress = mailAddress;
    }
}
