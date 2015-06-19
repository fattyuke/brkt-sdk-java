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

import java.sql.Timestamp;
import java.util.Map;

public class OperatingSystem extends BrktResource {

    private String label;
    private Map<String, String> metadata;
    private Map<String, String> osFeatures;
    private String platform;
    private String version;

    public String getLabel() {
        return label;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Map<String, String> getOsFeatures() {
        return osFeatures;
    }

    public String getPlatform() {
        return platform;
    }

    public String getVersion() {
        return version;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("label", label)
                .add("platform", platform)
                .add("version", version).toString();
    }
}
