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

import java.util.Map;

public class SecurityGroup extends BrktResource {
    private Map<String, String> metadata;
    private Map<String, String> providerSecurityGroup;
    private Constants.RequestedState requestedState;

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Map<String, String> getProviderSecurityGroup() {
        return providerSecurityGroup;
    }

    public Constants.RequestedState getRequestedState() {
        return requestedState;
    }


    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("requestedState", requestedState)
                .toString();
    }
}
