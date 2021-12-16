package isa.FishingBookingApp;

import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@SpringBootApplication
public class FishingBookingAppApplication implements CommandLineRunner {

	private ReservationEntitiesRepository reservationEntitiesRepository;
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private UserRoleRepository userRoleRepository;
	private EntityImageRepository entityImageRepository;
	private AdditionalServiceRepository additionalServiceRepository;
	private SubscriptionRepository subscriptionRepository;
	private RequestForDeletingAccountRepository requestForDeletingAccountRepository;
	private AvailableAppointmentRepository availableAppointmentRepository;

	@Autowired
	public FishingBookingAppApplication(ReservationEntitiesRepository reservationEntitiesRepository, AddressRepository addressRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, EntityImageRepository entityImageRepository, AdditionalServiceRepository additionalServiceRepository, SubscriptionRepository subscriptionRepository, RequestForDeletingAccountRepository requestForDeletingAccountRepository, AvailableAppointmentRepository availableAppointmentRepository) {
		this.reservationEntitiesRepository = reservationEntitiesRepository;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.entityImageRepository = entityImageRepository;
		this.additionalServiceRepository = additionalServiceRepository;
		this.subscriptionRepository = subscriptionRepository;
		this.requestForDeletingAccountRepository = requestForDeletingAccountRepository;
		this.availableAppointmentRepository = availableAppointmentRepository;
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
		userRoleRepository.save(regUserRole);
		userRoleRepository.save(cottageOwnerRole);
		userRoleRepository.save(boatOwnerRole);
		userRoleRepository.save(adminRole);

		// inicijalizacija adresa
		Address address1 = new Address(0, 0, "Glavna ulica", "15", "Beograd", "21100", "Srbija");
		Address address2 = new Address(0, 0, "Zabacena ulica", "16", "Novi Sad", "23330", "Srbija");
		Address address3 = new Address(0, 0, "Periceva ulica", "bb", "Zlatibor", "24555", "Srbija");
		Address address4 = new Address(0, 0, "Tornjoški put", "5а", "Senta", "11100", "Srbija");
		addressRepository.save(address1);
		addressRepository.save(address2);
		addressRepository.save(address3);
		addressRepository.save(address4);

		// inicijalizacija korisnika (LOZINKA ZA SVE: 123)
		RegularUser regularUser1 = new RegularUser("isaproject.tim27+1@gmail.com", "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu", "Pero", "Peric", "+3816011111", address1, regUserRole, true, false);
		RegularUser regularUser2 = new RegularUser("isaproject.tim27+2@gmail.com", "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu", "Milica", "Miletic", "+3816022222", address2, regUserRole, true, true);
		CottageOwner cottageOwner1 = new CottageOwner("isaproject.tim27+3@gmail.com", "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu", "Damir", "Dakic", "+3816033333", address1, cottageOwnerRole, true, true, "Da ponudim korisnicima udoban provod u mojim vikendicama");
		CottageOwner cottageOwner2 = new CottageOwner("isaproject.tim27+4@gmail.com", "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu", "Lazar", "Lazic", "+3816044444", address1, cottageOwnerRole, true, false, "Da zaradim");
		BoatOwner boatOwner1 = new BoatOwner("isaproject.tim27+5@gmail.com", "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu", "Bojan", "Bokic", "+3816055555", address2, boatOwnerRole, true, true, "Da pruzim ljudima dozivljaj brzog glisera");
		userRepository.save(regularUser1);
		userRepository.save(regularUser2);
		userRepository.save(cottageOwner1);
		userRepository.save(cottageOwner2);
		userRepository.save(boatOwner1);

		// Inicijalizacija vikendica
		Cottage cottage1 = new Cottage("Sunce", address3, "Budite se uz najlepsi izlazak sunca.", null, "Zabranjeno pusenje. Zabranjeno dovodjenje zivotinja.", null, cottageOwner1, 3, 2, 2000);
		Cottage cottage2 = new Cottage("Snezna dolina", address4, "Uzivajte u prelepom pogledu.", null, "Zabranjeno pusenje. Zabranjeno dovodjenje zivotinja.", null, cottageOwner1, 4, 2, 3000);
		reservationEntitiesRepository.save(cottage1);
		reservationEntitiesRepository.save(cottage2);

		// Inicijalizacija dodatnih usluga
		AdditionalService additionalService1 = new AdditionalService(cottage1, "Djakuzi u dvoristu vikendice", "Pristup djakuziju u bilo koje doba dana. Uživajte u vreloj kupki na otvorenom.", 5000);
		AdditionalService additionalService2 = new AdditionalService(cottage1, "Pun pansion (cena na dan)", "Doručak, ručak i večera...uvek sveža hrana koju priprema najbolji kuvar iz okoline", 3000);
		AdditionalService additionalService3 = new AdditionalService(cottage1, "Vožnja kvadovima", "Sat vremena vožnje kvadovima po prelepoj prirodi.", 6000);
		AdditionalService additionalService4 = new AdditionalService(cottage2, "Djakuzi u dvoristu vikendice", "Pristup djakuziju u bilo koje doba dana. Uživajte u vreloj kupki na otvorenom.", 7000);
		additionalServiceRepository.save(additionalService1);
		additionalServiceRepository.save(additionalService2);
		additionalServiceRepository.save(additionalService3);
		additionalServiceRepository.save(additionalService4);

		// subscription
		Subscription subscription1 = new Subscription(regularUser2, cottage1);
		Subscription subscription2 = new Subscription(regularUser1, cottage1);
		Subscription subscription3 = new Subscription(regularUser2, cottage2);
		subscriptionRepository.save(subscription1);
		subscriptionRepository.save(subscription2);
		subscriptionRepository.save(subscription3);

		//
		RequestForDeletingAccount requestForDeletingAccount1 = new RequestForDeletingAccount(regularUser1, "adasdasdadsad");
		requestForDeletingAccountRepository.save(requestForDeletingAccount1);

		// dostupni termini za rezervaciju entiteta
		LocalDateTime dtStart1 = LocalDateTime.now();
		LocalDateTime dtEnd1 = dtStart1.plusDays(10);
		AvailableAppointment appointment1 = new AvailableAppointment(cottage1, dtStart1, dtEnd1);
		availableAppointmentRepository.save(appointment1);


		// Ucitavanje slika
		String file1 ="src/main/resources/static/images/imgCottage1.jpg";
		String file2 ="src/main/resources/static/images/imgCottage2.jpeg";
		byte[] img1 = null;
		byte[] img2 = null;

		try {
			img1 = readImage(file1);
			EntityImage entityImage1 = new EntityImage(cottage1, img1, "Vikendica1");
			entityImageRepository.save(entityImage1);

			img2 = readImage(file2);
			EntityImage entityImage2 = new EntityImage(cottage2, img2, "Vikendica2");
			entityImageRepository.save(entityImage2);
		} catch (Exception e) {

		}
	}

	private byte[] readImage(String file) throws IOException {
		byte[] img1 = null;
		DataInputStream reader = new DataInputStream(new FileInputStream(file));
		int nBytesToRead = reader.available();
		if(nBytesToRead > 0) {
			byte[] bytes = new byte[nBytesToRead];
			reader.read(bytes);
			img1 = bytes;
		}
		return img1;
	}
}
