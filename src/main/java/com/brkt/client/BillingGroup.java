/*
* Copyright 2015 Bracket Computing, Inc. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License").
* You may not use this file except in compliance with the License.
* A copy of the License is located at
*
* https://github.com/brkt/brkt-java-sdk/blob/master/LICENSE
*
* or in the "license" file accompanying this file. This file is
* distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
* CONDITIONS OF ANY KIND, either express or implied. See the 
* License for the specific language governing permissions and
* limitations under the License.
*/

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
