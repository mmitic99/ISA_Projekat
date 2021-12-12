package isa.FishingBookingApp;

import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FishingBookingAppApplication implements CommandLineRunner {

	private ReservationEntitiesRepository reservationEntitiesRepository;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private UserRoleRepository userRoleRepository;

	@Autowired
	public FishingBookingAppApplication(ReservationEntitiesRepository reservationEntitiesRepository, AddressRepository addressRepository, UserRepository userRepository, UserRoleRepository userRoleRepository){
		this.reservationEntitiesRepository = reservationEntitiesRepository;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FishingBookingAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		// inicijalizacija rola
		UserRole regUserRole = new UserRole("ROLE_USER");
		UserRole cottageOwnerRole = new UserRole("ROLE_cottageOwner");
		UserRole boatOwnerRole = new UserRole("ROLE_boatOwner");
		UserRole adminRole = new UserRole("ROLE_ADMIN");
		UserRole instructorRole = new UserRole("ROLE_fishingInstructor");
		userRoleRepository.save(regUserRole);
		userRoleRepository.save(cottageOwnerRole);
		userRoleRepository.save(boatOwnerRole);
		userRoleRepository.save(adminRole);
		userRoleRepository.save(instructorRole);
		// inicijalizacija adresa
		Address address1 = new Address(0, 0, "Glavna ulica", "15", "Beograd", "21100", "Srbija");
		Address address2 = new Address(0, 0, "Zabacena ulica", "16", "Novi Sad", "23330", "Srbija");
		Address address3 = new Address(0, 0, "Periceva ulica", "bb", "Zlatibor", "24555", "Srbija");
		Address address4 = new Address(0, 0, "Tornjoški put", "5а", "Senta", "11100", "Srbija");
		Address address5 = new Address(0, 0, "Petra Drapšina", "10", "Novi Sad", "21000", "Srbija");

		addressRepository.save(address1);
		addressRepository.save(address2);
		addressRepository.save(address3);
		addressRepository.save(address4);
		addressRepository.save(address5);

		// inicijalizacija korisnika (LOZINKA ZA SVE: 123)
		RegularUser regularUser1 = new RegularUser("isaproject.tim27+1@gmail.com", "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu", "Pero", "Peric", "+3816011111", address1, regUserRole, true, false);
		RegularUser regularUser2 = new RegularUser("isaproject.tim27+2@gmail.com", "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu", "Milica", "Miletic", "+3816022222", address2, regUserRole, true, true);
		CottageOwner cottageOwner1 = new CottageOwner("isaproject.tim27+3@gmail.com", "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu", "Damir", "Dakic", "+3816033333", address1, cottageOwnerRole, true, true, "Da ponudim korisnicima udoban provod u mojim vikendicama");
		CottageOwner cottageOwner2 = new CottageOwner("isaproject.tim27+4@gmail.com", "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu", "Lazar", "Lazic", "+3816044444", address1, adminRole, true, false, "Da zaradim");
		BoatOwner boatOwner1 = new BoatOwner("isaproject.tim27+5@gmail.com", "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu", "Bojan", "Bokic", "+3816055555", address2, boatOwnerRole, true, true, "Da pruzim ljudima dozivljaj brzog glisera");
		FishingInstructor fishingInstructor1 = new FishingInstructor("isaproject.tim27+6@gmail.com", "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu", "Deki", "Dekic", "+38160113368", address5, instructorRole, true, true, "Da naucim ljude da dobro pecaju.");
		userRepository.save(regularUser1);
		userRepository.save(regularUser2);
		userRepository.save(cottageOwner1);
		userRepository.save(cottageOwner2);
		userRepository.save(boatOwner1);	
		userRepository.save(fishingInstructor1);
		// Inicijalizacija vikendica
		Cottage cottage1 = new Cottage("Sunce", address3, "Budite se uz najlepsi izlazak sunca.", null, "Zabranjeno pusenje. Zabranjeno dovodjenje zivotinja.", null, cottageOwner1, 3, 2, 2000);
		Cottage cottage2 = new Cottage("Snezna dolina", address4, "Uzivajte u prelepom pogledu.", null, "Zabranjeno pusenje. Zabranjeno dovodjenje zivotinja.", null, cottageOwner1, 4, 2, 3000);
		reservationEntitiesRepository.save(cottage1);
		reservationEntitiesRepository.save(cottage2);

	}

}
