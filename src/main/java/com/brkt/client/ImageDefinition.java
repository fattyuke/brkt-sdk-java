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

public class ImageDefinition extends BrktResource {
    private boolean isBase;
    private Map<String, String> metadata;
    private OperatingSystem os;
    private Map<String, String> osSettings;
    private String state;
    private boolean isEncrypted;
    private String unencryptedParent;

    public boolean isBase() {
        return isBase;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String getOperatingSystemId() {
        if (os != null) {
            return os.getId();
        }
        return null;
    }

    public Map<String, String> getOsSettings() {
        return osSettings;
    }

    public String getState() {
        return state;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public String getUnencryptedParent() {
        return unencryptedParent;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("state", state)
                .toString();
    }
}
