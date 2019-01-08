package com.dtrade.service.simulators.arbitrage;

import com.dtrade.model.config.AssetType;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.TicketProvider;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IQuotesService;
import org.mockito.internal.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class ClientsManager {

    private static final Logger logger = LoggerFactory.getLogger(BitfinexClient.class);

    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private IQuotesService quotesService;

    private List<BitfinexClient> bitfinexClients = Collections.synchronizedList(new ArrayList<>());
    private List<AdvatageClient> advantageClients = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private KrakenClient krakenClient;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    @EventListener(ContextRefreshedEvent.class)
    public void init(){

        List<Diamond> diamonds = diamondService.getEnlistedOrRoboHidden();
        //startBitfinexClients(diamonds);
        startAdvantageProviders(diamonds);
        startKrakenClient(diamonds);
    }

    //TODO fix about restart
    private void startKrakenClient(List<Diamond> diamonds){
        List<Diamond> filtered = diamonds.stream().filter(d->d.getTicketProvider()!=null && d.getTicketProvider().equals(TicketProvider.KRAKEN)).collect(Collectors.toList());
        Runnable runnable = ()->{
            try {
                krakenClient.execute(filtered);
            }catch (Exception e){
                logger.error("{}", e);
            }
        };
        executorService.scheduleAtFixedRate(runnable, 1_000, 20_000, TimeUnit.MILLISECONDS);
    }

    private void startAdvantageProviders(List<Diamond> diamonds){
        diamonds.stream().filter(d->d.getTicketProvider()!=null
                && d.getTicketProvider().equals(TicketProvider.ALPHAVANTAGE)).forEach(d->{
            AdvatageClient advatageClient = new AdvatageClient();
            advatageClient.setDiamond(d);
            advatageClient.setAssetType(AssetType.STOCKS);
            advatageClient.setQuotesService(quotesService);
            advatageClient.setDiamondService(diamondService);
            advantageClients.add(advatageClient);
        });

        //decided rand to lower requests number to advantage api
        Runnable runnable = ()->{

            Random random = new Random();
            int rand = random.nextInt(advantageClients.size());
            try {
                AdvatageClient advatageClient =  advantageClients.get(rand);
                advatageClient.execute();
            }catch (Exception e){
                logger.error("{}", e);
            }
        };

        executorService.scheduleAtFixedRate(runnable, 40_000, 20_000, TimeUnit.MILLISECONDS);
    }

    private void startBitfinexClients(List<Diamond> diamonds){
        diamonds.stream().filter(d->d.getTicketProvider().equals(TicketProvider.BITFINEX)).forEach(d-> {
            BitfinexClient client = null;
            try{
                client = new BitfinexClient(d, diamondService, bitfinexClients);
            }catch (Exception e){
                e.printStackTrace();
            }
            bitfinexClients.add(client);
            client.connect();
        });

        Runnable runnable = ()->{
            try {
                bitfinexClients.forEach(client -> {
                    if (client.isClosed()) {
                        client.reconnect();
                    }
                });
            }catch (Exception e){
                logger.error("{}", e);
            }
        };
        executorService.scheduleAtFixedRate(runnable, 1_000, 5_000, TimeUnit.MILLISECONDS);
    }

    /*
    public static void main( String[] args ) throws Exception {
        BitfinexClient c = new BitfinexClient(diamond, new URI("wss://api.bitfinex.com/ws/2")); //"wss://api.bitfinex.com/ws" )); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        c.connect();
    }*/
}
