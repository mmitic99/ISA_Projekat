package isa.FishingBookingApp.dto;

public class ReportForReservationDTO {
    private Long reportForReservationId;
    private Long reservationId;
    private String description;
    private String type;
    private boolean customerAppeared;
    private boolean requestForPenalty;

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
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
}
