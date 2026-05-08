package com.phonebook.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public final class EmailUtil {
    private static final String FROM = "noreply@phonebook.com";
    private static final String HOST = "smtp.gmail.com";
    private static final String USER = "your_email@gmail.com";
    private static final String PASS = "your_app_password";

    private EmailUtil() {}

    public static void sendConfirmationEmail(String to) {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASS);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject("Confirm your email");
            msg.setText("Click to confirm: http://localhost:8080/phonebook/confirm?email=" + to);
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}