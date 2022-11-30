package com.zw.service;

import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static javax.mail.internet.InternetAddress.parse;

@Slf4j
public class EmailService {

    private String username;
    private String password;

    public EmailService(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public static void sendMailTest() {

//        String direction = "zwvs@163.com"; // target email
        String direction = "913352800@qq.com"; // target email

        String HOST = "zwvs@163.com";
        String PASSWORD = "****";
        Properties props = new Properties();
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.host", "smtp.163.com");
        props.setProperty("mail.transport.protocol", "smtp");
//        Session session = Session.getInstance(props);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(HOST, PASSWORD);
            }
        });

//        Transport transport = null;
        Message msg = new MimeMessage(session);
        try {
            msg.setSubject("hey pig22222");
//            msg.setText("hello pig pig");
            msg.setFrom(new InternetAddress(HOST));
            msg.setRecipients(Message.RecipientType.TO, parse(direction + ",zwvs@163.com"));

            // /var/tmp/github/db
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File("/var/tmp/github/db/weiz.json"));

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent("this is a new msg", "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            msg.setContent(multipart);
            Transport.send(msg);

//            transport = session.getTransport();
//            transport.connect("smtp.163.com", HOST, PASSWORD);
//            transport.sendMessage(msg, new Address[]{new InternetAddress(direction)});

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            try {
//                transport.close();
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
        }

    }
}
