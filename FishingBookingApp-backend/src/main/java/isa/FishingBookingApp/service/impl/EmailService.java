package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.model.*;
import isa.FishingBookingApp.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;
    private Environment env;
    private SubscriptionService subscriptionService;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, Environment env, SubscriptionService subscriptionService) {
        this.javaMailSender = javaMailSender;
        this.env = env;
        this.subscriptionService = subscriptionService;
    }

    @Async
    public void sendVerificationMail(User user) throws MailException, InterruptedException, MessagingException {
        System.out.println("Email sending...");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mail = new MimeMessageHelper(message);

        mail.setTo(user.getMailAddress());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("ISA-PROJEKAT Verifikacija email-a");

        String url = "http://localhost:8080/auth/verify/" + user.getId();

        String text = "Pozdrav " + user.getName() + ",<br>"
                + "Da bi ste verifikovali svoj nalog, posetite sledeću stranicu:<br>"
                + "<h1><a href=" + url + " target=\"_self\">VERIFIKUJ</a></h1> "
                + "Hvala,<br>" + "IsaTim27.";

        mail.setText(text, true);

        javaMailSender.send(message);

        System.out.println("Email sended!");
    }

    @Async
    public void sendReservationInfo(Reservation reservation, Boolean isAction) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mail = new MimeMessageHelper(message);

        mail.setTo(reservation.getUser().getMailAddress());
        mail.setFrom(env.getProperty("spring.mail.username"));
        if (isAction) {
            mail.setSubject("ISA-PROJEKAT Potvrda rezervacije akcije");
        }
        else {
            mail.setSubject("ISA-PROJEKAT Potvrda rezervacije");
        }


        String type = "";
        if (reservation.getReservationEntity().getType().equals("cottage")) {
            type = "vikendicu";
        } else if (reservation.getReservationEntity().getType().equals("boat")) {
            type = "brod";
        } else if (reservation.getReservationEntity().getType().equals("fishingInstructor")) {
            type = "instruktora pecanja";
        }

        String text = "Pozdrav " + reservation.getUser().getName() + ",<br><br>" + "" +
                "Vaša rezervacija za " + type + " <strong>" + reservation.getReservationEntity().getName() +
                "</strong> je uspešno prihvaćena." +
                "<br><br> Detalji rezervacije: <br>" +
                "<table border=\"1\" style=\"width: 100%\">" +
                "<tr><td style=\"width: 20%\">Naziv</td><td style=\"width: 100%\">" + reservation.getReservationEntity().getName() + "</td></tr>" +
                "<tr><td style=\"width: 20%\">Adresa</td><td style=\"width: 100%\">" + reservation.getReservationEntity().getAddress() + "</td></tr>" +
                "<tr><td style=\"width: 20%\">Ukupno dana</td><td style=\"width: 100%\">" + reservation.getDurationInHours() / 24 + "</td></tr>" +
                "<tr><td style=\"width: 20%\">Cena po danu</td><td style=\"width: 100%\">" + reservation.getReservationEntity().getPrice() + "</td></tr>" +
                "</table><br>";

        if(reservation.getAdditionalServices() !=null && reservation.getAdditionalServices().size() != 0) {
            text += "Dodatne usluge:<br><table border=\"1\" style=\"width: 100%\">" +
                    "<tr><th>Naziv</th><th>Cena po danu</th></tr>";
            for (AdditionalService additionalService :
                    reservation.getAdditionalServices()) {
                text += "<tr><td style=\"width: 80%\">" + additionalService.getName() + "</td><td style=\"width: 20%\">" + additionalService.getPrice() + "</td></tr>";

            }
            text += "</table>";
        }

        if (isAction) {
            text += "<h1 align=\"right\">Ukupna akcijska cena: " + reservation.getPrice() + "</h1>";
        } else {
            text += "<h1 align=\"right\">Ukupna cena: " + reservation.getPrice() + "</h1>";
        }

        mail.setText(text, true);

        javaMailSender.send(message);
    }

    @Async
    public void sendCreatedActionInfo(SpecialReservation specialReservation) throws Exception {
        for (User user : subscriptionService.getUsersSubscribedToReservationEntity(specialReservation.getReservationEntity().getId())) {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(message);

            mail.setTo(user.getMailAddress());
            mail.setFrom(env.getProperty("spring.mail.username"));

            String type = "";
            if (specialReservation.getReservationEntity().getType().equals("cottage")) {
                type = "vikendicu";
                mail.setSubject("ISA-PROJEKAT Kreirana je akcija za vikendicu na koju ste pretplaćeni");
            } else if (specialReservation.getReservationEntity().getType().equals("boat")) {
                type = "brod";
                mail.setSubject("ISA-PROJEKAT Kreirana je akcija za brod/čamac na koji ste pretplaćeni");
            } else if (specialReservation.getReservationEntity().getType().equals("fishingInstructor")) {
                type = "instruktora pecanja";
                mail.setSubject("ISA-PROJEKAT Kreirana je akcija za instruktora pecanja na kojeg ste pretplaćeni");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            String text = "Pozdrav " + user.getName() + ",<br><br>" + "" +
                    "Izašla je akcija za " + type + " <strong>" + specialReservation.getReservationEntity().getName() +
                    "</strong> na koju ste pretplaćeni. Akcija važi od " + "<strong>" + specialReservation.getValidFrom().format(formatter) +  "</strong> do " +
                    "<strong>" + specialReservation.getValidTo().format(formatter) + "</strong>" +
                    "<br><br> Detalji akcije: <br>" +
                    "<table border=\"1\" style=\"width: 100%\">" +
                    "<tr><td style=\"width: 20%\">Naziv</td><td style=\"width: 100%\">" + specialReservation.getReservationEntity().getName() + "</td></tr>" +
                    "<tr><td style=\"width: 20%\">Adresa</td><td style=\"width: 100%\">" + specialReservation.getReservationEntity().getAddress() + "</td></tr>" +
                    "<tr><td style=\"width: 20%\">Ukupno dana</td><td style=\"width: 100%\">" + specialReservation.getDurationInHours() / 24 + "</td></tr>" +
                    "<tr><td style=\"width: 20%\">Cena po danu</td><td style=\"width: 100%\">" + specialReservation.getReservationEntity().getPrice() + "</td></tr>" +
                    "</table><br>";

            if(specialReservation.getAdditionalServices() !=null && specialReservation.getAdditionalServices().size() != 0) {
                text += "Dodatne usluge:<br><table border=\"1\" style=\"width: 100%\">" +
                        "<tr><th>Naziv</th><th>Cena po danu</th></tr>";
                for (AdditionalService additionalService :
                        specialReservation.getAdditionalServices()) {
                    text += "<tr><td style=\"width: 80%\">" + additionalService.getName() + "</td><td style=\"width: 20%\">" + additionalService.getPrice() + "</td></tr>";

                }
                text += "</table>";
            }

            text += "<h1 align=\"right\">Ukupna akcijska cena: " + specialReservation.getPrice() + "</h1>";

            mail.setText(text, true);

            javaMailSender.send(message);
        }
    }
}
