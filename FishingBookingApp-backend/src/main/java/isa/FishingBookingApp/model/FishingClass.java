package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class FishingClass extends ReservationEntities{

	int a ;
	@ManyToOne(fetch = FetchType.LAZY)
	private FishingInstructor fishingInstructor;
}
//login to dusan git