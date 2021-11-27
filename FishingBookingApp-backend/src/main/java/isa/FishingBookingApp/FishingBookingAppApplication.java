package isa.FishingBookingApp;

import isa.FishingBookingApp.model.ReservationEntities;
import isa.FishingBookingApp.model.Address;
import isa.FishingBookingApp.model.User;
import isa.FishingBookingApp.model.UserRole;
import isa.FishingBookingApp.repository.AddressRepository;
import isa.FishingBookingApp.repository.ReservationEntitiesRepository;
import isa.FishingBookingApp.repository.UserRepository;
import isa.FishingBookingApp.repository.UserRoleRepository;
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
	@Autowired
	private UserRepository uRepo;
	@Autowired
	private UserRoleRepository urRepo;

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

		UserRole role1 = new UserRole();
		role1.setName("admin");

		urRepo.save(role1);

		User user = new User();
		user.setMailAddress("asdsad");
		user.setName("asdsad");
		user.setSurname("asdsad");
		user.setPassword("asdsad");
		user.setRole(role1);
		uRepo.save(user);
	}

}
