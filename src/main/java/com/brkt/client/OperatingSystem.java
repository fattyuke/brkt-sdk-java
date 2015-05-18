package com.brkt.client;

import com.google.common.base.MoreObjects;

import java.sql.Timestamp;
import java.util.Map;

public class OperatingSystem {

    private String createdBy;
    private Timestamp createdTime;
    private String customer;
    private String description;
    private String id;
    private String label;
    private Map<String, String> metadata;
    private String modifiedBy;
    private Timestamp modifiedTime;
    private String name;
    private Map<String, String> osFeatures;
    private String platform;
    private String version;

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public String getCustomer() {
        return customer;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Map<String, String> getMetadata() {
        return metadata;
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
                .add("id", id)
                .add("name", name)
                .add("label", label)
                .add("platform", platform)
                .add("version", version).toString();
    }
}
