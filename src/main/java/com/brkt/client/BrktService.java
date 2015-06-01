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

/**
 * High-level abstraction of the Bracket service.  Sends request to the
 * service and returns responses as Java objects.
 */
public class BrktService {

    public static final Type TYPE_OPERATING_SYSTEM_LIST =
            new TypeToken<ArrayList<OperatingSystem>>() {}.getType();
    public static final Type TYPE_IMAGE_DEFINITION_LIST =
            new TypeToken<ArrayList<ImageDefinition>>() {}.getType();
    public static final Type TYPE_CSP_IMAGE_LIST =
            new TypeToken<ArrayList<CspImage>>() {}.getType();
    public static final Type TYPE_MACHINE_TYPE_LIST =
            new TypeToken<ArrayList<MachineType>>() {}.getType();
    public static final Type TYPE_BILLING_GROUP_LIST =
            new TypeToken<ArrayList<BillingGroup>>() {}.getType();
    public static final Type TYPE_COMPUTING_CELL_LIST =
            new TypeToken<ArrayList<ComputingCell>>() {}.getType();
    public static final Type TYPE_VOLUME_LIST =
            new TypeToken<ArrayList<Volume>>() {}.getType();
    public static final Type TYPE_INSTANCE_LIST =
            new TypeToken<ArrayList<Instance>>() {}.getType();
    public static final Type TYPE_WORKLOAD_LIST =
            new TypeToken<ArrayList<Workload>>() {}.getType();

    public static final String OPERATING_SYSTEM_ROOT = "/v1/api/config/operatingsystem";
    public static final String IMAGE_DEFINITION_ROOT = "/v1/api/config/imagedefinition";
    public static final String CSP_IMAGE_ROOT = "/v1/api/config/cspimage";
    public static final String MACHINE_TYPE_ROOT = "/v1/api/config/machinetype";
    public static final String BILLING_GROUP_ROOT = "/v1/api/config/billinggroup";
    public static final String COMPUTING_CELL_ROOT = "/v1/api/config/computingcell";
    public static final String VOLUME_ROOT = "/v1/api/config/brktvolume";
    public static final String INSTANCE_ROOT = "/v2/api/config/instance";
    public static final String V1_INSTANCE_ROOT = "/v1/api/config/instance";
    public static final String WORKLOAD_ROOT = "/v2/api/config/workload";

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

    // Operating system.
    public List<OperatingSystem> getAllOperatingSystems() {
        return get(OPERATING_SYSTEM_ROOT, TYPE_OPERATING_SYSTEM_LIST);
    }

    public OperatingSystem getOperatingSystem(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", OPERATING_SYSTEM_ROOT, id);
        return get(uri, OperatingSystem.class);
    }

    public List<ImageDefinition> getOperatingSystemImageDefinitions(String osId) {
        Preconditions.checkNotNull(osId);
        String uri = String.format("%s/%s/imagedefinitions", OPERATING_SYSTEM_ROOT, osId);
        return get(uri, TYPE_IMAGE_DEFINITION_LIST);
    }

    // Image definition.
    public List<ImageDefinition> getAllImageDefinitions() {
        return get(IMAGE_DEFINITION_ROOT, TYPE_IMAGE_DEFINITION_LIST);
    }

    public ImageDefinition getImageDefinition(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", IMAGE_DEFINITION_ROOT, id);
        return get(uri, ImageDefinition.class);
    }

    public List<CspImage> getImageDefinitionCspImages(String imageDefinitionId) {
        Preconditions.checkNotNull(imageDefinitionId);
        String uri = String.format("%s/%s/cspimages", IMAGE_DEFINITION_ROOT, imageDefinitionId);
        return get(uri, TYPE_CSP_IMAGE_LIST);
    }

    // CSP image.
    public List<CspImage> getAllCspImages() {
        return get(CSP_IMAGE_ROOT, TYPE_CSP_IMAGE_LIST);
    }

    public CspImage getCspImage(String id) {
        String uri = String.format("%s/%s", CSP_IMAGE_ROOT, id);
        return get(uri, CspImage.class);
    }

    // Machine type.
    public List<MachineType> getAllMachineTypes() {
        return get(MACHINE_TYPE_ROOT, TYPE_MACHINE_TYPE_LIST);
    }

    public MachineType getMachineType(String id) {
        String uri = String.format("%s/%s", MACHINE_TYPE_ROOT, id);
        return get(uri, MachineType.class);
    }

    // Billing group.
    public List<BillingGroup> getAllBillingGroups() {
        return get(BILLING_GROUP_ROOT, TYPE_BILLING_GROUP_LIST);
    }

    public BillingGroup getBillingGroup(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", BILLING_GROUP_ROOT, id);
        return get(uri, BillingGroup.class);
    }

    public BillingGroup createBillingGroup(Map<String, Object> attrs) {
        return post(BILLING_GROUP_ROOT, BillingGroup.class, attrs);
    }

    public BillingGroup updateBillingGroup(String id, Map<String, Object> attrs) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", BILLING_GROUP_ROOT, id);
        return post(uri, BillingGroup.class, attrs);
    }

    public void deleteBillingGroup(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", BILLING_GROUP_ROOT, id);
        delete(uri, BillingGroup.class);
    }

    // Computing cell.
    public List<ComputingCell> getAllComputingCells() {
        return get(COMPUTING_CELL_ROOT, TYPE_COMPUTING_CELL_LIST);
    }

    public ComputingCell getComputingCell(String computingCellId) {
        Preconditions.checkNotNull(computingCellId);
        String uri = String.format("%s/%s", COMPUTING_CELL_ROOT, computingCellId);
        return get(uri, ComputingCell.class);
    }

    public ComputingCell updateComputingCell(String computingCellId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(computingCellId);
        String uri = String.format("%s/%s", COMPUTING_CELL_ROOT, computingCellId);
        return post(uri, ComputingCell.class, attrs);
    }

    public ComputingCell updateComputingCell(String computingCellId, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateComputingCell(computingCellId, attrs);
    }

    public List<Volume> getComputingCellVolumes(String computingCellId) {
        Preconditions.checkNotNull(computingCellId);
        String uri = String.format("%s/%s/brktvolumes", COMPUTING_CELL_ROOT, computingCellId);
        return get(uri, TYPE_VOLUME_LIST);
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

    public List<Volume> getVolumeChildren(String volumeId) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s/children", VOLUME_ROOT, volumeId);
        return get(uri, TYPE_VOLUME_LIST);
    }

    public Volume snapshotVolume(String volumeId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s/snapshot", VOLUME_ROOT, volumeId);
        return post(uri, Volume.class, attrs);
    }

    public Volume cloneVolume(String volumeId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s/clone", VOLUME_ROOT, volumeId);
        return post(uri, Volume.class, attrs);
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

    public Instance deleteInstance(String instanceId) {
        Preconditions.checkNotNull(instanceId);
        String uri = String.format("%s/%s", INSTANCE_ROOT, instanceId);
        return delete(uri, Instance.class);
    }

    public Instance createInstance(Map<String, Object> attrs) {
        return post(INSTANCE_ROOT, Instance.class, attrs);
    }

    public InstanceReboot rebootInstance(String instanceId) {
        Preconditions.checkNotNull(instanceId);
        String uri = String.format("%s/%s/reboot", INSTANCE_ROOT, instanceId);
        return post(uri, InstanceReboot.class, null);
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
