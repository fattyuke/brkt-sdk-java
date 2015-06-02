package com.brkt.client;

public class SecurityGroupRule extends BrktResource {
    private String cidrIp;
    private String ipProto;
    private Boolean isIngress;
    private Integer portRangeFrom;
    private Integer portRangeTo;
    private String securityGroup;
    private String srcSecurityGroup;

    public String getCidrIp() {
        return cidrIp;
    }

    public String getIpProto() {
        return ipProto;
    }

    public Boolean getIsIngress() {
        return isIngress;
    }

    public Integer getPortRangeFrom() {
        return portRangeFrom;
    }

    public Integer getPortRangeTo() {
        return portRangeTo;
    }

    public String getSecurityGroupId() {
        return securityGroup;
    }

    public String getSrcSecurityGroupId() {
        return srcSecurityGroup;
    }
}
