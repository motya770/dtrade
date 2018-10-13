package com.dtrade.service.simulators.arbitrage;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.IDiamondService;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

public class BitfinexClient extends WebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(BitfinexClient.class);

    private Diamond diamond;
    private IDiamondService diamondService;
    private List<BitfinexClient> clients;
    private static final String BITFINEX_URI = "wss://api.bitfinex.com/ws/2";

    @Override
    public void run() {
        super.run();
    }

    private Diamond getDiamond(){
        return diamond;
    }

    private String getBitfinexPairName(){
        return "t" + diamond.getName().replace("/", "");
    }

    public BitfinexClient(Diamond diamond, IDiamondService diamondService, List<BitfinexClient> clients) throws Exception {
        super( new URI(BITFINEX_URI));
        this.diamond = diamond;
        this.diamondService = diamondService;
        this.clients = clients;
    }

    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        // String ping = "{\"event\":\"ping\",\"cid\": 1234 }";

        String subscribe = "{ \"event\": \"subscribe\",  \"channel\": \"ticker\",  \"symbol\": \""  + getBitfinexPairName()  +  "\"}";
        send(subscribe);
        logger.info( "opened connection" );

        //TODO can create bug because hidden field is gonna be reenlisted
        /*
        if(diamond.getDiamondStatus()!= DiamondStatus.ENLISTED){
            diamond.setDiamondStatus(DiamondStatus.ENLISTED);
            diamondService.update(diamond);
        }*/
    }

    @Override
    public void onMessage( String message ) {
       logger.info( "received: " + message );

            /*
            FRR	float	Flash Return Rate - average of all fixed rate funding over the last hour
            BID	float	Price of last highest bid
            BID_PERIOD	integer	Bid period covered in days
            BID_SIZE	float	Sum of the 25 highest bid sizes
            ASK	float	Price of last lowest ask
            ASK_PERIOD	integer	Ask period covered in days
            ASK_SIZE	float	Sum of the 25 lowest ask sizes
            DAILY_CHANGE	float	Amount that the last price has changed since yesterday
            DAILY_CHANGE_PERC	float	Amount that the price has changed expressed in percentage terms
            LAST_PRICE	float	Price of the last trade.
            VOLUME	float	Daily volume
            HIGH	float	Daily high
            LOW	float	Daily low


            10000 //: Unknown event
            10001 //: Unknown pair
            10305 //: Reached limit of open channels
             */

        if(message.contains("code")){

            JSONObject obj = new JSONObject(message);

            String code =  obj.getString("code");
            if(!StringUtils.isEmpty(code)){
                if (code.equals("10000")){

                }else if (code.equals("10001")){

                }else if(code.equals("10305")){

                }
            }
        }else if (message.contains("[") && !message.contains("hb")) { //not a heartbeat

            JSONArray arr = new JSONArray(message);
            JSONArray values = arr.getJSONArray(1);
           logger.debug("values: " + values);
            BigDecimal bid = new BigDecimal(values.getDouble(0));//BID
            BigDecimal ask = new BigDecimal(values.getDouble(2));//ASK

            diamondService.defineRobotBorders(diamond, bid, ask);
        }

        //[1,[6311.8,21.55044611,6311.9,22.37189934,172.9,0.0282,6311.9,32007.13546903,6499.5,6070.1]]
    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {


        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
       logger.info( "Connection closed by " + ( remote ? "remote peer" : "us" ) + " Code: " + code + " Reason: " + reason );
    }

    @Override
    public void onError( Exception ex ) {
        ex.printStackTrace();

        // if the error is fatal then onClose will be called additionally
    }
}