package com.brkt.client;

import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Map;

public class ComputingCell extends BrktResource {

    private String gatewayIp;
    private List<String> memberGroups;
    private Map<String, String> metadata;
    private Network network;
    private Constants.Provider provider;
    private Constants.RequestedState requestedState;

    public String getGatewayIp() {
        return gatewayIp;
    }

    public List<String> getMemberGroups() {
        return memberGroups;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Network getNetwork() {
        return network;
    }

    public Constants.Provider getProvider() {
        return provider;
    }

    public Constants.RequestedState getRequestedState() {
        return requestedState;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("provider", provider)
                .toString();
    }
}
