package com.brkt.client;

import com.brkt.client.util.BrktRestClient;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private static final Type TYPE_INSTANCE_LIST =
            new TypeToken<ArrayList<Instance>>() {}.getType();

    private static final String VOLUME_ROOT = "/v1/api/config/brktvolume";
    private static final String INSTANCE_ROOT = "/v1/api/config/instance";

    private final BrktRestClient client;

    /**
     * Wraps an {@link IOException} so that the caller doesn't need to put a {@code try/catch}
     * around every service call.
     */
    public static class RuntimeIoException extends RuntimeException {
        public RuntimeIoException(IOException cause) {
            super(cause);
        }
    }

    /**
     * Wraps a {@link com.brkt.client.util.BrktRestClient.HttpError} so that the caller
     * doesn't need to put a {@code try/catch} around every service call.
     */
    public static class RuntimeHttpError extends RuntimeException {
        public final int status;
        public final String message;
        public final byte[] payload;

        public RuntimeHttpError(BrktRestClient.HttpError cause) {
            super(cause);
            this.status = cause.status;
            this.message = cause.message;
            this.payload = cause.payload;
        }
    }

    public BrktService(BrktRestClient client) {
        Preconditions.checkNotNull(client);
        this.client = client;
    }

    /**
     * Wraps {@link BrktRestClient#post} and throws a {@link com.brkt.client.BrktService.RuntimeIoException}
     * or {@link com.brkt.client.BrktService.RuntimeHttpError} if an error occurred.
     */
    private <T> T post(String path, Class<T> myClass, Map<String, Object> elements) {
        try {
            return client.post(path, myClass, elements);
        } catch (IOException e) {
            throw new RuntimeIoException(e);
        } catch (BrktRestClient.HttpError e) {
            throw new RuntimeHttpError(e);
        }
    }

    /**
     * Wraps {@link BrktRestClient#get} and throws a {@link com.brkt.client.BrktService.RuntimeIoException}
     * or {@link com.brkt.client.BrktService.RuntimeHttpError} if an error occurred.
     */
    private <T> T get(String path, Type type) {
        try {
            return client.get(path, type);
        } catch (IOException e) {
            throw new RuntimeIoException(e);
        } catch (BrktRestClient.HttpError e) {
            throw new RuntimeHttpError(e);
        }
    }

    /**
     * Wraps {@link BrktRestClient#delete} and throws a {@link com.brkt.client.BrktService.RuntimeIoException}
     * or {@link com.brkt.client.BrktService.RuntimeHttpError} if an error occurred.
     */
    private <T> T delete(String path, Class<T> myClass) {
        try {
            return client.delete(path, myClass);
        } catch (IOException e) {
            throw new RuntimeIoException(e);
        } catch (BrktRestClient.HttpError e) {
            throw new RuntimeHttpError(e);
        }
    }

    public List<OperatingSystem> getOperatingSystems() {
        return get("/v1/api/config/operatingsystem", TYPE_OPERATING_SYSTEM_LIST);
    }

    public List<ImageDefinition> getImageDefinitions() {
        return get("/v1/api/config/imagedefinition", TYPE_IMAGE_DEFINITION_LIST);
    }

    public List<CspImage> getCspImages() {
        return get("/v1/api/config/cspimage", TYPE_CSP_IMAGE_LIST);
    }

    public List<MachineType> getMachineTypes() {
        return get("/v1/api/config/machinetype", TYPE_MACHINE_TYPE_LIST);
    }

    public List<Volume> getAllVolumes() {
        return get(VOLUME_ROOT, TYPE_VOLUME_LIST);
    }

    public Volume getVolume(String volumeId) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s", VOLUME_ROOT, volumeId);
        return get(uri, Volume.class);
    }

    public Volume updateVolume(String volumeId, String fieldName, Object value) {
        Map<String, Object> elements = ImmutableMap.of(fieldName, value);
        return updateVolume(volumeId, elements);
    }

    public Volume updateVolume(String volumeId, Map<String, Object> elements) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s", VOLUME_ROOT, volumeId);
        return post(uri, Volume.class, elements);
    }

    public Volume createVolume(Map<String, Object> elements) {
        return post(VOLUME_ROOT, Volume.class, elements);
    }

    public Volume deleteVolume(String volumeId) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s", VOLUME_ROOT, volumeId);
        return delete(uri, Volume.class);
    }

    public List<Instance> getAllInstances() {
        return get(INSTANCE_ROOT, TYPE_INSTANCE_LIST);
    }

    public Instance getInstance(String instanceId) {
        Preconditions.checkNotNull(instanceId);
        String uri = String.format("%s/%s", INSTANCE_ROOT, instanceId);
        return get(uri, Instance.class);
    }
}
