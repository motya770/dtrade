package com.dtrade;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import com.dtrade.model.quote.depth.DepthQuote;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IQuotesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Created by kudelin on 12/7/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QuotesServiceTest  extends BaseTest{

    @Autowired
    private IQuotesService quotesService;

    @Autowired
    private IDiamondService diamondService;

    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testDepthQuotes(){

        Optional<Diamond> diamondOptional = diamondService.getAllAvailable(null).stream().findFirst();
        Pair<List<DepthQuote>, List<DepthQuote>> result = quotesService.getDepthQuotes(diamondOptional.get());

        Assert.assertNotNull(result);
    }
}
