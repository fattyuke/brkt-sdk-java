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

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

public class Workload extends BrktResource {

    private String billingGroup;
    private Boolean deleted;
    private Boolean enableServiceDomain;
    private Boolean expired;
    private Timestamp leaseExpireTime;
    private Timestamp leaseModifiedTime;
    private BigDecimal maxCost;
    private Map<String, String> metadata;
    private Constants.RequestedState requestedState;
    private String serviceDomain;
    private String workloadTemplate;
    private String zone;

    public String getBillingGroupId() {
        return billingGroup;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Boolean getEnableServiceDomain() {
        return enableServiceDomain;
    }

    public Boolean getExpired() {
        return expired;
    }

    public Timestamp getLeaseExpireTime() {
        return leaseExpireTime;
    }

    public Timestamp getLeaseModifiedTime() {
        return leaseModifiedTime;
    }

    public BigDecimal getMaxCost() {
        return maxCost;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Constants.RequestedState getRequestedState() {
        return requestedState;
    }

    public String getServiceDomain() {
        return serviceDomain;
    }

    public String getWorkloadTemplateId() {
        return workloadTemplate;
    }

    public String getZoneId() {
        return zone;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("requestedState", requestedState)
                .toString();
    }
}
