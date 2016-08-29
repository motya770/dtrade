package com.dtrade.service.impl;

import com.dtrade.model.account.Account;
import com.dtrade.service.IMailService;

import com.dtrade.service.ITemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Created by matvei on 6/7/15.
 */
@Service
public class MailService implements IMailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private final static String USER_NAME = "support@unityoptions.com";  // GMail user name (just the part before "@gmail.com")
    private final static String PASSWORD = "pragma1pragma1"; // GMail password
    private final static String from = USER_NAME;
    private final static String pass = PASSWORD;

    private Properties props;
    private String host;

//    @Autowired
//    @Qualifier(value = "taskExecutor")
//    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ITemplateService templateService;

    @PostConstruct
    private void init() {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        this.props = props;
        this.host = host;
    }

    @Override
    public void sendRegistrationMail(Account account) {

        //Runnable runnable = () -> {
            String RECIPIENT = account.getMail();

            String[] to = {RECIPIENT}; // list of recipient email addresses
            String subject = "Регистрация в UnityOptions.";
            String body = templateService.getRegistrationMail(account);

            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);

            try {
                message.setFrom(new InternetAddress(from));
                InternetAddress[] toAddress = new InternetAddress[to.length];

                // To get the array of addresses
                for (int i = 0; i < to.length; i++) {
                    toAddress[i] = new InternetAddress(to[i]);
                }

                for (int i = 0; i < toAddress.length; i++) {
                    message.addRecipient(Message.RecipientType.TO, toAddress[i]);
                }

                System.out.println(body);
                Multipart mp = new MimeMultipart();

                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText("", "utf-8");

                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(body, "text/html; charset=utf-8");

                mp.addBodyPart(textPart);
                mp.addBodyPart(htmlPart);
                message.setContent(mp);

                message.setSubject(subject);
                ///message.setText(body, null, "html");
                Transport transport = session.getTransport("smtp");
                transport.connect(host, from, pass);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
            } catch (AddressException ae) {
                logger.error("{}", ae);
                ae.printStackTrace();
            } catch (MessagingException me) {
                logger.error("{}", me);
                me.printStackTrace();
            }
        //};

        //taskExecutor.execute(runnable);
    }
}
