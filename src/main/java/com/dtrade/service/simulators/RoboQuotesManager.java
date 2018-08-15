package com.dtrade.service.simulators;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.service.IDiamondService;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class RoboQuotesManager {

    @Autowired
    private IDiamondService diamondService;

    private List<BitfinexClient> clients = Collections.synchronizedList(new ArrayList<>());

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @EventListener(ContextRefreshedEvent.class)
    public void init(){

        List<Diamond> diamonds = diamondService.getAllAvailable("");
        diamonds.forEach(d-> {
            BitfinexClient client = null;
            try{
                     client = new BitfinexClient(d, diamondService, clients);
                }catch (Exception e){
                     e.printStackTrace();
                }
             clients.add(client);
             client.connect();
        });


        Runnable runnable = ()->{
            clients.forEach(client->{
                if(client.isClosed()){
                    client.reconnect();
                }
            });
        };
        executorService.scheduleAtFixedRate(runnable, 1_000, 5_000, TimeUnit.MILLISECONDS);
    }

    /*
    public static void main( String[] args ) throws Exception {
        BitfinexClient c = new BitfinexClient(diamond, new URI("wss://api.bitfinex.com/ws/2")); //"wss://api.bitfinex.com/ws" )); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        c.connect();
    }*/
}
