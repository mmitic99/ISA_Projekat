package isa.FishingBookingApp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ReservationEntities {

    @Id
    @SequenceGenerator(name = "reservationEntitiesGen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservationEntitiesGen")
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    private String promotionalDescription;

    @OneToMany(mappedBy = "reservationEntity")
    private Set<SpecialReservation> preDefinedReservations;

    private String rulesOfConduct;

    //@OneToMany(mappedBy = "reservationEntity")
    //private Set<AdditionalService> additionalServices;

    private String type;

    public ReservationEntities() {
    }

    public ReservationEntities(String name, Address address, String promotionalDescription, Set<SpecialReservation> preDefinedReservations, String rulesOfConduct, Set<AdditionalService> additionalServices, String type) {
        this.name = name;
        this.address = address;
        this.promotionalDescription = promotionalDescription;
        this.preDefinedReservations = preDefinedReservations;
        this.rulesOfConduct = rulesOfConduct;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPromotionalDescription() {
        return promotionalDescription;
    }

    public void setPromotionalDescription(String promotionalDescription) {
        this.promotionalDescription = promotionalDescription;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRulesOfConduct() {
        return rulesOfConduct;
    }

    public void setRulesOfConduct(String rulesOfConduct) {
        this.rulesOfConduct = rulesOfConduct;
    }
}
