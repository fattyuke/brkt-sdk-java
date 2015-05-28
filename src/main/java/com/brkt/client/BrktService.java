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
    private static final Type TYPE_WORKLOAD_LIST =
            new TypeToken<ArrayList<Workload>>() {}.getType();

    private static final String OPERATING_SYSTEM_ROOT = "/v1/api/config/operatingsystem";
    private static final String IMAGE_DEFINITION_ROOT = "/v1/api/config/imagedefinition";
    private static final String CSP_IMAGE_ROOT = "/v1/api/config/cspimage";
    private static final String MACHINE_TYPE_ROOT = "/v1/api/config/machinetype";
    private static final String VOLUME_ROOT = "/v1/api/config/brktvolume";
    private static final String INSTANCE_ROOT = "/v2/api/config/instance";
    private static final String V1_INSTANCE_ROOT = "/v1/api/config/instance";
    private static final String WORKLOAD_ROOT = "/v2/api/config/workload";

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
    private <T> T post(String path, Class<T> myClass, Map<String, Object> attrs) {
        try {
            return client.post(path, myClass, attrs);
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

    public List<OperatingSystem> getAllOperatingSystems() {
        return get(OPERATING_SYSTEM_ROOT, TYPE_OPERATING_SYSTEM_LIST);
    }

    public OperatingSystem getOperatingSystem(String id) {
        String uri = String.format("%s/%s", OPERATING_SYSTEM_ROOT, id);
        return get(uri, OperatingSystem.class);
    }

    public List<ImageDefinition> getAllImageDefinitions() {
        return get(IMAGE_DEFINITION_ROOT, TYPE_IMAGE_DEFINITION_LIST);
    }

    public ImageDefinition getImageDefinition(String id) {
        String uri = String.format("%s/%s", IMAGE_DEFINITION_ROOT, id);
        return get(uri, ImageDefinition.class);
    }

    public List<CspImage> getAllCspImages() {
        return get(CSP_IMAGE_ROOT, TYPE_CSP_IMAGE_LIST);
    }

    public CspImage getCspImage(String id) {
        String uri = String.format("%s/%s", CSP_IMAGE_ROOT, id);
        return get(uri, CspImage.class);
    }

    public List<MachineType> getAllMachineTypes() {
        return get(MACHINE_TYPE_ROOT, TYPE_MACHINE_TYPE_LIST);
    }

    public MachineType getMachineType(String id) {
        String uri = String.format("%s/%s", MACHINE_TYPE_ROOT, id);
        return get(uri, MachineType.class);
    }

    // Volume.
    public List<Volume> getAllVolumes() {
        return get(VOLUME_ROOT, TYPE_VOLUME_LIST);
    }

    public Volume getVolume(String volumeId) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s", VOLUME_ROOT, volumeId);
        return get(uri, Volume.class);
    }

    public Volume updateVolume(String volumeId, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateVolume(volumeId, attrs);
    }

    public Volume updateVolume(String volumeId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s", VOLUME_ROOT, volumeId);
        return post(uri, Volume.class, attrs);
    }

    public Volume createVolume(Map<String, Object> attrs) {
        return post(VOLUME_ROOT, Volume.class, attrs);
    }

    public Volume deleteVolume(String volumeId) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s", VOLUME_ROOT, volumeId);
        return delete(uri, Volume.class);
    }

    // Instance.
    public List<Instance> getAllInstances() {
        return get(INSTANCE_ROOT, TYPE_INSTANCE_LIST);
    }

    public Instance getInstance(String instanceId) {
        Preconditions.checkNotNull(instanceId);
        String uri = String.format("%s/%s", INSTANCE_ROOT, instanceId);
        return get(uri, Instance.class);
    }

    public Instance updateInstance(String instanceId, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateInstance(instanceId, attrs);
    }

    public Instance updateInstance(String instanceId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(instanceId);
        String uri = String.format("%s/%s", INSTANCE_ROOT, instanceId);
        return post(uri, Instance.class, attrs);
    }

    public List<Volume> getInstanceVolumes(String instanceId) {
        Preconditions.checkNotNull(instanceId);
        String uri = String.format("%s/%s/brktvolumes", V1_INSTANCE_ROOT, instanceId);
        return get(uri, TYPE_VOLUME_LIST);
    }

    public Instance createInstance(Map<String, Object> attrs) {
        return post(INSTANCE_ROOT, Instance.class, attrs);
    }

    // Workload.
    public List<Workload> getAllWorkloads() {
        return get(WORKLOAD_ROOT, TYPE_WORKLOAD_LIST);
    }

    public Workload getWorkload(String workloadId) {
        Preconditions.checkNotNull(workloadId);
        String uri = String.format("%s/%s", WORKLOAD_ROOT, workloadId);
        return get(uri, Workload.class);
    }

    public Workload createWorkload(Map<String, Object> attrs) {
        return post(WORKLOAD_ROOT, Workload.class, attrs);
    }

    public Workload updateWorkload(String workloadId, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateWorkload(workloadId, attrs);
    }
    
    public Workload updateWorkload(String workloadId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(workloadId);
        String uri = String.format("%s/%s", WORKLOAD_ROOT, workloadId);
        return post(uri, Workload.class, attrs);
    }

    public List<Instance> getWorkloadInstances(String workloadId) {
        Preconditions.checkNotNull(workloadId);
        String uri = String.format("%s/%s/instance", WORKLOAD_ROOT, workloadId);
        return get(uri, TYPE_INSTANCE_LIST);
    }

    public Workload deleteWorkload(String workloadId) {
        Preconditions.checkNotNull(workloadId);
        String uri = String.format("%s/%s", WORKLOAD_ROOT, workloadId);
        return delete(uri, Workload.class);
    }
}
