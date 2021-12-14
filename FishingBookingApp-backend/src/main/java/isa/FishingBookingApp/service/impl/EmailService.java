package isa.FishingBookingApp.service.impl;

import isa.FishingBookingApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;
    private Environment env;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, Environment env) {
        this.javaMailSender = javaMailSender;
        this.env = env;
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
                + "<h1><a href=" + url + " target=\"_self\">VERIFIKUJ</a></h1>"
                + "Hvala,<br>" + "IsaTim27.";

        mail.setText(text, true);

        javaMailSender.send(message);

        System.out.println("Email sended!");
    }
}
