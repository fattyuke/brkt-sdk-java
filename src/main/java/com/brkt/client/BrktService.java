package com.brkt.client;

import com.brkt.client.util.BrktRestClient;
import com.google.common.base.Preconditions;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BrktService {

    private static final Type TYPE_OPERATING_SYSTEM_LIST =
            new TypeToken<ArrayList<OperatingSystem>>() {}.getType();
    private static final Type TYPE_IMAGE_DEFINITION_LIST =
            new TypeToken<ArrayList<ImageDefinition>>() {}.getType();
    private static final Type TYPE_CSP_IMAGE_LIST =
            new TypeToken<ArrayList<CspImage>>() {}.getType();
    private static final Type TYPE_MACHINE_TYPE_LIST =
            new TypeToken<ArrayList<MachineType>>() {}.getType();
    private static final Type TYPE_VOLUME_LIST =
            new TypeToken<ArrayList<Volume>>() {}.getType();

    private final BrktRestClient client;

    public BrktService(BrktRestClient client) {
        Preconditions.checkNotNull(client);
        this.client = client;
    }

    public List<OperatingSystem> getOperatingSystems() {
        return client.get("/v1/api/config/operatingsystem", TYPE_OPERATING_SYSTEM_LIST);
    }

    public List<ImageDefinition> getImageDefinitions() {
        return client.get("/v1/api/config/imagedefinition", TYPE_IMAGE_DEFINITION_LIST);
    }

    public List<CspImage> getCspImages() {
        return client.get("/v1/api/config/cspimage", TYPE_CSP_IMAGE_LIST);
    }

    public List<MachineType> getMachineTypes() {
        return client.get("/v1/api/config/machinetype", TYPE_MACHINE_TYPE_LIST);
    }

    public List<Volume> getVolumes() {
        return client.get("/v1/api/config/brktvolume", TYPE_VOLUME_LIST);
    }
}
