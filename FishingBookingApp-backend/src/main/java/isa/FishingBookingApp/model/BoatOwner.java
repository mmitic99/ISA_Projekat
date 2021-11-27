package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class BoatOwner extends User {

    @OneToMany(mappedBy = "boatOwner", fetch = FetchType.LAZY)
    private Set<Boat> ownedBoats;

    public BoatOwner() {

    }

    public Set<Boat> getOwnedBoats() {
        return ownedBoats;
    }

    public void setOwnedBoats(Set<Boat> ownedBoats) {
        this.ownedBoats = ownedBoats;
    }
}
