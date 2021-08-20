/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author marco
 */
public class EmailSender {

    private final Session session;

    public EmailSender() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        String username = "uipath.analisabr@gmail.com";
        String password = "@u1p4th@";

        this.session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void enviarEmail(String assunto, String msg, List<String> emails) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Message message = new MimeMessage(session);

                    message.setFrom(new InternetAddress("ifpecaboteste@gmail.com"));
                    InternetAddress[] enderecos = new InternetAddress[emails.size()];
                    int i = 0;
                    for (String e : emails) {
                        enderecos[i] = new InternetAddress(e);
                        i++;
                    }

                    message.setRecipients(
                            Message.RecipientType.TO, enderecos);
                    message.setSubject(assunto);

                    MimeBodyPart mimeBodyPart = new MimeBodyPart();
                    mimeBodyPart.setContent(msg, "text/html");

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(mimeBodyPart);

                    message.setContent(multipart);

                    //Transport.send(message);
                } catch (AddressException ex) {
                    Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
}
