package com.ptrader.connector.kraken.input;

import com.ptrader.connector.kraken.input.exceptions.InvalidStateException;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class AddStandardOrderInput implements ApiInput {

    private static class AddStandardOrderInputTimeField {
        Long time;
        boolean isOffset;

        AddStandardOrderInputTimeField(Long time, boolean isOffset) {
            this.time = time;
            this.isOffset = isOffset;
        }


        @Override
        public String toString() {
            return (isOffset ? "+" : "") + time.toString();
        }
    }

    private AddStandardOrderInput(String pair, TransactionType type, double volume, boolean validate) {
        this.pair = pair;
        this.transactionType = type;
        this.volume = volume;
        this.validate = validate;
    }


    public static AddStandardOrderInput createMarketOrder(String pair, TransactionType type, double volume, boolean validate) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.MARKET;

        return obj;
    }

    public static AddStandardOrderInput createLimitOrder(String pair,
                                           TransactionType type,
                                           double volume,
                                           boolean validate,
                                           double limitPrice) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.LIMIT;
        obj.price = Optional.of(limitPrice);

        return obj;
    }

    public static AddStandardOrderInput createStopLossOrder(String pair,
                                              TransactionType type,
                                              double volume,
                                              boolean validate,
                                              double stopLossPrice) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.STOP_LOSS;
        obj.price = Optional.of(stopLossPrice);

        return obj;
    }

    public static AddStandardOrderInput createTakeProfitOrder(String pair,
                                                TransactionType type,
                                                double volume,
                                                boolean validate,
                                                double takeProfitPrice) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.TAKE_PROFIT;
        obj.price = Optional.of(takeProfitPrice);

        return obj;
    }

    public static AddStandardOrderInput createStopLossProfitOrder(String pair,
                                                    TransactionType type,
                                                    double volume,
                                                    boolean validate,
                                                    double stopLossPrice,
                                                    double takeProfitPrice) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.STOP_LOSS_PROFIT;
        obj.price = Optional.of(stopLossPrice);
        obj.price2 = Optional.of(takeProfitPrice);

        return obj;
    }

    public static AddStandardOrderInput createStopLossProfitLimitOrder(String pair,
                                                         TransactionType type,
                                                         double volume,
                                                         boolean validate,
                                                         double stopLossPrice,
                                                         double takeProfitPrice) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.STOP_LOSS_PROFIT_LIMIT;
        obj.price = Optional.of(stopLossPrice);
        obj.price2 = Optional.of(takeProfitPrice);

        return obj;
    }

    public static AddStandardOrderInput createStopLossLimitOrder(String pair,
                                                   TransactionType type,
                                                   double volume,
                                                   boolean validate,
                                                   double stopLossTrigger,
                                                   double triggeredLimitPrice) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.STOP_LOSS_LIMIT;
        obj.price = Optional.of(stopLossTrigger);
        obj.price2 = Optional.of(triggeredLimitPrice);

        return obj;
    }

    public static AddStandardOrderInput createTakeProfitLimitOrder(String pair,
                                                     TransactionType type,
                                                     double volume,
                                                     boolean validate,
                                                     double takeProfitTrigger,
                                                     double triggeredLimitPrice) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.TAKE_PROFIT_LIMIT;
        obj.price = Optional.of(takeProfitTrigger);
        obj.price2 = Optional.of(triggeredLimitPrice);

        return obj;
    }

    public static AddStandardOrderInput createTrailingStopOrder(String pair,
                                                  TransactionType type,
                                                  double volume,
                                                  boolean validate,
                                                  double trailingStopOffset) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.TRAILING_STOP;
        obj.price = Optional.of(trailingStopOffset);
        return obj;
    }

    public static AddStandardOrderInput createTrailingStopLimitOrder(String pair,
                                                       TransactionType type,
                                                       double volume,
                                                       boolean validate,
                                                       double trailingStopOffset,
                                                       double triggeredLimitOffset) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.TRAILING_STOP_LIMIT;
        obj.price = Optional.of(trailingStopOffset);
        obj.price2 = Optional.of(triggeredLimitOffset);
        return obj;
    }

    public static AddStandardOrderInput createStopLossAndLimitOrder(String pair,
                                                      TransactionType type,
                                                      double volume,
                                                      boolean validate,
                                                      double stopLossPrice,
                                                      double limitPrice) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.STOP_LOSS_AND_LIMIT;
        obj.price = Optional.of(stopLossPrice);
        obj.price2 = Optional.of(limitPrice);
        return obj;
    }

    public static AddStandardOrderInput createSettlePositionOrder(String pair,
                                                    TransactionType type,
                                                    double volume,
                                                    boolean validate) {
        AddStandardOrderInput obj = new AddStandardOrderInput(pair, type, volume, validate);
        obj.orderType = OrderType.SETTLE_POSITION;
        return obj;
    }

    private void validateFlags() throws InvalidStateException {
        validateLeverage();
        if (oflags.contains(OrderFlags.POST_ONLY_ORDER) && !orderType.equals(OrderType.LIMIT))
            throw new InvalidStateException("POST_ONLY_ORDER is only valid for LIMIT orders");
    }

    private void validateLeverage() throws InvalidStateException {
        if (oflags.contains(OrderFlags.VOLUME_IN_QUOTE_CURRENCY) && leverage.isPresent())
            throw new InvalidStateException("Can't have leverage with VOLUME_IN_QUOTE_CURRENCY flag");
    }

    // TODO this should be verified

    private String pair;

    private TransactionType transactionType;

    private OrderType orderType;

    private Optional<Double> price;

    private Optional<Double> price2;

    private double volume;

    private Optional<Double> leverage;

    private List<OrderFlags> oflags;

    private Optional<AddStandardOrderInputTimeField> startTime;

    private Optional<AddStandardOrderInputTimeField> expireTime;

    private Optional<String> userReferenceId;

    private boolean validate;

    public void setLeverage(double leverage) throws InvalidStateException {
        this.leverage = Optional.of(leverage);
        validateLeverage();
    }

    public void removeLeverage() throws InvalidStateException {
        this.leverage = Optional.empty();
        validateLeverage();
    }

    public void setFlags(OrderFlags... flags) throws InvalidStateException {
        this.oflags = Arrays.asList(flags);
        validateFlags();
    }

    public void removeFlags() {
        this.oflags = Collections.emptyList();
    }

    public void setStartTimeOffsetInSeconds(Long offsetInSeconds) {
        this.startTime = Optional.of(new AddStandardOrderInputTimeField(offsetInSeconds, true));
    }

    public void setStartTime(Long epoch) {
        this.startTime = Optional.of(new AddStandardOrderInputTimeField(epoch, false));
    }

    public void removeStartTime() {
        this.startTime = Optional.empty();
    }

    public void setExpireTimeOffsetInSeconds(Long offsetInSeconds) {
        this.expireTime = Optional.of(new AddStandardOrderInputTimeField(offsetInSeconds, true));
    }

    public void setExpireTime(Long epoch) {
        this.expireTime = Optional.of(new AddStandardOrderInputTimeField(epoch, false));
    }

    public void removeExpireTime() {
        this.expireTime = Optional.empty();
    }

    private void setUserReferenceId(String userReferenceId) {
        this.userReferenceId = Optional.of(userReferenceId);
    }

    private void removeUserReferenceId() {
        this.userReferenceId = Optional.empty();
    }


    @Override
    public Map<String, String> getInput() {
        Map<String, String> res = new HashMap<>();

        res.put("pair", pair);
        res.put("type", transactionType.getValue());
        res.put("ordertype", orderType.getValue());
        price.ifPresent( price -> res.put("price", price.toString()) );
        price2.ifPresent( price -> res.put("price2", price.toString()) );
        res.put("volume", String.valueOf(volume));
        leverage.ifPresent(leverage -> res.put("leverage", leverage.toString()));
        res.put("oflags", oflags.stream().map(OrderFlags::getValue).collect(Collectors.joining(",")));
        startTime.ifPresent(time -> res.put("starttm", time.toString()));
        expireTime.ifPresent(time -> res.put("expiretm", time.toString()));
        userReferenceId.ifPresent(userReferenceId -> res.put("expiretm",userReferenceId));
        res.put("validate", "validate");

        return res;
    }
}
