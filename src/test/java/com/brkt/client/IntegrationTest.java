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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.brkt.client.Constants.RequestedState;
import com.brkt.client.util.BrktRestClient;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IntegrationTest {

    private static final String PREFIX = "Integration Test";

    private final BrktService service;

    static class Arguments {
        @Parameter(description = "http[s]://example.com[:port]")
        List<String> parameters = Lists.newArrayList();

        @Parameter(names = { "--token" }, required = true, description = "Auth token")
        String token;

        @Parameter(names = { "--key" }, required = true, description = "MAC key")
        String macKey;

        @Parameter(names = { "-h", "--help"}, help = true, description = "Show usage")
        boolean help;

        String rootUri;
    }

    private static Arguments parseArgs(String[] stringArgs) {
        Arguments args = new Arguments();
        JCommander jc = new JCommander(args);
        jc.parse(stringArgs);

        if (args.help) {
            jc.setProgramName("run-integration-test");
            jc.usage();
            System.exit(0);
        }
        if (args.parameters.size() != 1) {
            System.err.println("Must specify one server root URI.  Found " + args.parameters.size());
            System.exit(1);
        }

        args.rootUri = args.parameters.get(0);

        return args;
    }

    private IntegrationTest(BrktService service) {
        this.service = service;
    }

    /**
     * Delete any resources that were created by this test run or a
     * previous failed test run.
     */
    private void cleanUp() {
        for (Volume v : service.getAllVolumes()) {
            if (v.getName().startsWith(PREFIX) &&
                    v.getRequestedState() != RequestedState.DELETED) {
                service.deleteVolume(v.getId());
            }
        }
        // No need to explicitly delete instances.  They will automatically
        // get deleted with their workloads.
        for (Workload w : service.getAllWorkloads()) {
            if (w.getName().startsWith(PREFIX) &&
                    w.getRequestedState() != RequestedState.DELETED) {
                service.deleteWorkload(w.getId());
            }
        }
        for (BillingGroup group : service.getAllBillingGroups()) {
            if (group.getName().startsWith(PREFIX)) {
                service.deleteBillingGroup(group.getId());
            }
        }
        for (SecurityGroup sg : service.getAllSecurityGroups()) {
            if (sg.getName().startsWith(PREFIX) &&
                    sg.getRequestedState() != RequestedState.DELETED) {
                service.deleteSecurityGroup(sg.getId());
            }
        }
    }

    private void testOperatingSystem() {
        System.out.println("Testing operating system.");
        boolean foundLinux = false;
        boolean foundUbuntu = false;

        List<OperatingSystem> oss = service.getAllOperatingSystems();
        for (OperatingSystem os : oss) {
            if (os.getPlatform().equals("linux")) {
                foundLinux = true;
            }
            if (os.getLabel().contains("Ubuntu")) {
                foundUbuntu = true;
            }
        }
        assertTrue(foundLinux);
        assertTrue(foundUbuntu);

        OperatingSystem expected = oss.get(0);
        OperatingSystem actual = service.getOperatingSystem(expected.getId());
        assertEquals(expected.getId(), actual.getId());

        // We don't know which OS we have, so just make sure that
        // this endpoint isn't totally broken.
        service.getOperatingSystemImageDefinitions(actual.getId());
    }

    private void testImageDefinition() {
        System.out.println("Testing image definition.");
        boolean foundUbuntu = false;

        List<ImageDefinition> ids = service.getAllImageDefinitions();
        for (ImageDefinition id : ids) {
            if (id.getName().contains("Ubuntu")) {
                foundUbuntu = true;
            }
        }
        assertTrue(foundUbuntu);

        ImageDefinition expected = ids.get(0);
        ImageDefinition actual = service.getImageDefinition(expected.getId());
        assertEquals(expected.getId(), actual.getId());

        // We don't know which image defintion we have, so just make sure
        // that this endpoint isn't totally broken.
        service.getImageDefinitionCspImages(actual.getId());
    }

    private void testCspImage() {
        System.out.println("Testing CSP image.");
        boolean foundAws = false;

        List<CspImage> cis = service.getAllCspImages();
        for (CspImage ci : cis) {
            if (ci.getProvider().equals("AWS")) {
                foundAws = true;
            }
        }
        assertTrue(foundAws);

        CspImage expected = cis.get(0);
        CspImage actual = service.getCspImage(expected.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    private void testMachineType() {
        System.out.println("Testing machine type.");
        boolean foundCpuCores = false;
        boolean foundStorageGb = false;
        boolean foundProvider = false;

        List<MachineType> mts = service.getAllMachineTypes();
        for (MachineType mt : mts) {
            if (mt.getCpuCores() == 4) {
                foundCpuCores = true;
            }
            if (mt.getStorageGb() == 10) {
                foundStorageGb = true;
            }
            if (mt.getProvider() == Constants.Provider.AWS) {
                foundProvider = true;
            }
        }

        assertTrue(foundCpuCores);
        assertTrue(foundStorageGb);
        assertTrue(foundProvider);

        MachineType expected = mts.get(0);
        MachineType actual = service.getMachineType(expected.getId());
        assertEquals(expected.getId(), actual.getId());
    }

    private void testBillingGroup() {
        System.out.println("Testing billing group.");

        // Get all.
        List<BillingGroup> groups = service.getAllBillingGroups();
        String customerId = groups.get(0).getCustomerId();

        // Create.
        String name = PREFIX + " group";
        Map<String, Object> attrs = new BillingGroupRequestBuilder()
                .name(name).customerId(customerId).build();
        BillingGroup group = service.createBillingGroup(attrs);
        String id = group.getId();

        // Get by id.
        group = service.getBillingGroup(id);
        assertEquals(id, group.getId());
        assertEquals(name, group.getName());

        // Update.
        String description = "Engineering";
        attrs = new BillingGroupRequestBuilder().description(description).build();
        group = service.updateBillingGroup(id, attrs);
        assertEquals(description, group.getDescription());

        // Delete.
        service.deleteBillingGroup(id);
        try {
            service.getBillingGroup(id);
            fail("Exception not thrown when getting a deleted billing group");
        } catch (BrktService.RuntimeHttpError e) {
            assertEquals(404, e.status);
        }
    }

    private void testNetwork() {
        System.out.println("Testing network.");

        Network network = service.getAllNetworks().get(0);
        ComputingCell cc = service.getComputingCell(network.getComputingCellId());
        assertNotNull(cc);
        assertEquals(RequestedState.AVAILABLE, network.getRequestedState());
        String id = network.getId();
        assertEquals(id, service.getNetwork(id).getId());

        List<Zone> zones = service.getNetworkZones(network.getId());
        assertFalse(zones.isEmpty());
    }

    private void testZone() {
        System.out.println("Testing zone.");

        Zone zone = service.getAllZones().get(0);
        assertNotNull(service.getNetwork(zone.getNetworkId()));
    }

    private void testSecurityGroupRule(String groupId) {
        // Create.
        Map<String, Object> attrs = new SecurityGroupRuleRequestBuilder()
                .srcSecurityGroupId(groupId)
                .ipProto("tcp")
                .portRangeFrom(2000)
                .portRangeTo(2100).build();
        SecurityGroupRule rule = service.createSecurityGroupRule(groupId, attrs);
        String id = rule.getId();

        // Get.
        assertEquals(id, service.getSecurityGroupRule(id).getId());
        boolean found = false;
        List<SecurityGroupRule> rules = service.getRulesForSecurityGroup(groupId);
        assertEquals(1, rules.size());
        assertEquals(id, rules.get(0).getId());

        // Update.
        String description = "Describe this rule";
        attrs = new SecurityGroupRuleRequestBuilder().description(description).build();
        rule = service.updateSecurityGroupRule(rule.getId(), attrs);
        assertEquals(description, rule.getDescription());

        // Delete.
        service.deleteSecurityGroupRule(id);
        try {
            service.getSecurityGroupRule(id);
            fail("RuntimeHttpError was not thrown");
        } catch (BrktService.RuntimeHttpError e) {
            assertEquals(404, e.status);
        }
    }

    private void testSecurityGroup() {
        System.out.println("Testing security group.");

        Network network = service.getAllNetworks().get(0);

        // Create.
        String name = PREFIX + " security group";
        Map<String, Object> attrs = new SecurityGroupRequestBuilder().name(name).build();
        SecurityGroup group = service.createSecurityGroup(network.getId(), attrs);
        assertEquals(RequestedState.AVAILABLE, group.getRequestedState());
        String id = group.getId();

        // Get.
        assertEquals(id, service.getSecurityGroup(id).getId());
        boolean found = false;
        for (SecurityGroup sg : service.getAllSecurityGroups()) {
            if (sg.getId().equals(id)) {
                found = true;
            }
        }
        assertTrue(found);

        // Update.
        String description = "Describe this security group";
        attrs = new SecurityGroupRequestBuilder().description(description).build();
        group = service.updateSecurityGroup(id, attrs);
        assertEquals(description, group.getDescription());

        testSecurityGroupRule(id);

        // Delete.
        group = service.deleteSecurityGroup(id);
        assertEquals(RequestedState.DELETED, group.getRequestedState());
    }

    private void testComputingCell() {
        System.out.println("Testing computing cell.");
        ComputingCell cc = service.getAllComputingCells().get(0);
        assertEquals(RequestedState.AVAILABLE, cc.getRequestedState());
        String id = cc.getId();
        assertEquals(id, service.getComputingCell(id).getId());

        // Just make sure that the endpoint is valid.
        service.getComputingCellVolumes(id);
    }

    private void testVolume(ComputingCell cell, BillingGroup group) {
        System.out.println("Testing volume.");

        // Create.
        String name = PREFIX + " volume";
        Map<String, Object> attrs = new VolumeRequestBuilder()
                .computingCellId(cell.getId())
                .billingGroupId(group.getId())
                .iops(100)
                .sizeInGb(10)
                .name(name).build();
        Volume volume = service.createVolume(attrs);
        assertEquals(cell.getId(), volume.getComputingCellId());
        assertEquals(group.getId(), volume.getBillingGroupId());
        assertEquals(100, volume.getIops().intValue());
        assertEquals(10, volume.getSizeInGb().intValue());
        assertEquals(name, volume.getName());
        assertEquals(RequestedState.AVAILABLE, volume.getRequestedState());

        // Get.
        String id = volume.getId();
        assertEquals(id, service.getVolume(id).getId());
        boolean found = false;
        for (Volume v : service.getAllVolumes()) {
            if (v.getId().equals(id)) {
                found = true;
            }
        }
        assertTrue(found);

        // Update.
        String description = "Describe this volume";
        attrs = new VolumeRequestBuilder().description(description).build();
        volume = service.updateVolume(id, attrs);
        assertEquals(description, volume.getDescription());

        // Delete.
        volume = service.deleteVolume(id);
        assertEquals(RequestedState.DELETED, volume.getRequestedState());
    }

    private void testInstance(Workload workload) {
        System.out.println("Testing instance.");

        // Find image definition.
        ImageDefinition imageDef = null;
        for (ImageDefinition id : service.getAllImageDefinitions()) {
            if (id.getName().startsWith("Bracket Ubuntu 14.04")) {
                imageDef = id;
            }
        }
        assertNotNull(imageDef);

        // Find machine type.
        MachineType machineType = null;
        for (MachineType mt : service.getAllMachineTypes()) {
            if (mt.getCpuCores() == 1) {
                machineType = mt;
                break;
            }
        }
        assertNotNull(machineType);

        // Create.
        String name = PREFIX + " instance";
        Map<String, Object> attrs = new InstanceRequestBuilder()
                .workloadId(workload.getId())
                .imageDefinitionId(imageDef.getId())
                .machineTypeId(machineType.getId())
                .name(name).build();
        Instance instance = service.createInstance(attrs);
        assertEquals(workload.getId(), instance.getWorkloadId());
        assertEquals(imageDef.getId(), instance.getImageDefinitionId());
        assertEquals(machineType.getId(), instance.getMachineTypeId());
        assertEquals(name, instance.getName());

        // Get.
        String id = instance.getId();
        assertEquals(id, service.getInstance(id).getId());
        boolean found = false;
        for (Instance i : service.getAllInstances()) {
            if (i.getId().equals(id)) {
                found = true;
            }
        }
        assertTrue(found);

        // Update.
        String description = "Describe this instance";
        attrs = new InstanceRequestBuilder().description(description).build();
        instance = service.updateInstance(id, attrs);
        assertEquals(description, instance.getDescription());

        // Delete.
        instance = service.deleteInstance(id);
        assertEquals(RequestedState.DELETED, instance.getRequestedState());
    }

    private void testWorkload(Zone zone, BillingGroup group) {
        System.out.println("Testing workload.");
        String name = PREFIX + " workload";

        // Create.
        Map<String, Object> attrs = new WorkloadRequestBuilder()
                .billingGroupId(group.getId())
                .zoneId(zone.getId())
                .name(name).build();
        Workload workload = service.createWorkload(attrs);
        assertEquals(group.getId(), workload.getBillingGroupId());
        assertEquals(zone.getId(), workload.getZoneId());
        assertEquals(name, workload.getName());

        // Get.
        String id = workload.getId();
        assertEquals(id, service.getWorkload(id).getId());
        boolean found = false;
        for (Workload w : service.getAllWorkloads()) {
            if (w.getId().equals(id)) {
                found = true;
            }
        }
        assertTrue(found);

        // Update.
        String description = "Describe this workload";
        attrs = new WorkloadRequestBuilder().description(description).build();
        workload = service.updateWorkload(id, attrs);
        assertEquals(description, workload.getDescription());

        testInstance(workload);

        // Delete.
        workload = service.deleteWorkload(id);
        assertEquals(RequestedState.DELETED, workload.getRequestedState());
    }

    public static void main(String[] stringArgs) {
        Arguments args = null;
        try {
            args = parseArgs(stringArgs);
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        BrktRestClient client = new BrktRestClient.Builder(args.rootUri)
                .accessToken(args.token).macKey(args.macKey).build();
        BrktService service = new BrktService(client);
        IntegrationTest test = new IntegrationTest(service);

        // Verify required resources.
        ComputingCell cell = null;
        List<ComputingCell> cells = service.getAllComputingCells();
        if (cells.isEmpty()) {
            System.err.println("No computing cells found.");
            System.exit(1);
        }
        cell = cells.get(0);

        test.cleanUp();

        test.testOperatingSystem();
        test.testImageDefinition();
        test.testCspImage();
        test.testMachineType();
        test.testBillingGroup();
        test.testNetwork();
        test.testZone();
        test.testSecurityGroup();
        test.testComputingCell();

        // Create a billing group for new volumes and workloads.
        String name = PREFIX + " group for workloads";
        Map<String, Object> attrs = new BillingGroupRequestBuilder()
                .name(name)
                .customerId(cell.getCustomerId()).build();
        BillingGroup group = service.createBillingGroup(attrs);

        test.testVolume(cell, group);
        Zone zone = service.getAllZones().get(0);
        test.testWorkload(zone, group);

        test.cleanUp();

        System.out.println("Success!");
    }
}
