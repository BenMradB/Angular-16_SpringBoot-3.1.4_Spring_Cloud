package com.bilel.SpringBoot_TP01.events.listeners;

import com.bilel.SpringBoot_TP01.entities.User;
import com.bilel.SpringBoot_TP01.events.RegistrationEvent;
import com.bilel.SpringBoot_TP01.services.TokenService;
import com.bilel.SpringBoot_TP01.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {
    private final UserService userService;
    private final TokenService tokenService;
    private final JavaMailSender javaMailSender;
    private User user;

    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        // 1. Get the newly created user (registered user)
        user = event.getUser();

        // 2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();

        // 3. Save the verification token for the user
        tokenService.saveUserVerificationToken(user, verificationToken);

        // 4. Build the verification URL to be sent to the user
        String verificationURL = event.getApplicationURL() + "/api/register/verify?token=" + verificationToken;

        // 5. Send the email
        try {
            sendEmail(verificationURL);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendEmail(String verificationURL) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "Benmrad Bilel";
        String content = "<p> Hi, "+ user.getUsername()+ ", </p>"+
                "<p>Thank you for registering with us,"+" " +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +verificationURL+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Benmrad Bilel";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("bolbolpubg2001@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        javaMailSender.send(message);
    }
}


