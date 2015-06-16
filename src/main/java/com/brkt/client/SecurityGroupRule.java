/*
* Copyright 2015 Bracket Computing, Inc. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License").
* You may not use this file except in compliance with the License.
* A copy of the License is located at
*
* https://github.com/brkt/brkt-java-sdk/blob/master/LICENSE
*
* or in the "license" file accompanying this file. This file is
* distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
* CONDITIONS OF ANY KIND, either express or implied. See the 
* License for the specific language governing permissions and
* limitations under the License.
*/

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
