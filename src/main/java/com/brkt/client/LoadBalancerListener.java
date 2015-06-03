package com.brkt.client;

public class LoadBalancerListener extends BrktResource {
    private Integer instancePort;
    private Constants.Protocol instanceProtocol;
    private Boolean isHealthCheckListener;
    private Integer listenerPort;
    private Constants.Protocol listenerProtocol;
    private String loadBalancer;
    private Boolean stickiness;

    public Integer getInstancePort() {
        return instancePort;
    }

    public Constants.Protocol getInstanceProtocol() {
        return instanceProtocol;
    }

    public Boolean getIsHealthCheckListener() {
        return isHealthCheckListener;
    }

    public Integer getListenerPort() {
        return listenerPort;
    }

    public Constants.Protocol getListenerProtocol() {
        return listenerProtocol;
    }

    public String getLoadBalancerId() {
        return loadBalancer;
    }

    public Boolean getStickiness() {
        return stickiness;
    }
}
