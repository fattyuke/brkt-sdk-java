package com.brkt.client;

import java.math.BigDecimal;
import java.util.Map;

public class BillingGroup extends BrktResource {

    BigDecimal allocatedCost;
    BigDecimal allocatedBalance;
    BigDecimal maxHourlyRate;
    Map<String, String> metadata;
    Integer numberOfMembers;
    BigDecimal parentBalance;
    BigDecimal refundableCost;
    BigDecimal spentCost;

    public BigDecimal getAllocatedCost() {
        return allocatedCost;
    }

    public BigDecimal getAllocatedBalance() {
        return allocatedBalance;
    }

    public BigDecimal getMaxHourlyRate() {
        return maxHourlyRate;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Integer getNumberOfMembers() {
        return numberOfMembers;
    }

    public BigDecimal getParentBalance() {
        return parentBalance;
    }

    public BigDecimal getRefundableCost() {
        return refundableCost;
    }

    public BigDecimal getSpentCost() {
        return spentCost;
    }
}
