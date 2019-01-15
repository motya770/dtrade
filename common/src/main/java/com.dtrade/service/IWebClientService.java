package com.dtrade.service;

import org.springframework.web.reactive.function.client.WebClient;

public interface IWebClientService  {
     WebClient.ResponseSpec getPostResponse(String url, Object body);

     WebClient.ResponseSpec getGetResponse(String url);
}
