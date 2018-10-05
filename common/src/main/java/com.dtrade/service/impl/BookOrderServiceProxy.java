package com.dtrade.service.impl;

import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderServiceProxy;
import com.dtrade.utils.ConsulUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Service
public class BookOrderServiceProxy implements IBookOrderServiceProxy {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ConsulUtils consulUtils;

    @Override
    public String getSpreadForDiamonds(List<Long> diamonds) {
        try {
            String url = consulUtils.engineUrl() + "/book-order/get-diamonds-spread";
            RequestEntity<?> requestEntity = RequestEntity.post(new URI(url)).body(diamonds);
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
            return responseEntity.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BookOrderView getBookOrderView(Long diamondId) {
        try {
            String url = consulUtils.engineUrl() + "/book-order/get-view";
            RequestEntity<?> requestEntity = RequestEntity.post(new URI(url)).body(diamondId);
            ResponseEntity<BookOrderView> responseEntity = restTemplate.exchange(requestEntity, BookOrderView.class);
            return responseEntity.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pair<Diamond, Pair<BigDecimal, BigDecimal>> getSpread(Diamond diamond) {
        try {
            String url = consulUtils.engineUrl() + "/book-order/get-spread";
            RequestEntity<?> requestEntity = RequestEntity.post(new URI(url)).body(diamond);
            ResponseEntity<Pair> responseEntity = restTemplate.exchange(requestEntity, Pair.class);
            return (Pair<Diamond, Pair<BigDecimal, BigDecimal>>) responseEntity.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean remove(TradeOrder tradeOrder) {
        String url = consulUtils.engineUrl() + "/book-order/remove";
        boolean result = restTemplate.postForObject(url, tradeOrder, boolean.class);
        System.out.println("result: " + result);
        return result;
    }

    @Override
    public boolean addNew(TradeOrder tradeOrder) {
        try {
            String url = consulUtils.engineUrl() + "/book-order/add-new";
            RequestEntity<?> requestEntity = RequestEntity.post(new URI(url)).body(tradeOrder);
            ResponseEntity<?> responseEntity = restTemplate.exchange(requestEntity, String.class);
            if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
