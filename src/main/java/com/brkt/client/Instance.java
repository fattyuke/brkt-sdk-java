package com.brkt.client;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class Instance extends BrktResource {

    public static final String DESCRIPTION = "description";
    public static final String LEASE_EXPIRE_TIME = "lease_expire_time";
    public static final String LOAD_BALANCER = "load_balancer";
    public static final String METADATA = "metadata";
    public static final String NAME = "name";
    public static final String SECURITY_GROUPS = "security_groups";
    public static final String SERVICE_NAME = "service_name";

    private BigDecimal baseHourlyRate;
    private String billingGroup;
    private String computingCell;
    private Integer cpuCores;
    private CspImage cspImage;
    private BigDecimal dailyCost;
    private String guestImageId;
    private BigDecimal hourlyCost;
    private String instanceTemplate;
    private Boolean internetAccessible;
    private String internetIpAddress;
    private String ipAddress;
    private Timestamp leaseExpireTime;
    private String loadBalancer;
    private Map<String, String> metadata;
    private BigDecimal monthlyCost;
    private String nextImageId;
    private String osImage;
    private Map<String, String> providerInstance;
    private BigDecimal ram;
    private Constants.RequestedState requestedState;
    private List<String> securityGroups;
    private String serviceName;
    private String serviceNameFqdn;
    private String workload;
    private Timestamp workloadLeaseExpireTime;

    public BigDecimal getBaseHourlyRate() {
        return baseHourlyRate;
    }

    public String getBillingGroupId() {
        return billingGroup;
    }

    public String getComputingCellId() {
        return computingCell;
    }

    public Integer getCpuCores() {
        return cpuCores;
    }

    public CspImage getCspImage() {
        return cspImage;
    }

    public BigDecimal getDailyCost() {
        return dailyCost;
    }

    public String getGuestImageId() {
        return guestImageId;
    }

    public BigDecimal getHourlyCost() {
        return hourlyCost;
    }

    public String getInstanceTemplateId() {
        return instanceTemplate;
    }

    public Boolean isInternetAccessible() {
        return internetAccessible;
    }

    public String getInternetIpAddress() {
        return internetIpAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Timestamp getLeaseExpireTime() {
        return leaseExpireTime;
    }

    public String getLoadBalancerId() {
        return loadBalancer;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public BigDecimal getMonthlyCost() {
        return monthlyCost;
    }

    public String getNextImageId() {
        return nextImageId;
    }

    public String getOsImage() {
        return osImage;
    }

    public Map<String, String> getProviderInstance() {
        return providerInstance;
    }

    public BigDecimal getRam() {
        return ram;
    }

    public Constants.RequestedState getRequestedState() {
        return requestedState;
    }

    public List<String> getSecurityGroupIds() {
        return securityGroups;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceNameFqdn() {
        return serviceNameFqdn;
    }

    public String getWorkloadId() {
        return workload;
    }

    public Timestamp getWorkloadLeaseExpireTime() {
        return workloadLeaseExpireTime;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("requestedState", requestedState)
                .toString();
    }
}
