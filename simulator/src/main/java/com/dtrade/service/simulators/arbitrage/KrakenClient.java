package com.dtrade.service.simulators.arbitrage;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.IDiamondService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KrakenClient {

    @Autowired
    private IDiamondService diamondService;

    private RestTemplate restTemplate = new RestTemplate();

    public void execute(List<Diamond> diamonds){

        Map<String, Diamond> map = new HashMap<>();
        StringBuilder builder = new StringBuilder();

        for(int i=0; i<diamonds.size(); i++){
            Diamond diamond = diamonds.get(i);
            builder.append(diamond.getTicketName());
            if(i < (diamonds.size() - 1)){
                builder.append(",");
            }
            map.put(diamond.getTicketName(), diamond);
        }

        String url = "https://api.kraken.com/0/public/Ticker?pair=" + builder.toString();
        String resp = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = new JSONObject(resp);
        JSONObject resultJson = jsonObject.getJSONObject("result");
        for(Map.Entry<String, Diamond> entry: map.entrySet()){
            String  bid = resultJson.getJSONObject(entry.getKey()).getJSONArray("a").getString(0);
            String  ask = resultJson.getJSONObject(entry.getKey()).getJSONArray("a").getString(1);
            diamondService.defineRobotBorders(entry.getValue(), new BigDecimal(bid), new BigDecimal(ask));
        }
    }

    public static void main(String... args){
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.kraken.com/0/public/Ticker?pair=QTUMETH,EOSEUR";
        String resp = restTemplate.getForObject(url, String.class);
        System.out.println(resp);
        JSONObject jsonObject = new JSONObject(resp);
        System.out.println(jsonObject.getJSONObject("result").getJSONObject("EOSEUR").getJSONArray("a").getString(0));

    }

}
