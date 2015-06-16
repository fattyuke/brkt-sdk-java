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

import java.util.Map;

public class Network extends BrktResource {

    private String cidrBlock;
    private String computingCell;
    private Map<String, String> metadata;
    private Map<String, String> providerNetwork;
    private Constants.RequestedState requestedState;

    public String getCidrBlock() {
        return cidrBlock;
    }

    public String getComputingCellId() {
        return computingCell;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Map<String, String> getProviderNetwork() {
        return providerNetwork;
    }

    public Constants.RequestedState getRequestedState() {
        return requestedState;
    }
}