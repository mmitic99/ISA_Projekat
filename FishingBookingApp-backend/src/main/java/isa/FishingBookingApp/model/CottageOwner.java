package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "cottage_owner_user")
public class CottageOwner extends User {

    private String explanationOfReg;

    public CottageOwner() {

    }

    public CottageOwner(User user) {
        super(user);
    }

    public CottageOwner(String mailAddress, String password, String name, String surname, String mobileNumber, Address address, UserRole role, boolean enabled, boolean verified, String explanationOfReg) {
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
