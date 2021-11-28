package isa.FishingBookingApp;

import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.repository.*;
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
	@Autowired
	private CottageRepository cottageRepo;

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

		// pravljenje vikendice
		Address cottageAdress = new Address();
		cottageAdress.setCountry("Srbija");
		cottageAdress.setCity("Nis");
		cottageAdress.setNumber("15a");
		cottageAdress.setStreet("Dobra ulica");
		aRepo.save(cottageAdress);

		Cottage cottage = new Cottage();
		cottage.setPromotionalDescription("Veoma opsiran promotivni opis");
		cottage.setName("Sunce");
		cottage.setCottageOwner(co);
		cottage.setPrice(2000);
		cottage.setAddress(cottageAdress);
		cottage.setBedsPerRoom(3);
		cottage.setNumberOfRooms(4);
		cottageRepo.save(cottage);
	}

}
