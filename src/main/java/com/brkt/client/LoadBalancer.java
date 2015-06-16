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

import java.util.Map;

public class LoadBalancer extends BrktResource {
    private String billingGroup;
    private Integer healthCheckInterval;
    private String healthCheckListener;
    private String healthCheckTarget;
    private Integer healthCheckTimeout;
    private Integer healthyThreshold;
    private String instancesInService;
    private String instancesOutOfService;
    private Map<String, String> metadata;
    private Map<String, String> providerLoadBalancer;
    private Constants.RequestedState requestedState;
    private String securityGroup;
    private String serviceName;
    private String serviceNameFqdn;
    private Integer unhealthyThreshold;
    private String workload;

    public String getBillingGroupId() {
        return billingGroup;
    }

    public Integer getHealthCheckInterval() {
        return healthCheckInterval;
    }

    public String getHealthCheckListener() {
        return healthCheckListener;
    }

    public String getHealthCheckTarget() {
        return healthCheckTarget;
    }

    public Integer getHealthCheckTimeout() {
        return healthCheckTimeout;
    }

    public Integer getHealthyThreshold() {
        return healthyThreshold;
    }

    public String getInstancesInService() {
        return instancesInService;
    }

    public String getInstancesOutOfService() {
        return instancesOutOfService;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Map<String, String> getProviderLoadBalancer() {
        return providerLoadBalancer;
    }

    public Constants.RequestedState getRequestedState() {
        return requestedState;
    }

    public String getSecurityGroupId() {
        return securityGroup;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceNameFqdn() {
        return serviceNameFqdn;
    }

    public Integer getUnhealthyThreshold() {
        return unhealthyThreshold;
    }

    public String getWorkloadId() {
        return workload;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("requestedState", requestedState)
                .toString();
    }
}
