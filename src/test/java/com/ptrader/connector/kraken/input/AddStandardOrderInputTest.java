package com.ptrader.connector.kraken.input;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddStandardOrderInputTest {
    private String pair = "XRPUSD";
    private TransactionType transactionType = TransactionType.BUY;
    private double volume = 50;
    private boolean validate = true;
    private double price = 10;
    private double price2 = 20;

    @Test
    public void createMarketOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createMarketOrder(pair, transactionType, volume, validate);
        assertEquals(order.getOrderType(), OrderType.MARKET);
    }

    @Test
    public void createLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createLimitOrder(pair, transactionType, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.LIMIT);
    }

    @Test
    public void createStopLossOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createStopLossOrder(pair, transactionType, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS);
    }

    @Test
    public void createTakeProfitOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createTakeProfitOrder(pair, transactionType, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.TAKE_PROFIT);
    }

    @Test
    public void createStopLossProfitOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createStopLossProfitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_PROFIT);
    }

    @Test
    public void createStopLossProfitLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createStopLossProfitLimitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_PROFIT_LIMIT);
    }

    @Test
    public void createStopLossLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createStopLossLimitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_LIMIT);
    }

    @Test
    public void createTakeProfitLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createTakeProfitLimitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.TAKE_PROFIT_LIMIT);
    }

    @Test
    public void createTrailingStopOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createTrailingStopOrder(pair, transactionType, volume, validate, price);
        assertEquals(order.getOrderType(), OrderType.TRAILING_STOP);
    }

    @Test
    public void createTrailingStopLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createTrailingStopLimitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.TRAILING_STOP_LIMIT);
    }

    @Test
    public void createStopLossAndLimitOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createStopLossAndLimitOrder(pair, transactionType, volume, validate, price, price2);
        assertEquals(order.getOrderType(), OrderType.STOP_LOSS_AND_LIMIT);
    }

    @Test
    public void createSettlePositionOrder() {
        AddStandardOrderInput order = AddStandardOrderInput.createSettlePositionOrder(pair, transactionType, volume, validate);
        assertEquals(order.getOrderType(), OrderType.SETTLE_POSITION);
    }
}