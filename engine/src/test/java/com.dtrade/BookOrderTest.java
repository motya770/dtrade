package com.dtrade;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookOrderTest extends BaseTest {

    @Autowired
    private IBookOrderService bookOrderService;

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testBookOrder(){

        TradeOrder order = createTestBuyTradeOrder();
        System.out.println("Order: " + order.getId());

        bookOrderService.addNew(order, false);

        BookOrder bookOrder = bookOrderService.getBookOrder(order.getDiamond().getId());

        if(bookOrder.getBuyOrders().contains(order)){
            System.out.println("BUY.");
            bookOrderService.remove(order);
        }

        if(bookOrder.getBuyOrders().contains(order)){
            System.out.println("SELL STILL CONTAINS.");
        }
    }
}
