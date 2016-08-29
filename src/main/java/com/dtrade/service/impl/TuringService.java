package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.service.ITuringService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matvei on 4/13/15.
 */
@Service
public class TuringService implements ITuringService {

    private static final Logger logger = LoggerFactory.getLogger(TuringService.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void check(String captcha, String remoteIp) throws Exception {

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://www.google.com/recaptcha/api/siteverify");

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("secret", "6LeIRwUTAAAAAIUuRZk3_AFWmZE1M481-F5l7H8K"));
        params.add(new BasicNameValuePair("response", captcha));
        params.add(new BasicNameValuePair("remoteip", remoteIp));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try (InputStream inputStream = entity.getContent()) {
                JsonNode node = mapper.readTree(inputStream);
                boolean success = node.get("success").asBoolean();
                if (!success) {
                    ArrayNode errors = (ArrayNode) node.get("error-codes");
                    if (errors.size() > 0) {
                        throw new TradeException(errors.get(0).asText());
                    }
                }
            } catch (Exception e) {
                logger.error("{}", e);
                throw new TradeException("Can't check captcha");
            }
        }
    }
}
