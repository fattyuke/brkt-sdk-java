package com.brkt.client;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

public class MachineType {
    private String id;
    private BigDecimal ram;
    private Integer storageGb;
    private BigDecimal encryptedInstanceStorageGb;
    private Integer cpuCores;
    private BigDecimal hourlyRate;
    private Boolean ssd;
    private Boolean supportsPv;

    public String getId() {
        return id;
    }

    public BigDecimal getRam() {
        return ram;
    }

    public Integer getStorageGb() {
        return storageGb;
    }

    public BigDecimal getEncryptedInstanceStorageGb() {
        return encryptedInstanceStorageGb;
    }

    public Integer getCpuCores() {
        return cpuCores;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public Boolean getSsd() {
        return ssd;
    }

    public Boolean getSupportsPv() {
        return supportsPv;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("cpuCores", cpuCores)
                .add("ram", ram)
                .add("storageGb", storageGb)
                .toString();
    }
}
