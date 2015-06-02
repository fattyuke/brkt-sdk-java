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
