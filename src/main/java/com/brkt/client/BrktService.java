package com.brkt.client;

import com.brkt.client.util.BrktRestClient;
import com.google.common.base.Preconditions;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BrktService {

    private static final Type OPERATING_SYSTEM_LIST_TYPE =
            new TypeToken<ArrayList<OperatingSystem>>() {}.getType();

    private final BrktRestClient client;

    public BrktService(BrktRestClient client) {
        Preconditions.checkNotNull(client);
        this.client = client;
    }

    public List<OperatingSystem> getOperatingSystems() {
        return client.get("/v1/api/config/operatingsystem", OPERATING_SYSTEM_LIST_TYPE);
    }
}
