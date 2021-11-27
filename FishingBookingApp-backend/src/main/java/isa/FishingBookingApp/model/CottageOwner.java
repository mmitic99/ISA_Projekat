package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class CottageOwner extends User {

    @OneToMany(mappedBy = "cottageOwner", fetch = FetchType.LAZY)
    private Set<Cottage> ownedCottages;

    public CottageOwner() {
    }

    public Set<Cottage> getOwnedCottages() {
        return ownedCottages;
    }

    public void setOwnedCottages(Set<Cottage> ownedCottages) {
        this.ownedCottages = ownedCottages;
    }
}
