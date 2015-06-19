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

public class LoadBalancerListener extends BrktResource {
    private Integer instancePort;
    private Constants.Protocol instanceProtocol;
    private Boolean isHealthCheckListener;
    private Integer listenerPort;
    private Constants.Protocol listenerProtocol;
    private String loadBalancer;
    private Boolean stickiness;

    public Integer getInstancePort() {
        return instancePort;
    }

    public Constants.Protocol getInstanceProtocol() {
        return instanceProtocol;
    }

    public Boolean getIsHealthCheckListener() {
        return isHealthCheckListener;
    }

    public Integer getListenerPort() {
        return listenerPort;
    }

    public Constants.Protocol getListenerProtocol() {
        return listenerProtocol;
    }

    public String getLoadBalancerId() {
        return loadBalancer;
    }

    public Boolean getStickiness() {
        return stickiness;
    }
}
