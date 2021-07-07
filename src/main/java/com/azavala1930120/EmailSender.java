package com.azavala1930120;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class EmailSender {

    private String host = "";
    private int port = 0;
    private String username = "";
    private String password = "";

    public EmailSender(String username, String password) {
        Map<String, String> hostsAvailable = new HashMap<>();
        hostsAvailable.put("Google", "smtp.gmail.com");
        hostsAvailable.put("Microsoft", "smtp-mail.outlook.com");

        Map<String, Integer> portsAvailable = new HashMap<>();
        portsAvailable.put("Google", 25);
        portsAvailable.put("Microsoft", 587);

        this.username = username;
        this.password = password;

        if (username.contains("gmail")) {
            host = hostsAvailable.get("Google");
            port = portsAvailable.get("Google");
        } else if (username.matches("(@live|@Outlook|@hotmail)")) {
            host = hostsAvailable.get("Microsoft");
            port = portsAvailable.get("Microsoft");
        }
    }

    public void sendMail(String receiver, String subject, String msg, List<String> filepaths) {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            if (filepaths.size() > 0) {
                for (String f : filepaths) {
                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                    attachmentBodyPart.attachFile(new File(f));

                    multipart.addBodyPart(attachmentBodyPart);
                }
            }

            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        EmailSender es = new EmailSender("omniculo@gmail.com", "omniculo1245");

        List<String> files = new ArrayList<>();
        files.add("/mnt/d/Programacion/Java/hermes/src/main/java/com/azavala1930120/EmailSender.java");
        files.add("/mnt/d/Programacion/Java/hermes/src/main/java/com/azavala1930120/data/xd.csv");
        files.add("pom.xml");

        es.sendMail("omniculo@gmail.com", "Titulo me la pelas",
                "<h1><span style=\"color: #ff0000;\">xdxdxdxd</span></h1>", files);
    }
}