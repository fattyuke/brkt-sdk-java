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
