package com.brkt.client.util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.brkt.client.BillingGroup;
import com.brkt.client.BrktService;
import com.brkt.client.ComputingCell;
import com.brkt.client.CspImage;
import com.brkt.client.ImageDefinition;
import com.brkt.client.Instance;
import com.brkt.client.LoadBalancer;
import com.brkt.client.LoadBalancerListener;
import com.brkt.client.MachineType;
import com.brkt.client.Network;
import com.brkt.client.OperatingSystem;
import com.brkt.client.SecurityGroup;
import com.brkt.client.SecurityGroupRule;
import com.brkt.client.Volume;
import com.brkt.client.Workload;
import com.brkt.client.Zone;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private Arguments args;

    static class Arguments {
        // Without this, JCommand will throw an unintuitive exception if the user specifies an
        // unexpected argument.
        @Parameter
        List<String> parameters = Lists.newArrayList();

        @Parameter(names = { "--token" }, required = true, description = "Auth token")
        String token;

        @Parameter(names = { "--key" }, required = true, description = "MAC key")
        String macKey;

        @Parameter(names = { "--root-uri"}, required = true, description = "Server root URI, e.g. http://example.com")
        String rootUri;

        @Parameter(names = { "--all-fields" }, description = "Print all fields on the returned objects.")
        boolean allFields;

        @Parameter(names = { "-h", "--help"}, help = true, description = "Show usage")
        boolean help;
    }

    private static void assertMinArgCount(List<String> args, int min) {
        if (args.size() < min) {
            System.err.print("This command requires at least " + min + " argument");
            if (min > 1) {
                System.err.print("s");
            }
            System.err.println(".");
            System.exit(1);
        }
    }

    /**
     * Convert attributes in {@code key=value} format to a {@code Map} of key to value.
     */
    static Map<String, Object> splitAttrs(List<String> attrStrings, int beginIndex) {
        Map<String, Object> params = Maps.newHashMap();
        for (int i = beginIndex; i < attrStrings.size(); i++) {
            String keyValue = attrStrings.get(i);
            Matcher m = PAT_FIELD_VALUE.matcher(keyValue);
            if (!m.matches()) {
                throw new IllegalArgumentException("'" + keyValue + "' is not in the format \"field=value\".");
            }
            params.put(m.group(1), m.group(2));
        }
        return params;
    }

    private static final Converter<String, String> caseConverter =
            CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);

    private static String getIdArg(List<String> args) {
        assertMinArgCount(args, 1);
        return args.get(0);
    }

    private static Map<String, Object> getAttrArgs(List<String> args, int start) {
        assertMinArgCount(args, start);
        return convertKeysToUnderscore(splitAttrs(args, start));
    }

    /**
     * Convert keys in {@code attrs} from {@code camelCase} format to {@code underscore_format}.
     * Return a new {@code Map} that uses the converted keys.
     */
    static Map<String, Object> convertKeysToUnderscore(Map<String, Object> attrs) {
        Map<String, Object> converted = Maps.newHashMap();
        for (Map.Entry<String, Object> entry : attrs.entrySet()) {
            String newKey = caseConverter.convert(entry.getKey());
            converted.put(newKey, entry.getValue());
        }
        return converted;
    }

    @Parameters() static class CommandWithNoArgs {}

    @Parameters()
    static class CommandWithId {
        @Parameter(description = "<id>")
        List<String> args = Lists.newArrayList();

        String getId() {
            return getIdArg(args);
        }
    }

    @Parameters()
    static class CommandWithAttrs {
        @Parameter(description = "<field1=value> [field2=value ...]")
        List<String> args = Lists.newArrayList();

        Map<String, Object> getAttrs() {
            return getAttrArgs(args, 0);
        }
    }

    @Parameters()
    static class CommandWithIdAndAttrs {
        @Parameter(description = "<id> <field1=value> [field2=value ...]")
        List<String> args = Lists.newArrayList();

        String getId() {
            return getIdArg(args);
        }

        Map<String, Object> getAttrs() {
            return getAttrArgs(args, 1);
        }
    }

    // Commands with custom descriptions or behavior.
    @Parameters()
    static class CommandCreateSecurityGroup {
        @Parameter(description = "<networkId> <field1=value> [field2=value ...]")
        List<String> args = Lists.newArrayList();
    }

    @Parameters()
    static class CommandCreateSecurityGroupRule {
        @Parameter(description = "<securityGroupId> <field1=value> [field2=value ...]")
        List<String> args = Lists.newArrayList();
    }

    @Parameters()
    static class CommandGetRulesForSecurityGroup {
        @Parameter(description = "<securityGroupId>")
        List<String> args = Lists.newArrayList();
    }

    private static final Pattern PAT_FIELD_VALUE = Pattern.compile("([^=]+)=(.*)");

    private static final Comparator<Field> FIELD_SORTER = new Comparator<Field>() {
        @Override
        public int compare(Field o1, Field o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    };

    private void printObject(Object obj) {
        if (!this.args.allFields) {
            System.out.println(obj);
        } else {
            MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(obj);

            List<Field> fields = new ArrayList<Field>();

            Class<?> i = obj.getClass();
            while (i != null && i != Object.class) {
                for (Field f : i.getDeclaredFields()) {
                    if (!Modifier.isStatic(f.getModifiers())) {
                        f.setAccessible(true);
                        fields.add(f);
                    }
                }
                i = i.getSuperclass();
            }

            Collections.sort(fields, FIELD_SORTER);
            for (Field f : fields) {
                try {
                    helper.add(f.getName(), f.get(obj));
                } catch (IllegalAccessException e) {
                    System.err.println(e);
                }
            }
            System.out.println(helper);
        }
    }

    private void run(String[] stringArgs) {
        Arguments args = new Arguments();
        JCommander jc = null;

        // General-purpose commands.
        CommandWithNoArgs noArgs = new CommandWithNoArgs();
        CommandWithId idArg = new CommandWithId();
        CommandWithAttrs attrsArg = new CommandWithAttrs();
        CommandWithIdAndAttrs idAndAttrsArg = new CommandWithIdAndAttrs();

        // Specialized commands.
        CommandCreateSecurityGroup cmdCsg = new CommandCreateSecurityGroup();
        CommandGetRulesForSecurityGroup cmdGrfsg = new CommandGetRulesForSecurityGroup();
        CommandCreateSecurityGroupRule cmdCsgr = new CommandCreateSecurityGroupRule();

        try {
            jc = new JCommander(args);

            jc.addCommand("getAllOperatingSystems", noArgs, "gaos");
            jc.addCommand("getOperatingSystem", idArg, "gos");
            jc.addCommand("getOperatingSystemImageDefinitions", idArg, "gosid");

            jc.addCommand("getAllImageDefinitions", noArgs, "gaid");
            jc.addCommand("getImageDefinition", idArg, "gid");
            jc.addCommand("getImageDefinitionCspImages", idArg, "gidci");

            jc.addCommand("getAllCspImages", noArgs, "gaci");
            jc.addCommand("getCspImage", idArg, "gci");

            jc.addCommand("getAllMachineTypes", noArgs, "gamt");
            jc.addCommand("getMachineType", idArg, "gmt");

            jc.addCommand("getAllBillingGroups", noArgs, "gabg");
            jc.addCommand("getBillingGroup", idArg, "gbg");
            jc.addCommand("createBillingGroup", attrsArg, "cbg");
            jc.addCommand("updateBillingGroup", idAndAttrsArg, "ubg");
            jc.addCommand("deleteBillingGroup", idArg, "dbg");

            jc.addCommand("getAllNetworks", noArgs, "gan");
            jc.addCommand("getNetwork", idArg, "gn");

            jc.addCommand("getAllZones", noArgs, "gaz");
            jc.addCommand("getNetworkZones", idArg, "gnz");
            jc.addCommand("getZone", idArg, "gz");

            jc.addCommand("getAllSecurityGroups", noArgs, "gasg");
            jc.addCommand("getSecurityGroup", idArg, "gsg");
            jc.addCommand("createSecurityGroup", cmdCsg, "csg");
            jc.addCommand("updateSecurityGroup", idAndAttrsArg, "usg");
            jc.addCommand("deleteSecurityGroup", idArg, "dsg");

            jc.addCommand("getRulesForSecurityGroup", cmdGrfsg, "grfsg");
            jc.addCommand("getSecurityGroupRule", idArg, "gsgr");
            jc.addCommand("createSecurityGroupRule", cmdCsgr, "csgr");
            jc.addCommand("updateSecurityGroupRule", idAndAttrsArg, "usgr");
            jc.addCommand("deleteSecurityGroupRule", idArg, "dsgr");

            jc.addCommand("getAllComputingCells", noArgs, "gacc");
            jc.addCommand("getComputingCell", idArg, "gcc");
            jc.addCommand("getComputingCellVolumes", idArg, "gccv");
            jc.addCommand("updateComputingCell", idAndAttrsArg, "ucc");

            jc.addCommand("getAllVolumes", noArgs, "gav");
            jc.addCommand("getVolume", idArg, "gv");
            jc.addCommand("createVolume", attrsArg, "cv");
            jc.addCommand("updateVolume", idAndAttrsArg, "uv");
            jc.addCommand("deleteVolume", idArg, "dv");
            jc.addCommand("getVolumeChildren", idArg, "gvc");
            jc.addCommand("snapshotVolume", idAndAttrsArg, "sv");
            jc.addCommand("cloneVolume", idAndAttrsArg);

            jc.addCommand("getAllInstances", noArgs, "gai");
            jc.addCommand("getInstance", idArg, "gi");
            jc.addCommand("updateInstance", idAndAttrsArg, "ui");
            jc.addCommand("getInstanceVolumes", idArg, "giv");
            jc.addCommand("createInstance", attrsArg, "ci");
            jc.addCommand("deleteInstance", idArg, "di");
            jc.addCommand("rebootInstance", idArg, "ri");

            jc.addCommand("getAllWorkloads", noArgs, "gaw");
            jc.addCommand("getWorkload", idArg, "gw");
            jc.addCommand("createWorkload", attrsArg, "cw");
            jc.addCommand("updateWorkload", idAndAttrsArg, "uw");
            jc.addCommand("getWorkloadInstances", idArg, "gwi");
            jc.addCommand("deleteWorkload", idArg, "dw");

            jc.addCommand("getAllLoadBalancers", noArgs, "galb");
            jc.addCommand("getLoadBalancer", idArg, "glb");
            jc.addCommand("createLoadBalancer", attrsArg, "clb");
            jc.addCommand("updateLoadBalancer", idAndAttrsArg, "ulb");
            jc.addCommand("deleteLoadBalancer", idArg, "dlb");

            jc.addCommand("getAllLoadBalancerListeners", noArgs, "galbl");
            jc.addCommand("getLoadBalancerListener", idArg, "glbl");
            jc.addCommand("createLoadBalancerListener", attrsArg, "clbl");
            jc.addCommand("updateLoadBalancerListener", idAndAttrsArg, "ulbl");
            jc.addCommand("deleteLoadBalancerListener", idArg, "dlbl");

            jc.parse(stringArgs);
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        if (args.help) {
            jc.setProgramName("brkt-client");
            jc.usage();
            System.exit(0);
        }
        this.args = args;

        String command = jc.getParsedCommand();
        if (command == null) {
            System.err.println("Command not specified.  See usage (-h) for details.");
            System.exit(1);
        }

        BrktRestClient client = new BrktRestClient.Builder(args.rootUri)
                .macKey(args.macKey).accessToken(args.token).build();
        BrktService service = new BrktService(client);

        // Operating system.
        if (command.equals("getAllOperatingSystems")) {
            for (OperatingSystem os : service.getAllOperatingSystems()) {
                printObject(os);
            }
        }
        if (command.equals("getOperatingSystem")) {
            String id = idArg.getId();
            printObject(service.getOperatingSystem(id));
        }
        if (command.equals("getOperatingSystemImageDefinitions")) {
            String id = idArg.getId();
            for (ImageDefinition imageDef : service.getOperatingSystemImageDefinitions(id)) {
                printObject(imageDef);
            }
        }

        // Image definition.
        if (command.equals("getAllImageDefinitions")) {
            for (ImageDefinition id : service.getAllImageDefinitions()) {
                printObject(id);
            }
        }
        if (command.equals("getImageDefinition")) {
            String id = idArg.getId();
            printObject(service.getImageDefinition(id));
        }
        if (command.equals("getImageDefinitionCspImages")) {
            String id = idArg.getId();
            for (CspImage ci : service.getImageDefinitionCspImages(id)) {
                printObject(ci);
            }
        }

        // CSP image.
        if (command.equals("getAllCspImages")) {
            for (CspImage ci : service.getAllCspImages()) {
                printObject(ci);
            }
        }
        if (command.equals("getCspImage")) {
            String id = idArg.getId();
            printObject(service.getCspImage(id));
        }

        // Machine type.
        if (command.equals("getAllMachineTypes")) {
            for (MachineType mt : service.getAllMachineTypes()) {
                printObject(mt);
            }
        }
        if (command.equals("getMachineType")) {
            String id = idArg.getId();
            printObject(service.getMachineType(id));
        }

        // Billing group.
        if (command.equals("getAllBillingGroups")) {
            for (BillingGroup bg : service.getAllBillingGroups()) {
                printObject(bg);
            }
        }
        if (command.equals("getBillingGroup")) {
            String id = idArg.getId();
            printObject(service.getBillingGroup(id));
        }
        if (command.equals("createBillingGroup")) {
            Map<String, Object> attrs = attrsArg.getAttrs();
            printObject(service.createBillingGroup(attrs));
        }
        if (command.equals("updateBillingGroup")) {
            String id = idAndAttrsArg.getId();
            Map<String, Object> attrs = idAndAttrsArg.getAttrs();
            printObject(service.updateBillingGroup(id, attrs));
        }
        if (command.equals("deleteBillingGroup")) {
            String id = idArg.getId();
            service.deleteBillingGroup(id);
        }

        // Network.
        if (command.equals("getAllNetworks")) {
            for (Network n : service.getAllNetworks()) {
                printObject(n);
            }
        }
        if (command.equals("getNetwork")) {
            String id = idArg.getId();
            printObject(service.getNetwork(id));
        }

        // Zone.
        if (command.equals("getAllZones")) {
            for (Zone z : service.getAllZones()) {
                printObject(z);
            }
        }
        if (command.equals("getNetworkZones")) {
            String id = idArg.getId();
            for (Zone z : service.getNetworkZones(id)) {
                printObject(z);
            }
        }
        if (command.equals("getZone")) {
            String id = idArg.getId();
            printObject(service.getZone(id));
        }

        // Security group.
        if (command.equals("getAllSecurityGroups")) {
            for (SecurityGroup sg : service.getAllSecurityGroups()) {
                printObject(sg);
            }
        }
        if (command.equals("getSecurityGroup")) {
            String id = idArg.getId();
            printObject(service.getSecurityGroup(id));
        }
        if (command.equals("createSecurityGroup")) {
            String networkId = getIdArg(cmdCsg.args);
            Map<String, Object> attrs = getAttrArgs(cmdCsg.args, 1);
            printObject(service.createSecurityGroup(networkId, attrs));
        }
        if (command.equals("updateSecurityGroup")) {
            String id = idAndAttrsArg.getId();
            Map<String, Object> attrs = idAndAttrsArg.getAttrs();
            printObject(service.updateSecurityGroup(id, attrs));
        }
        if (command.equals("deleteSecurityGroup")) {
            String id = idArg.getId();
            printObject(service.deleteSecurityGroup(id));
        }

        // Security group rules.
        if (command.equals("getRulesForSecurityGroup")) {
            String id = getIdArg(cmdGrfsg.args);
            for (SecurityGroupRule r : service.getRulesForSecurityGroup(id)) {
                printObject(r);
            }
        }
        if (command.equals("getSecurityGroupRule")) {
            String id = idArg.getId();
            printObject(service.getSecurityGroupRule(id));
        }
        if (command.equals("createSecurityGroupRule")) {
            String id = getIdArg(cmdCsgr.args);
            Map<String, Object> attrs = getAttrArgs(cmdCsgr.args, 1);
            printObject(service.createSecurityGroupRule(id, attrs));
        }
        if (command.equals("updateSecurityGroupRule")) {
            String id = idAndAttrsArg.getId();
            Map<String, Object> attrs = idAndAttrsArg.getAttrs();
            printObject(service.updateSecurityGroupRule(id, attrs));
        }
        if (command.equals("deleteSecurityGroupRule")) {
            String id = idArg.getId();
            printObject(service.deleteSecurityGroupRule(id));
        }

        // Computing cell.
        if (command.equals("getAllComputingCells")) {
            for (ComputingCell cc : service.getAllComputingCells()) {
                printObject(cc);
            }
        }
        if (command.equals("getComputingCell")) {
            String id = idArg.getId();
            printObject(service.getComputingCell(id));
        }
        if (command.equals("getComputingCellVolumes")) {
            String id = idArg.getId();
            for (Volume v : service.getComputingCellVolumes(id)) {
                printObject(v);
            }
        }
        if (command.equals("updateComputingCell")) {
            String id = idAndAttrsArg.getId();
            Map<String, Object> attrs = idAndAttrsArg.getAttrs();
            printObject(service.updateComputingCell(id, attrs));
        }

        // Volume.
        if (command.equals("getAllVolumes")) {
            for (Volume v : service.getAllVolumes()) {
                printObject(v);
            }
        }
        if (command.equals("getVolume")) {
            String id = idArg.getId();
            printObject(service.getVolume(id));
        }
        if (command.equals("createVolume")) {
            Map<String, Object> attrs = attrsArg.getAttrs();
            printObject(service.createVolume(attrs));
        }
        if (command.equals("updateVolume")) {
            String id = idAndAttrsArg.getId();
            Map<String, Object> attrs = idAndAttrsArg.getAttrs();
            printObject(service.updateVolume(id, attrs));
        }
        if (command.equals("deleteVolume")) {
            String id = idArg.getId();
            printObject(service.deleteVolume(id));
        }
        if (command.equals("getVolumeChildren")) {
            String id = idArg.getId();
            for (Volume v : service.getVolumeChildren(id)) {
                printObject(v);
            }
        }
        if (command.equals("snapshotVolume")) {
            String id = idAndAttrsArg.getId();
            Map<String, Object> attrs = idAndAttrsArg.getAttrs();
            printObject(service.snapshotVolume(id, attrs));
        }
        if (command.equals("cloneVolume")) {
            String id = idAndAttrsArg.getId();
            Map<String, Object> attrs = idAndAttrsArg.getAttrs();
            printObject(service.cloneVolume(id, attrs));
        }

        // Instance.
        if (command.equals("getAllInstances")) {
            for (Instance i : service.getAllInstances()) {
                printObject(i);
            }
        }
        if (command.equals("getInstance")) {
            String id = idArg.getId();
            printObject(service.getInstance(id));
        }
        if (command.equals("updateInstance")) {
            String id = idAndAttrsArg.getId();
            Map<String, Object> attrs = idAndAttrsArg.getAttrs();
            printObject(service.updateInstance(id, attrs));
        }
        if (command.equals("getInstanceVolumes")) {
            String id = idArg.getId();
            for (Volume v : service.getInstanceVolumes(id)) {
                printObject(v);
            }
        }
        if (command.equals("createInstance")) {
            Map<String, Object> attrs = attrsArg.getAttrs();
            printObject(service.createInstance(attrs));
        }
        if (command.equals("deleteInstance")) {
            String id = idArg.getId();
            printObject(service.deleteInstance(id));
        }
        if (command.equals("rebootInstance")) {
            String id = idArg.getId();
            printObject(service.rebootInstance(id));
        }

        // Workload.
        if (command.equals("getAllWorkloads")) {
            for (Workload w : service.getAllWorkloads()) {
                printObject(w);
            }
        }
        if (command.equals("getWorkload")) {
            String id = idArg.getId();
            printObject(service.getWorkload(id));
        }
        if (command.equals("createWorkload")) {
            Map<String, Object> attrs = attrsArg.getAttrs();
            printObject(service.createWorkload(attrs));
        }
        if (command.equals("updateWorkload")) {
            String id = idAndAttrsArg.getId();
            Map<String, Object> attrs = idAndAttrsArg.getAttrs();
            printObject(service.updateWorkload(id, attrs));
        }
        if (command.equals("getWorkloadInstances")) {
            String id = idArg.getId();
            for (Instance i : service.getWorkloadInstances(id)) {
                printObject(i);
            }
        }
        if (command.equals("deleteWorkload")) {
            String id = idArg.getId();
            printObject(service.deleteWorkload(id));
        }

        // Load balancer.
        if (command.equals("getAllLoadBalancers")) {
            for (LoadBalancer lb : service.getAllLoadBalancers()) {
                printObject(lb);
            }
        }
        if (command.equals("getLoadBalancer")) {
            String id = idArg.getId();
            printObject(service.getLoadBalancer(id));
        }
        if (command.equals("createLoadBalancer")) {
            Map<String, Object> attrs = attrsArg.getAttrs();
            printObject(service.createLoadBalancer(attrs));
        }
        if (command.equals("updateLoadBalancer")) {
            String id = idAndAttrsArg.getId();
            Map<String, Object> attrs = idAndAttrsArg.getAttrs();
            printObject(service.updateLoadBalancer(id, attrs));
        }
        if (command.equals("deleteLoadBalancer")) {
            String id = idArg.getId();
            printObject(service.deleteLoadBalancer(id));
        }

        // Load balancer listener.
        if (command.equals("getAllLoadBalancerListeners")) {
            for (LoadBalancerListener lbl : service.getAllLoadBalancerListeners()) {
                printObject(lbl);
            }
        }
        if (command.equals("getLoadBalancerListener")) {
            String id = idArg.getId();
            printObject(service.getLoadBalancerListener(id));
        }
        if (command.equals("createLoadBalancerListener")) {
            Map<String, Object> attrs = attrsArg.getAttrs();
            printObject(service.createLoadBalancerListener(attrs));
        }
        if (command.equals("updateLoadBalancerListener")) {
            String id = idAndAttrsArg.getId();
            Map<String, Object> attrs = idAndAttrsArg.getAttrs();
            printObject(service.updateLoadBalancerListener(id, attrs));
        }
        if (command.equals("deleteLoadBalancerListener")) {
            String id = idArg.getId();
            printObject(service.deleteLoadBalancerListener(id));
        }
    }

    public static void main(String[] args) {
        try {
            new Main().run(args);
        } catch (BrktService.RuntimeHttpError e) {
            System.err.println(String.format("%d %s", e.status, e.message));
            if (e.status != 404 && e.payload.length > 0) {
                System.err.println(new String(e.payload));
            }
            System.exit(1);
        }
    }
}
