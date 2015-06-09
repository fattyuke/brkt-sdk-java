package com.brkt.client;

import com.brkt.client.util.BrktRestClient;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * High-level abstraction of the Bracket service.  Sends request to the
 * service and returns responses as Java objects.
 */
public class BrktService {

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
        return get(Constants.OPERATING_SYSTEM_ROOT, Constants.TYPE_OPERATING_SYSTEM_LIST);
    }

    public OperatingSystem getOperatingSystem(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.OPERATING_SYSTEM_ROOT, id);
        return get(uri, OperatingSystem.class);
    }

    public List<ImageDefinition> getOperatingSystemImageDefinitions(String osId) {
        Preconditions.checkNotNull(osId);
        String uri = String.format("%s/%s/imagedefinitions", Constants.OPERATING_SYSTEM_ROOT, osId);
        return get(uri, Constants.TYPE_IMAGE_DEFINITION_LIST);
    }

    // Image definition.
    public List<ImageDefinition> getAllImageDefinitions() {
        return get(Constants.IMAGE_DEFINITION_ROOT, Constants.TYPE_IMAGE_DEFINITION_LIST);
    }

    public ImageDefinition getImageDefinition(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.IMAGE_DEFINITION_ROOT, id);
        return get(uri, ImageDefinition.class);
    }

    public List<CspImage> getImageDefinitionCspImages(String imageDefinitionId) {
        Preconditions.checkNotNull(imageDefinitionId);
        String uri = String.format("%s/%s/cspimages", Constants.IMAGE_DEFINITION_ROOT, imageDefinitionId);
        return get(uri, Constants.TYPE_CSP_IMAGE_LIST);
    }

    // CSP image.
    public List<CspImage> getAllCspImages() {
        return get(Constants.CSP_IMAGE_ROOT, Constants.TYPE_CSP_IMAGE_LIST);
    }

    public CspImage getCspImage(String id) {
        String uri = String.format("%s/%s", Constants.CSP_IMAGE_ROOT, id);
        return get(uri, CspImage.class);
    }

    // Machine type.
    public List<MachineType> getAllMachineTypes() {
        return get(Constants.MACHINE_TYPE_ROOT, Constants.TYPE_MACHINE_TYPE_LIST);
    }

    public MachineType getMachineType(String id) {
        String uri = String.format("%s/%s", Constants.MACHINE_TYPE_ROOT, id);
        return get(uri, MachineType.class);
    }

    // Billing group.
    public List<BillingGroup> getAllBillingGroups() {
        return get(Constants.BILLING_GROUP_ROOT, Constants.TYPE_BILLING_GROUP_LIST);
    }

    public BillingGroup getBillingGroup(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.BILLING_GROUP_ROOT, id);
        return get(uri, BillingGroup.class);
    }

    public BillingGroup createBillingGroup(Map<String, Object> attrs) {
        return post(Constants.BILLING_GROUP_ROOT, BillingGroup.class, attrs);
    }

    public BillingGroup updateBillingGroup(String id, Map<String, Object> attrs) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.BILLING_GROUP_ROOT, id);
        return post(uri, BillingGroup.class, attrs);
    }

    public BillingGroup updateBillingGroup(String id, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateBillingGroup(id, attrs);
    }

    public void deleteBillingGroup(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.BILLING_GROUP_ROOT, id);
        delete(uri, BillingGroup.class);
    }

    // Network.
    public List<Network> getAllNetworks() {
        return get(Constants.NETWORK_ROOT, Constants.TYPE_NETWORK_LIST);
    }

    public Network getNetwork(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.NETWORK_ROOT, id);
        return get(uri, Network.class);
    }

    // Zone.
    public List<Zone> getAllZones() {
        return get(Constants.ZONE_ROOT, Constants.TYPE_ZONE_LIST);
    }

    public List<Zone> getNetworkZones(String networkId) {
        Preconditions.checkNotNull(networkId);
        String uri = String.format("%s/%s/zones", Constants.NETWORK_ROOT, networkId);
        return get(uri, Constants.TYPE_ZONE_LIST);
    }

    public Zone getZone(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.ZONE_ROOT, id);
        return get(uri, Zone.class);
    }

    // Security group.
    public List<SecurityGroup> getAllSecurityGroups() {
        return get(Constants.SECURITY_GROUP_ROOT, Constants.TYPE_SECURITY_GROUP_LIST);
    }

    public SecurityGroup getSecurityGroup(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.SECURITY_GROUP_ROOT, id);
        return get(uri, SecurityGroup.class);
    }

    public SecurityGroup createSecurityGroup(String networkId, Map<String, Object> attrs) {
        String uri = String.format("%s/%s/securitygroups", Constants.NETWORK_ROOT, networkId);
        return post(uri, SecurityGroup.class, attrs);
    }

    public SecurityGroup updateSecurityGroup(String id, Map<String, Object> attrs) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.SECURITY_GROUP_ROOT, id);
        return post(uri, SecurityGroup.class, attrs);
    }

    public SecurityGroup updateSecurityGroup(String id, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateSecurityGroup(id, attrs);
    }

    public SecurityGroup deleteSecurityGroup(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.SECURITY_GROUP_ROOT, id);
        return delete(uri, SecurityGroup.class);
    }

    // Security group rules.
    public List<SecurityGroupRule> getRulesForSecurityGroup(String securityGroupId) {
        Preconditions.checkNotNull(securityGroupId);
        String uri = String.format("%s/%s/rules", Constants.SECURITY_GROUP_ROOT, securityGroupId);
        return get(uri, Constants.TYPE_SECURITY_GROUP_RULE_LIST);
    }

    public SecurityGroupRule getSecurityGroupRule(String ruleId) {
        Preconditions.checkNotNull(ruleId);
        String uri = String.format("%s/%s", Constants.SECURITY_GROUP_RULE_ROOT, ruleId);
        return get(uri, SecurityGroupRule.class);
    }

    public SecurityGroupRule createSecurityGroupRule(String securityGroupId, Map<String, Object> attrs) {
        String uri = String.format("%s/%s/rules", Constants.SECURITY_GROUP_ROOT, securityGroupId);
        return post(uri, SecurityGroupRule.class, attrs);
    }

    public SecurityGroupRule updateSecurityGroupRule(String ruleId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(ruleId);
        String uri = String.format("%s/%s", Constants.SECURITY_GROUP_RULE_ROOT, ruleId);
        return post(uri, SecurityGroupRule.class, attrs);
    }

    public SecurityGroupRule deleteSecurityGroupRule(String ruleId) {
        Preconditions.checkNotNull(ruleId);
        String uri = String.format("%s/%s", Constants.SECURITY_GROUP_RULE_ROOT, ruleId);
        return delete(uri, SecurityGroupRule.class);
    }

    // Computing cell.
    public List<ComputingCell> getAllComputingCells() {
        return get(Constants.COMPUTING_CELL_ROOT, Constants.TYPE_COMPUTING_CELL_LIST);
    }

    public ComputingCell getComputingCell(String computingCellId) {
        Preconditions.checkNotNull(computingCellId);
        String uri = String.format("%s/%s", Constants.COMPUTING_CELL_ROOT, computingCellId);
        return get(uri, ComputingCell.class);
    }

    public List<Volume> getComputingCellVolumes(String computingCellId) {
        Preconditions.checkNotNull(computingCellId);
        String uri = String.format("%s/%s/brktvolumes", Constants.COMPUTING_CELL_ROOT, computingCellId);
        return get(uri, Constants.TYPE_VOLUME_LIST);
    }

    // Volume.
    public List<Volume> getAllVolumes() {
        return get(Constants.VOLUME_ROOT, Constants.TYPE_VOLUME_LIST);
    }

    public Volume getVolume(String volumeId) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s", Constants.VOLUME_ROOT, volumeId);
        return get(uri, Volume.class);
    }

    public Volume updateVolume(String volumeId, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateVolume(volumeId, attrs);
    }

    public Volume updateVolume(String volumeId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s", Constants.VOLUME_ROOT, volumeId);
        return post(uri, Volume.class, attrs);
    }

    public Volume createVolume(Map<String, Object> attrs) {
        return post(Constants.VOLUME_ROOT, Volume.class, attrs);
    }

    public Volume deleteVolume(String volumeId) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s", Constants.VOLUME_ROOT, volumeId);
        return delete(uri, Volume.class);
    }

    public List<Volume> getVolumeChildren(String volumeId) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s/children", Constants.VOLUME_ROOT, volumeId);
        return get(uri, Constants.TYPE_VOLUME_LIST);
    }

    public Volume snapshotVolume(String volumeId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s/snapshot", Constants.VOLUME_ROOT, volumeId);
        return post(uri, Volume.class, attrs);
    }

    public Volume cloneVolume(String volumeId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(volumeId);
        String uri = String.format("%s/%s/clone", Constants.VOLUME_ROOT, volumeId);
        return post(uri, Volume.class, attrs);
    }

    // CloudInit.
    public List<CloudInit> getAllCloudInits() {
        return get(Constants.CLOUD_INIT_ROOT, Constants.TYPE_CLOUD_INIT_LIST);
    }

    public CloudInit getCloudInit(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.CLOUD_INIT_ROOT, id);
        return get(uri, CloudInit.class);
    }

    public CloudInit createCloudInit(Map<String, Object> attrs) {
        return post(Constants.CLOUD_INIT_ROOT, CloudInit.class, attrs);
    }

    public CloudInit updateCloudInit(String id, Map<String, Object> attrs) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.CLOUD_INIT_ROOT, id);
        return post(uri, CloudInit.class, attrs);
    }

    public CloudInit updateCloudInit(String id, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateCloudInit(id, attrs);
    }

    public CloudInit deleteCloudInit(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.CLOUD_INIT_ROOT, id);
        return delete(uri, CloudInit.class);
    }

    // Instance.
    public List<Instance> getAllInstances() {
        return get(Constants.INSTANCE_ROOT, Constants.TYPE_INSTANCE_LIST);
    }

    public Instance getInstance(String instanceId) {
        Preconditions.checkNotNull(instanceId);
        String uri = String.format("%s/%s", Constants.INSTANCE_ROOT, instanceId);
        return get(uri, Instance.class);
    }

    public Instance updateInstance(String instanceId, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateInstance(instanceId, attrs);
    }

    public Instance updateInstance(String instanceId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(instanceId);
        String uri = String.format("%s/%s", Constants.INSTANCE_ROOT, instanceId);
        return post(uri, Instance.class, attrs);
    }

    public List<Volume> getInstanceVolumes(String instanceId) {
        Preconditions.checkNotNull(instanceId);
        String uri = String.format("%s/%s/brktvolumes", Constants.V1_INSTANCE_ROOT, instanceId);
        return get(uri, Constants.TYPE_VOLUME_LIST);
    }

    public Instance deleteInstance(String instanceId) {
        Preconditions.checkNotNull(instanceId);
        String uri = String.format("%s/%s", Constants.INSTANCE_ROOT, instanceId);
        return delete(uri, Instance.class);
    }

    public Instance createInstance(Map<String, Object> attrs) {
        return post(Constants.INSTANCE_ROOT, Instance.class, attrs);
    }

    public InstanceReboot rebootInstance(String instanceId) {
        Preconditions.checkNotNull(instanceId);
        String uri = String.format("%s/%s/reboot", Constants.INSTANCE_ROOT, instanceId);
        return post(uri, InstanceReboot.class, null);
    }

    // Workload.
    public List<Workload> getAllWorkloads() {
        return get(Constants.WORKLOAD_ROOT, Constants.TYPE_WORKLOAD_LIST);
    }

    public Workload getWorkload(String workloadId) {
        Preconditions.checkNotNull(workloadId);
        String uri = String.format("%s/%s", Constants.WORKLOAD_ROOT, workloadId);
        return get(uri, Workload.class);
    }

    public Workload createWorkload(Map<String, Object> attrs) {
        return post(Constants.WORKLOAD_ROOT, Workload.class, attrs);
    }

    public Workload updateWorkload(String workloadId, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateWorkload(workloadId, attrs);
    }
    
    public Workload updateWorkload(String workloadId, Map<String, Object> attrs) {
        Preconditions.checkNotNull(workloadId);
        String uri = String.format("%s/%s", Constants.WORKLOAD_ROOT, workloadId);
        return post(uri, Workload.class, attrs);
    }

    public List<Instance> getWorkloadInstances(String workloadId) {
        Preconditions.checkNotNull(workloadId);
        String uri = String.format("%s/%s/instance", Constants.WORKLOAD_ROOT, workloadId);
        return get(uri, Constants.TYPE_INSTANCE_LIST);
    }

    public Workload deleteWorkload(String workloadId) {
        Preconditions.checkNotNull(workloadId);
        String uri = String.format("%s/%s", Constants.WORKLOAD_ROOT, workloadId);
        return delete(uri, Workload.class);
    }

    // Load balancer.
    public List<LoadBalancer> getAllLoadBalancers() {
        return get(Constants.LOAD_BALANCER_ROOT, Constants.TYPE_LOAD_BALANCER_LIST);
    }

    public LoadBalancer getLoadBalancer(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.LOAD_BALANCER_ROOT, id);
        return get(uri, LoadBalancer.class);
    }

    public LoadBalancer createLoadBalancer(Map<String, Object> attrs) {
        return post(Constants.LOAD_BALANCER_ROOT, LoadBalancer.class, attrs);
    }

    public LoadBalancer updateLoadBalancer(String id, Map<String, Object> attrs) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.LOAD_BALANCER_ROOT, id);
        return post(uri, LoadBalancer.class, attrs);
    }

    public LoadBalancer updateLoadBalancer(String id, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateLoadBalancer(id, attrs);
    }

    public LoadBalancer deleteLoadBalancer(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.LOAD_BALANCER_ROOT, id);
        return delete(uri, LoadBalancer.class);
    }

    // Load balancer listener.
    public List<LoadBalancerListener> getAllLoadBalancerListeners() {
        return get(Constants.LOAD_BALANCER_LISTENER_ROOT, Constants.TYPE_LOAD_BALANCER_LISTENER_LIST);
    }

    public LoadBalancerListener getLoadBalancerListener(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.LOAD_BALANCER_LISTENER_ROOT, id);
        return get(uri, LoadBalancerListener.class);
    }

    public LoadBalancerListener createLoadBalancerListener(Map<String, Object> attrs) {
        return post(Constants.LOAD_BALANCER_LISTENER_ROOT, LoadBalancerListener.class, attrs);
    }

    public LoadBalancerListener updateLoadBalancerListener(String id, Map<String, Object> attrs) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.LOAD_BALANCER_LISTENER_ROOT, id);
        return post(uri, LoadBalancerListener.class, attrs);
    }

    public LoadBalancerListener updateLoadBalancerListener(String id, String fieldName, Object value) {
        Map<String, Object> attrs = ImmutableMap.of(fieldName, value);
        return updateLoadBalancerListener(id, attrs);
    }

    public LoadBalancerListener deleteLoadBalancerListener(String id) {
        Preconditions.checkNotNull(id);
        String uri = String.format("%s/%s", Constants.LOAD_BALANCER_LISTENER_ROOT, id);
        return delete(uri, LoadBalancerListener.class);
    }
}
