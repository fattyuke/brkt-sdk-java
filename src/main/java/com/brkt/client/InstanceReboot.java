package com.brkt.client;

public class InstanceReboot {

    private String reason;
    private String requestId;
    private Constants.RebootStatus status;

    public String getReason() {
        return reason;
    }

    public String getRequestId() {
        return requestId;
    }

    public Constants.RebootStatus getStatus() {
        return status;
    }
}
