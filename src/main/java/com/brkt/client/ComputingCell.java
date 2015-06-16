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

import java.util.List;
import java.util.Map;

public class ComputingCell extends BrktResource {

    private String gatewayIp;
    private List<String> memberGroups;
    private Map<String, String> metadata;
    private Network network;
    private Constants.Provider provider;
    private Constants.RequestedState requestedState;

    public String getGatewayIp() {
        return gatewayIp;
    }

    public List<String> getMemberGroups() {
        return memberGroups;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String getNetworkId() {
        if (network != null) {
            return network.getId();
        }
        return null;
    }

    public Constants.Provider getProvider() {
        return provider;
    }

    public Constants.RequestedState getRequestedState() {
        return requestedState;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("provider", provider)
                .toString();
    }
}
