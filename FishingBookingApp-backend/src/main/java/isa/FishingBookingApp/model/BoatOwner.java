package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "boat_owner_user")
public class BoatOwner extends User {

    @OneToMany(mappedBy = "boatOwner", fetch = FetchType.LAZY)
    private Set<Boat> ownedBoats;

    private String explanationOfReg;

    public BoatOwner() {
        ownedBoats = new HashSet<Boat>();
    }

    public BoatOwner(User user) {
        super(user);
        ownedBoats = new HashSet<Boat>();
    }

    public Set<Boat> getOwnedBoats() {
        return ownedBoats;
    }

    public void setOwnedBoats(Set<Boat> ownedBoats) {
        this.ownedBoats = ownedBoats;
    }

    public String getExplanationOfReg() {
        return explanationOfReg;
    }

    public void setExplanationOfReg(String explanationOfReg) {
        this.explanationOfReg = explanationOfReg;
    }
}
