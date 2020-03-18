package com.ptrader.connector.kraken.input;

import com.ptrader.connector.kraken.common.OrderDirection;
import com.ptrader.connector.kraken.common.OrderType;
import com.ptrader.connector.kraken.input.exceptions.InvalidStateException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PACKAGE)
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

    private OrderDirection orderDirection;

    private OrderType orderType;

    private Optional<BigDecimal> price;

    private Optional<BigDecimal> price2;

    private BigDecimal volume;

    private Optional<BigDecimal> leverage;

    private List<OrderFlags> oflags;

    private Optional<AddStandardOrderInputTimeField> startTime;

    private Optional<AddStandardOrderInputTimeField> expireTime;

    private Optional<String> userReferenceId;

    private boolean validateInputOnly;

    public void setLeverage(BigDecimal leverage) throws InvalidStateException {
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
        res.put("type", orderDirection.getValue());
        res.put("ordertype", orderType.getValue());
        price.ifPresent(price -> res.put("price", price.toString()));
        price2.ifPresent(price -> res.put("price2", price.toString()));
        res.put("volume", volume.toPlainString());
        leverage.ifPresent(leverage -> res.put("leverage", leverage.toString()));
        res.put("oflags", oflags.stream().map(OrderFlags::getValue).collect(Collectors.joining(",")));
        startTime.ifPresent(time -> res.put("starttm", time.toString()));
        expireTime.ifPresent(time -> res.put("expiretm", time.toString()));
        userReferenceId.ifPresent(userReferenceId -> res.put("expiretm", userReferenceId));
        res.put("validate", String.valueOf(validateInputOnly));

        return res;
    }
}
