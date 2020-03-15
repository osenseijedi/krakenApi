package com.ptrader.connector.kraken.input;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

public class AddStandardOrderInputFactoryTest {
    private String pair = "XRPUSD";
    private TransactionType transactionType = TransactionType.BUY;
    private BigDecimal volume = BigDecimal.valueOf(50);
    private boolean validate = true;
    private BigDecimal price = BigDecimal.valueOf(10);
    private BigDecimal price2 = BigDecimal.valueOf(20);

    @Test
    public void testCreateMarketOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createMarketOrder(pair, transactionType, volume, validate);
        assertEquals(order.getOrderType(), OrderType.MARKET);
    }

    @Test
    public void testCreateLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createLimitOrder(pair, transactionType, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.LIMIT);
    }

    @Test
    public void testCreateStopLossOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createStopLossOrder(pair, transactionType, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS);
    }

    @Test
    public void testCreateTakeProfitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createTakeProfitOrder(pair, transactionType, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.TAKE_PROFIT);
    }

    @Test
    public void testCreateStopLossProfitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createStopLossProfitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_PROFIT);
    }

    @Test
    public void testCreateStopLossProfitLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createStopLossProfitLimitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_PROFIT_LIMIT);
    }

    @Test
    public void testCreateStopLossLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createStopLossLimitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_LIMIT);
    }

    @Test
    public void testCreateTakeProfitLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createTakeProfitLimitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.TAKE_PROFIT_LIMIT);
    }

    @Test
    public void testCreateTrailingStopOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createTrailingStopOrder(pair, transactionType, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.TRAILING_STOP);
    }

    @Test
    public void testCreateTrailingStopLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createTrailingStopLimitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.TRAILING_STOP_LIMIT);
    }

    @Test
    public void testCreateStopLossAndLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createStopLossAndLimitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_AND_LIMIT);
    }

    @Test
    public void testCreateSettlePositionOrder() {
        AddStandardOrderInput order = AddStandardOrderInputFactory.createSettlePositionOrder(pair, transactionType, volume, validate);
        assertEquals(order.getOrderType(), OrderType.SETTLE_POSITION);
    }
}