package com.brkt.client;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

public class Volume extends BrktResource {
    public static final String AUTO_SNAPSHOT_DURATION_DAYS = "auto_snapshot_duration_days";
    public static final String AVAILABILITY = "availability";
    public static final String DESCRIPTION = "description";
    public static final String INSTANCE = "instance";
    public static final String IOPS = "iops";
    public static final String LARGE_IO = "large_io";
    public static final String LEASE_EXPIRE_TIME = "lease_expire_time";
    public static final String METADATA = "metadata";
    public static final String REQUESTED_STATE = "requested_state";
    public static final String SIZE_IN_GB = "size_in_gb";
    public static final String SLO = "slo";
    public static final String VOLUME_TYPE = "volume_type";

    private Integer autoSnapshotDurationDays;
    private Integer availability;
    private String billingGroup;
    private String bracketVolumeTemplate;
    private String computingCell;
    private BigDecimal cost;
    private BigDecimal dailyCost;
    private Boolean deleted;
    private Boolean expired;
    private BigDecimal hourlyCost;
    private String instance;
    private Integer iops;
    private Integer iopsMax;
    private Boolean isReadonly;
    private String iscsiTargetIp;
    private Boolean largeIo;
    private Timestamp leaseExpireTime;
    private Map<String, String> metadata;
    private Integer minIops;
    private Integer minSize;
    private BigDecimal monthlyCost;
    private String parent;
    private Map<String, String> providerBrktVolume;
    private Integer remainingGb;
    private Integer remainingIops;
    private Constants.RequestedState requestedState;
    private Integer sizeInGb;
    private Integer slo;
    private Integer version;
    private Integer volumeType;

    public Integer getAutoSnapshotDurationDays() {
        return autoSnapshotDurationDays;
    }

    public Constants.Availability getAvailability() {
        if (availability == null) {
            return null;
        }
        return Constants.Availability.getById(availability);
    }

    public Constants.ServiceLevelObjective getSlo() {
        if (slo == null) {
            return null;
        }
        return Constants.ServiceLevelObjective.getById(slo);
    }

    public Constants.VolumeType getVolumeType() {
        if (volumeType == null) {
            return null;
        }
        return Constants.VolumeType.getById(volumeType);
    }

    public String getBillingGroupId() {
        return billingGroup;
    }

    public String getBracketVolumeTemplateId() {
        return bracketVolumeTemplate;
    }

    public String getComputingCellId() {
        return computingCell;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public BigDecimal getDailyCost() {
        return dailyCost;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Boolean getExpired() {
        return expired;
    }

    public BigDecimal getHourlyCost() {
        return hourlyCost;
    }

    public String getInstanceId() {
        return instance;
    }

    public Integer getIops() {
        return iops;
    }

    public Integer getIopsMax() {
        return iopsMax;
    }

    public Boolean getIsReadonly() {
        return isReadonly;
    }

    public String getIscsiTargetIp() {
        return iscsiTargetIp;
    }

    public Boolean getLargeIo() {
        return largeIo;
    }

    public Timestamp getLeaseExpireTime() {
        return leaseExpireTime;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Integer getMinIops() {
        return minIops;
    }

    public Integer getMinSize() {
        return minSize;
    }

    public BigDecimal getMonthlyCost() {
        return monthlyCost;
    }

    public String getParent() {
        return parent;
    }

    public Map<String, String> getProviderBrktVolume() {
        return providerBrktVolume;
    }

    public Integer getRemainingGb() {
        return remainingGb;
    }

    public Integer getRemainingIops() {
        return remainingIops;
    }

    public Constants.RequestedState getRequestedState() {
        return requestedState;
    }

    public Integer getSizeInGb() {
        return sizeInGb;
    }

    public Integer getVersion() {
        return version;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("sizeInGb", sizeInGb)
                .add("parentId", parent)
                .add("requestedState", requestedState)
                .toString();
    }
}
