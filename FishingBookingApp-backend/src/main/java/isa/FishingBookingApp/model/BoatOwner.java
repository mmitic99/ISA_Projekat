package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "boat_owner_user")
public class BoatOwner extends User {

    private String explanationOfReg;

    public BoatOwner() {
    }

    public BoatOwner(User user) {
        super(user);
    }

    public BoatOwner(String mailAddress, String password, String name, String surname, String mobileNumber, Address address, UserRole role, boolean enabled, boolean verified, String explanationOfReg) {
        super(mailAddress, password, name, surname, mobileNumber, address, role, enabled, verified);
        this.explanationOfReg = explanationOfReg;
    }

    public String getExplanationOfReg() {
        return explanationOfReg;
    }

    public void setExplanationOfReg(String explanationOfReg) {
        this.explanationOfReg = explanationOfReg;
    }
}
