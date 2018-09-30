package com.dtrade.service.impl;


import com.dtrade.model.account.Account;
import com.dtrade.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.annotation.PostConstruct;
import java.io.StringWriter;

/**
 * Created by matvei on 6/7/15.
 */
@Service
public class TemplateService implements ITemplateService {

    private static final String DOMAIN_HOST = "DOMAIN_HOST";

//    @Resource(name="generalSettings")
//    private Map<String, String> generalSettings;

    @Autowired
    private Environment environment;

    private TemplateEngine engine;

    @PostConstruct
    private void init() {

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("XHTML");
        resolver.setSuffix(".html");
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        this.engine = engine;
    }

    @Override
    public String getRegistrationMail(Account account) {

        StringWriter writer = new StringWriter();
        Context context = new Context();

        String domainHost = environment.getProperty(DOMAIN_HOST);

        context.setVariable("linkConfirm",
                domainHost + "/accounts/mail-response?response=confirm&guid=" + account.getGuid());
        context.setVariable("linkCancel", domainHost + "/accounts/mail-response?response=cancel&guid=" + account.getGuid());

        engine.process("registration", context, writer);
        return writer.toString();
    }
}
