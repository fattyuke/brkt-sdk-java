package com.brkt.client;

import com.google.common.base.MoreObjects;

import java.util.Map;

public class CspImage extends BrktResource {
    private String provider;
    private ImageDefinition imageDefinition;
    private String cspImageId;
    private Map<String, String> cspSettings;
    private String state;

    public String getProvider() {
        return provider;
    }

    public String getImageDefinitionId() {
        if (imageDefinition != null) {
            return imageDefinition.getId();
        }
        return null;
    }

    public String getCspImageId() {
        return cspImageId;
    }

    public Map<String, String> getCspSettings() {
        return cspSettings;
    }

    public String getState() {
        return state;
    }

    public String toString() {
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("state", state);
        return helper.toString();
    }
}
