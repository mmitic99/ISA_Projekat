package isa.FishingBookingApp.exception;

public class ResourceConflictException extends RuntimeException{
    private static final long serialVersionUID = 1791564636123821405L;

    private String resource;

    public ResourceConflictException(String resource, String message) {
        super(message);
        this.setResource(resource);
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
