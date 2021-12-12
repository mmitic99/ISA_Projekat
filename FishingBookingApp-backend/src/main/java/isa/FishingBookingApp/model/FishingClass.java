package isa.FishingBookingApp.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class FishingClass extends ReservationEntities{

	public FishingClass() {
		this.setType("class");
	}
	
    public FishingClass(String name, Address address, String promotionalDescription, Set<SpecialReservation> preDefinedReservations, String rulesOfConduct, Set<AdditionalService> additionalServices, FishingInstructor fishingInstructor, String bio ,String gear, double price, float ifcanceled) {
        super(name, address, promotionalDescription, preDefinedReservations, rulesOfConduct, additionalServices, "class");
        this.fishingInstructor = fishingInstructor;
        this.bio= bio;
        this.gear = gear;
        this.ifcanceled = ifcanceled;
        this.setPrice(price);
    }
	
	@ManyToOne(fetch = FetchType.LAZY)
	private FishingInstructor fishingInstructor;
		
	private String bio; 
	
	private String gear;
	
	private float ifcanceled;
	
	private double price;

	public float getIfcanceled() {
		return ifcanceled;
	}

	public void setIfcanceled(float ifcanceled) {
		this.ifcanceled = ifcanceled;
	}

	public String getGear() {
		return gear;
	}

	public void setGear(String gear) {
		this.gear = gear;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

    public FishingInstructor getFishingInstructor() {
        return fishingInstructor;
    }

    public void setFishingInstructor(FishingInstructor fishingInstructor) {
        this.fishingInstructor = fishingInstructor;
    }

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
//login to dusan git