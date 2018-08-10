package com.dtrade.service.simulators;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class RoboQuotesManager {

    @EventListener(ContextRefreshedEvent.class)
    public void init(){

    }

    public static class BitfinexClient extends WebSocketClient {

        public BitfinexClient( URI serverURI ) {
            super( serverURI );
        }

        @Override
        public void onOpen( ServerHandshake handshakedata ) {
            String ping = "{\"event\":\"ping\",\"cid\": 1234 }";

            String subscribe = "{ \"event\": \"subscribe\",  \"channel\": \"ticker\",  \"symbol\": \"tBTCUSD\"}";
            send(subscribe);
            System.out.println( "opened connection" );
            // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
        }

        @Override
        public void onMessage( String message ) {
            System.out.println( "received: " + message );
        }

        @Override
        public void onClose( int code, String reason, boolean remote ) {
            // The codecodes are documented in class org.java_websocket.framing.CloseFrame
            System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) + " Code: " + code + " Reason: " + reason );
        }

        @Override
        public void onError( Exception ex ) {
            ex.printStackTrace();
            // if the error is fatal then onClose will be called additionally
        }

        public static void main( String[] args ) throws Exception {
            BitfinexClient c = new BitfinexClient( new URI("wss://api.bitfinex.com/ws/2")); //"wss://api.bitfinex.com/ws" )); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
            c.connect();
        }
    }
}
