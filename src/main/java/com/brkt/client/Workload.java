package com.brkt.client;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

public class Workload extends BrktResource {

    public static final String BILLING_GROUP = "billing_group";
    public static final String NAME = "name";
    public static final String ZONE = "zone";
    public static final String DESCRIPTION = "description";
    public static final String ENABLE_SERVICE_DOMAIN = "enable_service_domain";
    public static final String ID = "id";
    public static final String LEASE_EXPIRE_TIME = "lease_expire_time";
    public static final String MAX_COST = "max_cost";
    public static final String METADATA = "metadata";
    public static final String REQUESTED_STATE = "requested_state";

    public String billingGroup;
    public Boolean deleted;
    public Boolean enableServiceDomain;
    public Boolean expired;
    public Timestamp leaseExpireTime;
    public Timestamp leaseModifiedTime;
    public BigDecimal maxCost;
    public Map<String, String> metadata;
    public Constants.RequestedState requestedState;
    public String serviceDomain;
    public String workloadTemplate;
    public String zone;

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
