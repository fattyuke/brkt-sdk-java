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
