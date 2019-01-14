package com.dtrade.service;

import org.springframework.web.reactive.function.client.WebClient;

public interface IWebClientService  {
     WebClient.ResponseSpec getResponse(String url, Object body);
}
