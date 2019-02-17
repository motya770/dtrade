package com.dtrade.service;

import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public interface IWebClientService  {

     Mono<ClientResponse> postExchange(String url, Object body);

     WebClient.ResponseSpec getPostResponse(String url, Object body);

     WebClient.ResponseSpec getGetResponse(String url);


}
