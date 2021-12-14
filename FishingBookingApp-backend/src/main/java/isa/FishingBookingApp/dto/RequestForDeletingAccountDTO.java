package isa.FishingBookingApp.dto;

public class RequestForDeletingAccountDTO {
    private String mailAddress;
    private String explanation;

    public RequestForDeletingAccountDTO() {
    }

    public RequestForDeletingAccountDTO(String username, String explanation) {
        this.mailAddress = username;
        this.explanation = explanation;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

}
