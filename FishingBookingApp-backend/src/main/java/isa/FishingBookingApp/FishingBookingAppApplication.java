package isa.FishingBookingApp;

import isa.FishingBookingApp.model.*;
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
		role1.setName("ROLE_USER");
		urRepo.save(role1);

		RegularUser user = new RegularUser();
		user.setMailAddress("asdsad");
		user.setName("asdsad");
		user.setSurname("asdsad");
		user.setPassword("asdsad");
		user.setRole(role1);
		uRepo.save(user);

		// testiranje cuvanja konkretnog usera
		CottageOwner co = new CottageOwner();
		co.setMailAddress("mmmmm@gmail.com");
		co.setName("Pera");
		co.setSurname("Peric");
		co.setPassword("123");

		UserRole cottageOwnerRole = new UserRole();
		cottageOwnerRole.setName("cottageOwner");
		urRepo.save(cottageOwnerRole);

		co.setRole(cottageOwnerRole);

		uRepo.save(co);

		BoatOwner bo = new BoatOwner();
		bo.setMailAddress("ppppp@gmail.com");
		bo.setName("Mika");
		bo.setSurname("Mikic");
		bo.setPassword("123");

		UserRole boatOwnerRole = new UserRole();
		boatOwnerRole.setName("boatOwner");
		urRepo.save(boatOwnerRole);

		bo.setRole(boatOwnerRole);

		uRepo.save(bo);
	}

}
