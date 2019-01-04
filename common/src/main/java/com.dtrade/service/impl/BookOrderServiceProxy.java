package com.dtrade.service.impl;

import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderServiceProxy;
import com.dtrade.utils.ConsulUtils;
import com.dtrade.utils.MyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.LinkedHashMap;
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
