package com.dtrade.service.simulators.arbitrage;

import com.dtrade.model.config.AssetType;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IQuotesService;
import com.dtrade.service.impl.TradeOrderService;
import com.dtrade.utils.MyPair;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class AdvatageClient {

    private static final Logger logger = LoggerFactory.getLogger(AdvatageClient.class);

    private Diamond diamond;
    private AssetType assetType;
    private IQuotesService quotesService;
    private IDiamondService diamondService;

    public void execute(){
        try{
            quotesService.getLandingPrice(diamond.getTicketName(), assetType)
                    .subscribe((v)->{
                        MyPair<?, ?> pair = (MyPair<?, ?>)v;
                        if(pair!=null) {
                            if(pair.first!=null && pair.second!=null) {

                                BigDecimal f = new BigDecimal((String) pair.first);
                                BigDecimal s =  new BigDecimal((String) pair.second);

                                if (f.compareTo(s)==-1) {
                                    diamondService.defineRobotBorders(diamond, f, s);
                                }else{
                                    diamondService.defineRobotBorders(diamond, s, f);
                                }
                            }
                        }
                    });

        }catch (Exception e){
            logger.error("{}", e);
        }
    }
}
