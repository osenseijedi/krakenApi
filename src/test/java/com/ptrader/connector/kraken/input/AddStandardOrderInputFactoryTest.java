package com.ptrader.connector.kraken.input;

import com.ptrader.connector.kraken.common.OrderDirection;
import com.ptrader.connector.kraken.common.OrderType;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

public class AddStandardOrderInputFactoryTest {
    private String pair = "XRPUSD";
    private OrderDirection orderDirection = OrderDirection.BUY;
    private BigDecimal volume = BigDecimal.valueOf(50);
    private boolean validate = true;
    private BigDecimal price = BigDecimal.valueOf(10);
    private BigDecimal price2 = BigDecimal.valueOf(20);

    @Test
    public void testCreateMarketOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createMarketOrder(pair, orderDirection, volume, validate);
        assertEquals(order.getOrderType(), OrderType.MARKET);
    }

    @Test
    public void testCreateLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createLimitOrder(pair, orderDirection, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.LIMIT);
    }

    @Test
    public void testCreateStopLossOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createStopLossOrder(pair, orderDirection, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS);
    }

    @Test
    public void testCreateTakeProfitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createTakeProfitOrder(pair, orderDirection, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.TAKE_PROFIT);
    }

    @Test
    public void testCreateStopLossProfitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createStopLossProfitOrder(pair, orderDirection, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_PROFIT);
    }

    @Test
    public void testCreateStopLossProfitLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createStopLossProfitLimitOrder(pair, orderDirection, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_PROFIT_LIMIT);
    }

    @Test
    public void testCreateStopLossLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createStopLossLimitOrder(pair, orderDirection, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_LIMIT);
    }

    @Test
    public void testCreateTakeProfitLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createTakeProfitLimitOrder(pair, orderDirection, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.TAKE_PROFIT_LIMIT);
    }

    @Test
    public void testCreateTrailingStopOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createTrailingStopOrder(pair, orderDirection, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.TRAILING_STOP);
    }

    @Test
    public void testCreateTrailingStopLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createTrailingStopLimitOrder(pair, orderDirection, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.TRAILING_STOP_LIMIT);
    }

    @Test
    public void testCreateStopLossAndLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createStopLossAndLimitOrder(pair, orderDirection, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_AND_LIMIT);
    }

    @Test
    public void testCreateSettlePositionOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createSettlePositionOrder(pair, orderDirection, volume, validate);
        assertEquals(order.getOrderType(), OrderType.SETTLE_POSITION);
    }
}
