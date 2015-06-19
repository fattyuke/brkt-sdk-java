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

public class MachineType {
    private Integer cpuCores;
    private BigDecimal encryptedStorageGb;
    private BigDecimal hourlyCost;
    private String id;
    private Integer provider;
    private BigDecimal ram;
    private Integer storageGb;
    private Boolean supportsPv;

    public Integer getCpuCores() {
        return cpuCores;
    }

    public BigDecimal getEncryptedStorageGb() {
        return encryptedStorageGb;
    }

    public BigDecimal getHourlyCost() {
        return hourlyCost;
    }

    public String getId() {
        return id;
    }

    public Constants.Provider getProvider() {
        if (provider == null) {
            return null;
        }
        return Constants.Provider.getById(provider);
    }

    public BigDecimal getRam() {
        return ram;
    }

    public Integer getStorageGb() {
        return storageGb;
    }

    public Boolean getSupportsPv() {
        return supportsPv;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("provider", String.format("%s(%s)", getProvider(), provider))
                .add("cpuCores", cpuCores)
                .add("ram", ram)
                .add("storageGb", storageGb)
                .toString();
    }
}
