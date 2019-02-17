package com.dtrade.service.impl;

import com.dtrade.service.IWebClientService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Service
public class WebClientService implements IWebClientService {

    private WebClient client;

    @PostConstruct
    public void init(){
        client = WebClient
                .builder()
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public Mono<ClientResponse> postExchange(String url, Object body) {
        WebClient.UriSpec<WebClient.RequestBodySpec> request = client.post();
        request.uri(url);
        ((WebClient.RequestBodyUriSpec) request).body(BodyInserters.fromObject(body));
        return  ((WebClient.RequestBodyUriSpec) request).exchange();
    }

    @Override
    public WebClient.ResponseSpec getGetResponse(String url) {
        WebClient.RequestHeadersUriSpec<?> request = client.get();
        request.uri(url);
        WebClient.ResponseSpec responseSpec =  request.retrieve();
        return responseSpec;
    }

    @Override
    public WebClient.ResponseSpec getPostResponse(String url, Object body){

        WebClient.UriSpec<WebClient.RequestBodySpec> request = client.post();
        request.uri(url);
        ((WebClient.RequestBodyUriSpec) request).body(BodyInserters.fromObject(body));
        WebClient.ResponseSpec responseSpec =  ((WebClient.RequestBodyUriSpec) request).retrieve();
        return responseSpec;
    }
}
