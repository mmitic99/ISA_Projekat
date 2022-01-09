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
import java.util.HashSet;
import java.util.Set;

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
    private ReservationRepository reservationRepository;
    private ReviewRepository reviewRepository;
    private SpecialReservationRepository specialReservationRepository;

    @Autowired
    public FishingBookingAppApplication(ReservationEntitiesRepository reservationEntitiesRepository, AddressRepository addressRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, EntityImageRepository entityImageRepository, AdditionalServiceRepository additionalServiceRepository, SubscriptionRepository subscriptionRepository, RequestForDeletingAccountRepository requestForDeletingAccountRepository, AvailableAppointmentRepository availableAppointmentRepository, ReservationRepository reservationRepository, ReviewRepository reviewRepository, SpecialReservationRepository specialReservationRepository) {
        this.reservationEntitiesRepository = reservationEntitiesRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.entityImageRepository = entityImageRepository;
        this.additionalServiceRepository = additionalServiceRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.requestForDeletingAccountRepository = requestForDeletingAccountRepository;
        this.availableAppointmentRepository = availableAppointmentRepository;
        this.reservationRepository = reservationRepository;
        this.reviewRepository = reviewRepository;
        this.specialReservationRepository = specialReservationRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(FishingBookingAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // inicijalizacija rola
        UserRole regUserRole = new UserRole("ROLE_USER");
        UserRole cottageOwnerRole = new UserRole("ROLE_cottageOwner");
        UserRole boatOwnerRole = new UserRole("ROLE_boatOwner");
        UserRole adminRole = new UserRole("ROLE_ADMIN");
        userRoleRepository.save(regUserRole);
        userRoleRepository.save(cottageOwnerRole);
        userRoleRepository.save(boatOwnerRole);
        userRoleRepository.save(adminRole);

        String country_name = "Srbija";

        // inicijalizacija adresa
        Address address1 = new Address(0, 0, "Glavna ulica", "15", "Beograd", "21100", country_name);
        Address address2 = new Address(0, 0, "Zabacena ulica", "16", "Novi Sad", "21000", country_name);
        Address address3 = new Address(0, 0, "Nepoznata ulica", "26", "Nis", "18000", country_name);
        Address address4 = new Address(0, 0, "ulica", "96", "Subotica", "29000", country_name);
        Address address5 = new Address(0, 0, "Zabacena Ulica", "116", "Pirot", "18500", country_name);
        Address address6 = new Address(0, 0, "Ulica", "52", "Zrenjanin", "23506", country_name);
        Address addressOfCottage1 = new Address(43.71881497806544, 19.69865620896269, "Srebrna pahulja", "5a", "Zlatibor", "24555", country_name);
        Address addressOfCottage2 = new Address(43.28496170828387, 20.804868872788767, "Jovana Cvijića", "15", "Kopaonik", "11100", country_name);
        Address addressOfBoat1 = new Address(44.82340663049456, 20.445556122854804, "Adresa reke", "bb", "Beograd", "88888", country_name);
        Address addressOfBoat2 = new Address(42.43471617825651, 18.757104427091786, "Muo", "125", "Kotor", "15151", "Crna gora");
        addressRepository.save(address1);
        addressRepository.save(address2);
        addressRepository.save(address3);
        addressRepository.save(address4);
        addressRepository.save(address5);
        addressRepository.save(address6);
        addressRepository.save(addressOfCottage1);
        addressRepository.save(addressOfCottage2);
        addressRepository.save(addressOfBoat1);
        addressRepository.save(addressOfBoat2);

        String userPassword = "$2a$10$HQxGxmNa2CaiQQfxR24f2u/OqEnP9goOWuwBUkKc7T2xvTsC9Lriu";

        // inicijalizacija korisnika (LOZINKA ZA SVE: 123)
        RegularUser regularUser1 = new RegularUser("isaproject.tim27+1@gmail.com", userPassword, "Pero", "Peric", "+3816011111", address1, regUserRole, true, false);
        RegularUser regularUser2 = new RegularUser("isaproject.tim27+2@gmail.com", userPassword, "Milica", "Miletic", "+3816022222", address2, regUserRole, true, true);
        RegularUser regularUser3 = new RegularUser("isaproject.tim27+6@gmail.com", userPassword, "Mika", "Mikic", "+38160221252", address3, regUserRole, true, true);
        CottageOwner cottageOwner1 = new CottageOwner("isaproject.tim27+3@gmail.com", userPassword, "Damir", "Dakic", "+3816033333", address4, cottageOwnerRole, true, true, "Da ponudim korisnicima udoban provod u mojim vikendicama");
        CottageOwner cottageOwner2 = new CottageOwner("isaproject.tim27+4@gmail.com", userPassword, "Lazar", "Lazic", "+3816044444", address5, cottageOwnerRole, true, false, "Da zaradim");
        BoatOwner boatOwner1 = new BoatOwner("isaproject.tim27+5@gmail.com", userPassword, "Bojan", "Bokic", "+3816055555", address6, boatOwnerRole, true, true, "Da pruzim ljudima dozivljaj brzog glisera");
        userRepository.save(regularUser1);
        userRepository.save(regularUser2);
        userRepository.save(regularUser3);
        userRepository.save(cottageOwner1);
        userRepository.save(cottageOwner2);
        userRepository.save(boatOwner1);

        // Inicijalizacija vikendica
        Cottage cottage1 = new Cottage("Sunce", addressOfCottage1, "Budite se uz najlepsi izlazak sunca.", "Zabranjeno pusenje. Zabranjeno dovodjenje zivotinja.", cottageOwner1, 3, 2, 2000);
        Cottage cottage2 = new Cottage("Snezna dolina", addressOfCottage2, "Uzivajte u prelepom pogledu.", "Zabranjeno pusenje. Zabranjeno dovodjenje zivotinja.", cottageOwner1, 4, 2, 3000);
        reservationEntitiesRepository.save(cottage1);
        reservationEntitiesRepository.save(cottage2);

        // Inicijalizacija brodova tj camaca
        Boat boat1 = new Boat("Sofija", addressOfBoat1, "Uzivajte u pecanju na sred mora sa profesionalnim lokalnim ribolovcem.", "Poštovati sva uputstva i pravila vodiča i ribolovca.", boatOwner1, "čamac za pecanje", 6, 1, 70, 80, "GPS, radar, VHF radio, fishFinder", "- Profesionalni štapovi i oprema za pecanje morskih riba", 5, "Besplatno otkazivanje.", 8000);
        Boat boat2 = new Boat("Brzi Gonzales", addressOfBoat2, "Uzivajte u brzoj voznji po moru.", "Poštovati sva uputstva i pravila vodiča.", boatOwner1, "gliser", 6, 3, 100, 250, "GPS, radar", "", 4, "Prilikom otkazivanja rezervacije vlasnik zadržava 20%.", 10000);
        reservationEntitiesRepository.save(boat1);
        reservationEntitiesRepository.save(boat2);

        // Inicijalizacija dodatnih usluga
        AdditionalService additionalService1 = new AdditionalService(cottage1, "Djakuzi u dvoristu vikendice", "Pristup djakuziju u bilo koje doba dana. Uživajte u vreloj kupki na otvorenom.", 5000);
        AdditionalService additionalService2 = new AdditionalService(cottage1, "Pun pansion (cena na dan)", "Doručak, ručak i večera...uvek sveža hrana koju priprema najbolji kuvar iz okoline", 3000);
        AdditionalService additionalService3 = new AdditionalService(cottage1, "Vožnja kvadovima", "Sat vremena vožnje kvadovima po prelepoj prirodi.", 6000);
        AdditionalService additionalService4 = new AdditionalService(cottage2, "Djakuzi u dvoristu vikendice", "Pristup djakuziju u bilo koje doba dana. Uživajte u vreloj kupki na otvorenom.", 7000);
        AdditionalService additionalServiceForBoat1 = new AdditionalService(boat1, "Lov harpunom", "60 minuta lovljenja ribe harpunom (ronjenje sa specijalnom opremom za ronjenje i harpunom)", 15000);
        AdditionalService additionalServiceForBoat2 = new AdditionalService(boat2, "Parajedrenje", "20 minuta parajedrenja (uz pomoć glisera Vas vučemo dok ste zakačeni na posebnu vrstu padobrana)", 6000);
        additionalServiceRepository.save(additionalService1);
        additionalServiceRepository.save(additionalService2);
        additionalServiceRepository.save(additionalService3);
        additionalServiceRepository.save(additionalService4);
        additionalServiceRepository.save(additionalServiceForBoat1);
        additionalServiceRepository.save(additionalServiceForBoat2);

        // subscription
        Subscription subscription1 = new Subscription(regularUser2, cottage1);
        Subscription subscription2 = new Subscription(regularUser1, cottage1);
        Subscription subscription3 = new Subscription(regularUser2, cottage2);
        subscriptionRepository.save(subscription1);
        subscriptionRepository.save(subscription2);
        subscriptionRepository.save(subscription3);

        // zahtev za brisanje naloga
        RequestForDeletingAccount requestForDeletingAccount1 = new RequestForDeletingAccount(regularUser1, "Ne svidja mi se aplikacija");
        requestForDeletingAccountRepository.save(requestForDeletingAccount1);

        // dostupni termini za rezervaciju entiteta
        LocalDateTime dtStart1 = LocalDateTime.now();
        LocalDateTime dtEnd1 = dtStart1.plusDays(40);
        AvailableAppointment appointment1 = new AvailableAppointment(cottage1, dtStart1, dtEnd1);
        AvailableAppointment appointment2 = new AvailableAppointment(cottage2, dtStart1, dtEnd1);
        LocalDateTime dtStart2 = LocalDateTime.now().minusDays(10);
        LocalDateTime dtEnd2 = dtStart1.plusDays(30);
        AvailableAppointment appointment3 = new AvailableAppointment(boat1, dtStart2, dtEnd2);
        LocalDateTime dtStart3 = LocalDateTime.now().minusDays(5);
        LocalDateTime dtEnd3 = dtStart1.plusDays(70);
        AvailableAppointment appointment4 = new AvailableAppointment(boat2, dtStart3, dtEnd3);
        availableAppointmentRepository.save(appointment1);
        availableAppointmentRepository.save(appointment2);
        availableAppointmentRepository.save(appointment3);
        availableAppointmentRepository.save(appointment4);

        // rezervacije
        Reservation reservation1 = new Reservation(regularUser2, cottage1, LocalDateTime.now().minusDays(5), 24, 1, 10000);
        Reservation reservation2 = new Reservation(regularUser2, cottage2, LocalDateTime.now().minusDays(3), 96, 3, 22000);
        Reservation reservation3 = new Reservation(regularUser3, cottage1, LocalDateTime.now(), 48, 3, 27000);
        Reservation reservation4 = new Reservation(regularUser2, cottage1, LocalDateTime.now().plusDays(19), 72, 3, 30000);
        Reservation reservation5 = new Reservation(regularUser3, boat1, LocalDateTime.now().plusDays(5), 48, 3, 27000);
        Reservation reservation6 = new Reservation(regularUser2, boat2, LocalDateTime.now().plusDays(15), 72, 3, 30000);
        Reservation reservation7 = new Reservation(regularUser2, cottage1, LocalDateTime.now().minusDays(15), 24, 1, 13549);
        Reservation reservation8 = new Reservation(regularUser2, cottage2, LocalDateTime.now().plusDays(2), 24, 1, 9586);
        Reservation reservation9 = new Reservation(regularUser2, cottage2, LocalDateTime.now().minusDays(50), 24, 1, 29456);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);
        reservationRepository.save(reservation4);
        reservationRepository.save(reservation5);
        reservationRepository.save(reservation6);
        reservationRepository.save(reservation7);
        reservationRepository.save(reservation8);
        reservationRepository.save(reservation9);

        // brze rezevacije(akcije)
        LocalDateTime dtSpecResStart1 = LocalDateTime.now().plusDays(10);
        LocalDateTime dtValidFrom1 = LocalDateTime.now();
        LocalDateTime dtValidTo1 = dtStart1.plusDays(8);

        Set<AdditionalService> additionalServices1 = new HashSet<>();
        additionalServices1.add(additionalService1);
        additionalServices1.add(additionalService2);
        SpecialReservation specialReservation1 = new SpecialReservation(cottageOwner1, cottage1, dtSpecResStart1, 48, 5, additionalServices1, 15000, dtValidFrom1, dtValidTo1);

        Set<AdditionalService> additionalServices2 = new HashSet<>();
        additionalServices2.add(additionalServiceForBoat1);
        SpecialReservation specialReservation2 = new SpecialReservation(boatOwner1, boat1, dtSpecResStart1, 72, 5, additionalServices2, 1000, dtValidFrom1, dtValidTo1);

        specialReservationRepository.save(specialReservation1);
        specialReservationRepository.save(specialReservation2);

        // ocene
        Review review1 = new Review("najbolja vikendica", LocalDateTime.now().plusDays(15), 10, reservation1);
        review1.setApproved(true);
        Review review2 = new Review("super", LocalDateTime.now().plusDays(10), 7, reservation7);
        Review review3 = new Review("odlicno", LocalDateTime.now().plusDays(25), 9, reservation2);
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);

        // Ucitavanje slika
        String file1 = "src/main/resources/static/images/imgCottage1.jpg";
        String file2 = "src/main/resources/static/images/imgCottage2.jpeg";
        String boatfile1 = "src/main/resources/static/images/boat1.jpg";
        String boatfile2 = "src/main/resources/static/images/boat2img1.jpg";
        String boatfile3 = "src/main/resources/static/images/boat2img2.jpg";
        byte[] img1 = null;
        byte[] img2 = null;
        byte[] boatImg1 = null;
        byte[] boatImg2 = null;
        byte[] boatImg3 = null;

        try {
            img1 = readImage(file1);
            EntityImage entityImage1 = new EntityImage(cottage1, img1, "Vikendica1");
            entityImageRepository.save(entityImage1);

            img2 = readImage(file2);
            EntityImage entityImage2 = new EntityImage(cottage2, img2, "Vikendica2");
            entityImageRepository.save(entityImage2);

            boatImg1 = readImage(boatfile1);
            EntityImage entityImage3 = new EntityImage(boat1, boatImg1, "Brod1");
            entityImageRepository.save(entityImage3);

            boatImg2 = readImage(boatfile2);
            EntityImage entityImage4 = new EntityImage(boat2, boatImg2, "Brod2");
            entityImageRepository.save(entityImage4);

            boatImg3 = readImage(boatfile3);
            EntityImage entityImage5 = new EntityImage(boat2, boatImg3, "Brod2");
            entityImageRepository.save(entityImage5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] readImage(String file) throws IOException {
        byte[] img1 = null;
        try(DataInputStream reader = new DataInputStream(new FileInputStream(file))){
            int nBytesToRead = reader.available();
            if (nBytesToRead > 0) {
                byte[] bytes = new byte[nBytesToRead];
                while (reader.read(bytes) > 0) {
                    img1 = bytes;
                }
            }
            reader.close();
            return img1;
        }
    }
}
