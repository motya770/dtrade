package com.dtrade.service.impl;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.util.Pair;
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
    private DiscoveryClient discoveryClient;

    public String engineUrl() {
        return discoveryClient.getInstances("engine")
                .stream()
                .map(si -> si.getUri())
          .findFirst().get().toString();
    }

    @Override
    public List<Pair<?, ?>> getSpreadForDiamonds(List<Long> diamonds) {
        return null;
    }

    @Override
    public BookOrderView getBookOrderView(Long diamondId) {
        return null;
    }

    @Override
    public Pair<Diamond, Pair<BigDecimal, BigDecimal>> getSpread(Diamond diamond) {
        return null;
    }

    @Override
    public boolean remove(TradeOrder tradeOrder) {
        String url = engineUrl() + "/remove";

        boolean result = restTemplate.postForObject(url, tradeOrder, boolean.class);
        System.out.println("result: " + result);
        return false;
    }

    //@Override
    public BookOrder getBookOrder(Long diamondId) {
        try {
            String url = engineUrl() + "/book-order/get-book-order";

            RequestEntity<?> requestEntity =  RequestEntity.post(new URI(url)).body(diamondId);
            ResponseEntity<BookOrder> responseEntity = restTemplate.exchange(requestEntity, BookOrder.class);
            return responseEntity.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addNew(TradeOrder tradeOrder) {
        return false;
    }
}
