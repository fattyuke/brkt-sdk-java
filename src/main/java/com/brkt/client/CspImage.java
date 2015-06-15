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

public class CspImage extends BrktResource {
    private String provider;
    private ImageDefinition imageDefinition;
    private String cspImageId;
    private Map<String, String> cspSettings;
    private String state;

    public String getProvider() {
        return provider;
    }

    public String getImageDefinitionId() {
        if (imageDefinition != null) {
            return imageDefinition.getId();
        }
        return null;
    }

    public String getCspImageId() {
        return cspImageId;
    }

    public Map<String, String> getCspSettings() {
        return cspSettings;
    }

    public String getState() {
        return state;
    }

    public String toString() {
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("state", state);
        return helper.toString();
    }
}
