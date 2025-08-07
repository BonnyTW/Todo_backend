package com.backend.Arch.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

   
    public void sendRegistrationEmail(String toEmail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to the Todo App!");
        message.setText("Hello " + username + ",\n\nThanks for registering on our Todo platform!\n\nHave a productive day! 📝");

        mailSender.send(message);
    }
}

