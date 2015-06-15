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
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * Contains fields that are common to all Bracket resources.
 */
abstract public class BrktResource {
    private String id;
    private String customer;
    private String name;
    private String description;
    private String createdBy;
    private String modifiedBy;
    private Timestamp createdTime;
    private Timestamp modifiedTime;


    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public String getCustomerId() {
        return customer;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }
}
