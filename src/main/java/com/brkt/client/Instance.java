/*
* Copyright 2015 Bracket Computing, Inc. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License").
* You may not use this file except in compliance with the License.
* A copy of the License is located at
*
* https://github.com/brkt/brkt-sdk-java/blob/master/LICENSE
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

    private String billingGroup;
    private String cloudinit;
    private Integer cpuCores;
    private CspImage cspImage;
    private Boolean encrypted;
    private String imageDefinition;
    private String instanceTemplate;
    private Boolean internetAccessible;
    private String internetIpAddress;
    private String ipAddress;
    private Timestamp leaseExpireTime;
    private String loadBalancer;
    private String machineType;
    private Map<String, String> metadata;
    private String platform;
    private Map<String, String> providerInstance;
    private BigDecimal ram;
    private Constants.RequestedState requestedState;
    private List<String> securityGroups;
    private String serviceName;
    private String serviceNameFqdn;
    private String workload;
    private String zone;

    public String getBillingGroupId() {
        return billingGroup;
    }

    public String getCloudInitId() {
        return cloudinit;
    }

    public Integer getCpuCores() {
        return cpuCores;
    }

    public String getCspImageId() {
        if (cspImage != null) {
            return cspImage.getId();
        }
        return null;
    }

    public Boolean isEncrypted() {
        return encrypted;
    }

    public String getImageDefinitionId() {
        return imageDefinition;
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

    public String getMachineTypeId() {
        return machineType;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String getPlatform() {
        return platform;
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
