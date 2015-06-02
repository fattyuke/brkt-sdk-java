package com.brkt.client;

import java.util.Map;

public class Zone extends BrktResource {
    private String cidrBlock;
    private Map<String, String> metadata;
    private String network;
    private Map<String, String> providerZone;
    private Constants.RequestedState requestedState;
    private Boolean useMainRouteTable;

    public String getCidrBlock() {
        return cidrBlock;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String getNetworkId() {
        return network;
    }

    public Map<String, String> getProviderZone() {
        return providerZone;
    }

    public Constants.RequestedState getRequestedState() {
        return requestedState;
    }

    public Boolean getUseMainRouteTable() {
        return useMainRouteTable;
    }
}
