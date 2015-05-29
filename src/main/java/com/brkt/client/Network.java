package com.brkt.client;

import java.util.Map;

public class Network extends BrktResource {

    private String cidrBlock;
    private String computingCell;
    private Map<String, String> metadata;
    private Map<String, String> providerNetwork;
    private Constants.RequestedState requestedState;

    public String getCidrBlock() {
        return cidrBlock;
    }

    public String getComputingCellId() {
        return computingCell;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Map<String, String> getProviderNetwork() {
        return providerNetwork;
    }

    public Constants.RequestedState getRequestedState() {
        return requestedState;
    }
}
