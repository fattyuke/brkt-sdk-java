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
import com.brkt.client.util.BrktRestClient;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IntegrationTest {

    private static final String PREFIX = "Integration Test";

    private final BrktService service;
    private ComputingCell computingCell;

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
            jc.setProgramName("integration-test");
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
        for (BillingGroup group : service.getAllBillingGroups()) {
            if (group.getName().startsWith(PREFIX)) {
                service.deleteBillingGroup(group.getId());
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

    private void testComputingCell() {
        System.out.println("Testing computing cell.");
        List<ComputingCell> computingCells = service.getAllComputingCells();
        assertFalse(computingCells.isEmpty());
        computingCell = computingCells.get(0);
    }

    public static void main(String[] stringArgs) {
        Arguments args = parseArgs(stringArgs);
        BrktRestClient client = new BrktRestClient.Builder(args.rootUri)
                .accessToken(args.token).macKey(args.macKey).build();
        BrktService service = new BrktService(client);
        IntegrationTest test = new IntegrationTest(service);

        test.cleanUp();

        test.testOperatingSystem();
        test.testImageDefinition();
        test.testCspImage();
        test.testMachineType();
        test.testComputingCell();
        test.testBillingGroup();

        test.cleanUp();

        System.out.println("Success!");
    }
}
