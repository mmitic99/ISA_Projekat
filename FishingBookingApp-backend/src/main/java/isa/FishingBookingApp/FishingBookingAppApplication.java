package isa.FishingBookingApp;

import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.model.Address;
import isa.FishingBookingApp.repository.AddressRepository;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FishingBookingAppApplication implements CommandLineRunner {

	@Autowired
	private ReservationEntitiesRepository rFRepo;
	@Autowired
	private AddressRepository aRepo;

	public static void main(String[] args) {
		SpringApplication.run(FishingBookingAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		ReservationEntities rf = new ReservationEntities();
		rf.setName("naziv");
		rf.setPromotionalDescription("dasdasdsad");
		Address a = new Address();
		a.setCountry("Srbija");
		a.setCity("grad");
		aRepo.save(a);
		rf.setAddress(a);
		rFRepo.save(rf);
	}

}
