package com.brkt.client;

import com.google.common.base.MoreObjects;

import java.util.Map;

public class SecurityGroup extends BrktResource {
    private Map<String, String> metadata;
    private Map<String, String> providerSecurityGroup;
    private Constants.RequestedState requestedState;

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Map<String, String> getProviderSecurityGroup() {
        return providerSecurityGroup;
    }

    public Constants.RequestedState getRequestedState() {
        return requestedState;
    }


    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("requestedState", requestedState)
                .toString();
    }
}
