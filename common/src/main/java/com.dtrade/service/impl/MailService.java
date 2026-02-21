package com.dtrade.service.impl;

import com.dtrade.model.account.Account;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IMailService;
import com.dtrade.service.ITemplateService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by matvei on 6/7/15.
 */
@Service
public class MailService implements IMailService {

    @Value(value = "${SENDGRID_API_KEY}")
    private String sendGridApiKey;

    @Autowired
    private Configuration freemarkerConfig;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    @Override
    public void sendForgotPasswordMail(Account account) {
        Runnable runnable = ()-> {
            try {
                Template t = freemarkerConfig.getTemplate("recovery-password.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("account", account);

                String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);

                Email from = new Email("support@korono.io");
                String subject = "ResistApp: Password recovery.";

                //mail for tests
                sendMail(account, html, from, subject);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
        executor.execute(runnable);
    }

    @Override
    public void sendReferralMail(Account account) {

        Runnable runnable = () -> {
            try {
                Template t = freemarkerConfig.getTemplate("mail.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("accountLink", "x.resistapp.io/referral?myRef=" + account.getReferral());
                map.put("referralLink", "www.resistapp.io/?ref=" + account.getReferral());
                map.put("account", account);

                String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);

                Email from = new Email("support@korono.io");
                String subject = "Successfully added to the waiting list";

                //mail for tests
                sendMail(account, html, from, subject);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };

        executor.execute(runnable);
    }

    private void sendMail(Account account, String html, Email from, String subject) throws IOException {
        String toMail = account.getMail();
        String sendMail;
        if (toMail.contains("test123")) {
            sendMail = "matvei.kudelin@gmail.com";
        } else {
            sendMail = toMail;
        }

        Email to = new Email(sendMail);
        Content content = new Content("text/html", html);

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        sendRequest(mail, sg, request);
    }

    private static void sendRequest(Mail mail, SendGrid sg, Request request) throws IOException {
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
    }

    @Override
    public void sendRegistrationMail(Account account) {
    }

    public static void main(String[] args) throws IOException {
        Email from = new Email("test@example.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("test@example.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);


        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            sendRequest(mail, sg, request);
        } catch (IOException ex) {
            throw ex;
        }
    }
}
