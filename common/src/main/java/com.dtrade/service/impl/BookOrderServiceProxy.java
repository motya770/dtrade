package com.dtrade.service.impl;

import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderServiceProxy;
import com.dtrade.service.IWebClientService;
import com.dtrade.utils.ConsulUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class BookOrderServiceProxy implements IBookOrderServiceProxy {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ConsulUtils consulUtils;

    @Autowired
    private IWebClientService webClient;

    @Override
    public Mono<String> getSpreadForDiamonds(List<Long> diamonds) {
        try {
            String url = consulUtils.engineUrl() + "/book-order/get-diamonds-spread";
            WebClient.ResponseSpec responseSpec =  webClient.getPostResponse(url, diamonds);
            return responseSpec.bodyToMono(String.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Mono<BookOrderView> getBookOrderView(Long diamondId) {
        try {
            String url = consulUtils.engineUrl() + "/book-order/get-view";
            WebClient.ResponseSpec responseSpec = webClient.getPostResponse(url, diamondId);
            return responseSpec.bodyToMono(BookOrderView.class);

        }catch (Exception e){
           log.error("{}", e);
        }
        return null;
    }

    @Override
    public Pair<Diamond, Pair<BigDecimal, BigDecimal>> getSpread(Diamond diamond) {
        try {
            String url = consulUtils.engineUrl() + "/book-order/get-spread";
            RequestEntity<?> requestEntity = RequestEntity.post(new URI(url)).body(diamond);
            ResponseEntity<LinkedHashMap> responseEntity = restTemplate.exchange(requestEntity, LinkedHashMap.class);
            LinkedHashMap<Diamond, LinkedHashMap<Double, Double>> result = (LinkedHashMap<Diamond, LinkedHashMap<Double, Double>>) responseEntity.getBody();

            Pair<?, ?> bidAsk = Pair.of(new BigDecimal(result.get("second").get("first")).setScale(8, RoundingMode.HALF_UP),
                    new BigDecimal(result.get("second").get("second")).setScale(8, RoundingMode.HALF_UP));
            Pair<Diamond, ?> diamondPair = Pair.of(diamond, bidAsk);
            return (Pair<Diamond, Pair<BigDecimal, BigDecimal>>) diamondPair;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean remove(TradeOrder tradeOrder) {
        try{
            String url = consulUtils.engineUrl() + "/book-order/remove";
            System.out.println(url);
            System.out.println(restTemplate);
            System.out.println(tradeOrder);

            RequestEntity<?> requestEntity = RequestEntity.post(new URI(url)).body(tradeOrder);
            ResponseEntity<Boolean> responseEntity = restTemplate.exchange(requestEntity, Boolean.class);
           return  responseEntity.getBody();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mono<Boolean> addNew(TradeOrder tradeOrder) {
        try {
            String url = consulUtils.engineUrl() + "/book-order/add-new";

            WebClient.ResponseSpec responseSpec =  webClient.getPostResponse(url, tradeOrder);
            responseSpec.bodyToMono(String.class);
            RequestEntity<?> requestEntity = RequestEntity.post(new URI(url)).body(tradeOrder);
            ResponseEntity<?> responseEntity = restTemplate.exchange(requestEntity, String.class);

//            if(responseSpec.onStatus(HttpStatus.OK, ()->{})){
//                return null;
//                //return Mono.from(Boolean.TRUE);
//            }else {
//                log.error("Can't open order {}", tradeOrder);
//            }
        }catch (Exception e){
            log.error("{}", e);
        }
        return null;
    }
}
