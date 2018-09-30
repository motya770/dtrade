package com.dtrade.service.impl;

import com.dtrade.model.message.Message;
import com.dtrade.model.quote.Quote;
import com.dtrade.model.tradeorder.TradeOrderDTO;
import com.dtrade.service.IRabbitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RabbitService implements IRabbitService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitService.class);

    public final static String EXCHANGE_NAME = "dtrade_exchange";

    private Channel channel = null;
    private ConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        try {
            connectionFactory = new ConnectionFactory();
            connectionFactory.setAutomaticRecoveryEnabled(true);

            String host = "localhost";

            connectionFactory.setHost(host);
            if(!host.equals("localhost")) {
                connectionFactory.setUsername("quest");
                connectionFactory.setPassword("quest");
            }

            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        } catch (Exception e) {
            logger.error("Can't init messaging", e);
        }
    }

    @Override
    public void tradeOrderExecuted(TradeOrderDTO tradeOrder) {
        sendEntity(tradeOrder, "executed_tradeOrder");
    }
    @Override
    public void tradeOrderCreated(TradeOrderDTO tradeOrder) {
       // sendEntity(tradeOrder, "new_tradeOrder");
    }

    private void sendEntity(Object entity, String routingKey){
        try {
            Message sendMsg = Message.build(routingKey, entity);
            String message = mapper.writeValueAsString(sendMsg);
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
        }catch (Exception e){
            logger.error("Can't send trade order message", e);
        }
    }

    @Override
    public void tradeOrderUpdated(TradeOrderDTO tradeOrder) {
        //sendEntity(tradeOrder, "updated_tradeOrder");
    }

    @Override
    public void quoteCreated(Quote quote) {
        sendEntity(quote, "new_quote");
    }
}
