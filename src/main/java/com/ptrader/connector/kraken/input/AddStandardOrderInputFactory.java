package com.ptrader.connector.kraken.input;

import com.ptrader.connector.kraken.common.OrderDirection;
import com.ptrader.connector.kraken.common.OrderType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

public class AddStandardOrderInputFactory {

    private static AddStandardOrderInput.AddStandardOrderInputBuilder buildCommon(String pair,
                                                                                  OrderDirection orderDirection,
                                                                                  BigDecimal volume,
                                                                                  boolean validateInputOnly) {
        return AddStandardOrderInput.builder()
                .pair(pair)
                .orderDirection(orderDirection)
                .volume(volume)
                .validateInputOnly(validateInputOnly)
                .price(Optional.empty())
                .price2(Optional.empty())
                .startTime(Optional.empty())
                .expireTime(Optional.empty())
                .leverage(Optional.empty())
                .userReferenceId(Optional.empty())
                .oflags(Collections.emptyList());
    }

    public static AddStandardOrderInput createMarketOrder(String pair, OrderDirection orderDirection, BigDecimal volume, boolean validateInputOnly) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly).orderType(OrderType.MARKET).build();

    }

    public static AddStandardOrderInput createLimitOrder(String pair,
                                                         OrderDirection orderDirection,
                                                         BigDecimal volume,
                                                         boolean validateInputOnly,
                                                         BigDecimal limitPrice) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly)
                .orderType(OrderType.LIMIT)
                .price(Optional.of(limitPrice)).build();
    }

    public static AddStandardOrderInput createStopLossOrder(String pair,
                                                            OrderDirection orderDirection,
                                                            BigDecimal volume,
                                                            boolean validateInputOnly,
                                                            BigDecimal stopLossPrice) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly)
                .orderType(OrderType.STOP_LOSS)
                .price(Optional.of(stopLossPrice)).build();
    }

    public static AddStandardOrderInput createTakeProfitOrder(String pair,
                                                              OrderDirection orderDirection,
                                                              BigDecimal volume,
                                                              boolean validateInputOnly,
                                                              BigDecimal takeProfitPrice) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly)
                .orderType(OrderType.TAKE_PROFIT)
                .price(Optional.of(takeProfitPrice)).build();
    }

    public static AddStandardOrderInput createStopLossProfitOrder(String pair,
                                                                  OrderDirection orderDirection,
                                                                  BigDecimal volume,
                                                                  boolean validateInputOnly,
                                                                  BigDecimal stopLossPrice,
                                                                  BigDecimal takeProfitPrice) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly)
                .orderType(OrderType.STOP_LOSS_PROFIT)
                .price(Optional.of(stopLossPrice))
                .price2(Optional.of(takeProfitPrice)).build();
    }

    public static AddStandardOrderInput createStopLossProfitLimitOrder(String pair,
                                                                       OrderDirection orderDirection,
                                                                       BigDecimal volume,
                                                                       boolean validateInputOnly,
                                                                       BigDecimal stopLossPrice,
                                                                       BigDecimal takeProfitPrice) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly)
                .orderType(OrderType.STOP_LOSS_PROFIT_LIMIT)
                .price(Optional.of(stopLossPrice))
                .price2(Optional.of(takeProfitPrice)).build();
    }

    public static AddStandardOrderInput createStopLossLimitOrder(String pair,
                                                                 OrderDirection orderDirection,
                                                                 BigDecimal volume,
                                                                 boolean validateInputOnly,
                                                                 BigDecimal stopLossTrigger,
                                                                 BigDecimal triggeredLimitPrice) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly)
                .orderType(OrderType.STOP_LOSS_LIMIT)
                .price(Optional.of(stopLossTrigger))
                .price2(Optional.of(triggeredLimitPrice)).build();
    }

    public static AddStandardOrderInput createTakeProfitLimitOrder(String pair,
                                                                   OrderDirection orderDirection,
                                                                   BigDecimal volume,
                                                                   boolean validateInputOnly,
                                                                   BigDecimal takeProfitTrigger,
                                                                   BigDecimal triggeredLimitPrice) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly)
                .orderType(OrderType.TAKE_PROFIT_LIMIT)
                .price(Optional.of(takeProfitTrigger))
                .price2(Optional.of(triggeredLimitPrice)).build();
    }

    public static AddStandardOrderInput createTrailingStopOrder(String pair,
                                                                OrderDirection orderDirection,
                                                                BigDecimal volume,
                                                                boolean validateInputOnly,
                                                                BigDecimal trailingStopOffset) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly)
                .orderType(OrderType.TRAILING_STOP)
                .price(Optional.of(trailingStopOffset)).build();
    }

    public static AddStandardOrderInput createTrailingStopLimitOrder(String pair,
                                                                     OrderDirection orderDirection,
                                                                     BigDecimal volume,
                                                                     boolean validateInputOnly,
                                                                     BigDecimal trailingStopOffset,
                                                                     BigDecimal triggeredLimitOffset) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly)
                .orderType(OrderType.TRAILING_STOP_LIMIT)
                .price(Optional.of(trailingStopOffset))
                .price2(Optional.of(triggeredLimitOffset)).build();
    }

    public static AddStandardOrderInput createStopLossAndLimitOrder(String pair,
                                                                    OrderDirection orderDirection,
                                                                    BigDecimal volume,
                                                                    boolean validateInputOnly,
                                                                    BigDecimal stopLossPrice,
                                                                    BigDecimal limitPrice) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly)
                .orderType(OrderType.STOP_LOSS_AND_LIMIT)
                .price(Optional.of(stopLossPrice))
                .price2(Optional.of(limitPrice)).build();
    }

    public static AddStandardOrderInput createSettlePositionOrder(String pair,
                                                                  OrderDirection orderDirection,
                                                                  BigDecimal volume,
                                                                  boolean validateInputOnly) {
        return buildCommon(pair, orderDirection, volume, validateInputOnly)
                .orderType(OrderType.SETTLE_POSITION).build();
    }
}
