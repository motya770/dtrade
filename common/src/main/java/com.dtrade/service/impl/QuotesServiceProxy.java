package com.dtrade.service.impl;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.depth.DepthQuote;
import com.dtrade.service.IQuotesServiceProxy;
import com.dtrade.utils.ConsulUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class QuotesServiceProxy implements IQuotesServiceProxy {


    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ConsulUtils consulUtils;


    //@Override
//    public BookOrder getBookOrder(Long diamondId) {
//        try {
//            String url = engineUrl() + "/book-order/get-book-order";
//
//            RequestEntity<?> requestEntity =  RequestEntity.post(new URI(url)).body(diamondId);
//            ResponseEntity<BookOrder> responseEntity = restTemplate.exchange(requestEntity, BookOrder.class);
//            return responseEntity.getBody();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public String getDepthQuotes(Diamond diamond) {

        try {
            String url = consulUtils.engineUrl() + "/graph/get-depth-quotes";
            RequestEntity<?> requestEntity =  RequestEntity.post(new URI(url)).body(diamond);
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
            return responseEntity.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
