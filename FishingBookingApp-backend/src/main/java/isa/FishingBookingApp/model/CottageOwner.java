package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "cottage_owner_user")
public class CottageOwner extends User {

    @OneToMany(mappedBy = "cottageOwner", fetch = FetchType.LAZY)
    private Set<Cottage> ownedCottages;

    private String explanationOfReg;

    public CottageOwner() {

    }

    public CottageOwner(User user) {
        super(user);
    }

    public Set<Cottage> getOwnedCottages() {
        return ownedCottages;
    }

    public void setOwnedCottages(Set<Cottage> ownedCottages) {
        this.ownedCottages = ownedCottages;
    }

    public String getExplanationOfReg() {
        return explanationOfReg;
    }

    public void setExplanationOfReg(String explanationOfReg) {
        this.explanationOfReg = explanationOfReg;
    }
}
