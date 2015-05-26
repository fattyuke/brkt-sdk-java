package com.brkt.client.util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.brkt.client.BrktService;
import com.brkt.client.CspImage;
import com.brkt.client.ImageDefinition;
import com.brkt.client.Instance;
import com.brkt.client.MachineType;
import com.brkt.client.OperatingSystem;
import com.brkt.client.Volume;
import com.brkt.client.Workload;
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

        @Parameter(names = { "--key" }, required = true, description = "Secret key")
        String secretKey;

        @Parameter(names = { "--root-uri"}, required = true, description = "Server root URI, e.g. http://example.com")
        String rootUri;

        @Parameter(names = { "--all-fields" }, description = "Print all fields on the returned objects.")
        boolean allFields;

        @Parameter(names = { "-h", "--help"}, help = true, description = "Show usage")
        boolean help;
    }

    private static void assertMinArgs(List<String> args, int min) {
        if (args.size() < min) {
            System.err.print("This command requires at least " + min + " argument");
            if (min > 1) {
                System.err.print("s");
            }
            System.err.println(".");
            System.exit(1);
        }
    }

    private static void assertArgCount(List<String> args, int count) {
        if (args.size() != count) {
            System.err.print("This command requires " + count + " argument");
            if (count > 1) {
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
            assertArgCount(args, 1);
            return args.get(0);
        }
    }

    @Parameters()
    static class CommandWithAttrs {
        @Parameter(description = "<field1=value> [field2=value ...]")
        List<String> args = Lists.newArrayList();

        Map<String, Object> getAttrs() {
            assertMinArgs(args, 1);
            return convertKeysToUnderscore(splitAttrs(args, 0));
        }
    }

    @Parameters()
    static class CommandWithIdAndAttrs {
        @Parameter(description = "<id> <field1=value> [field2=value ...]")
        List<String> args = Lists.newArrayList();

        String getId() {
            assertMinArgs(args, 2);
            return args.get(0);
        }

        Map<String, Object> getAttrs() {
            assertMinArgs(args, 2);
            return convertKeysToUnderscore(splitAttrs(args, 1));
        }
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

        CommandWithNoArgs noArgs = new CommandWithNoArgs();
        CommandWithId idArg = new CommandWithId();
        CommandWithAttrs attrsArg = new CommandWithAttrs();
        CommandWithIdAndAttrs idAndAttrsArg = new CommandWithIdAndAttrs();

        try {
            jc = new JCommander(args);

            jc.addCommand("getAllOperatingSystems", noArgs, "gaos");
            jc.addCommand("getOperatingSystem", idArg, "gos");

            jc.addCommand("getAllImageDefinitions", noArgs, "gaid");
            jc.addCommand("getImageDefinition", idArg, "gid");

            jc.addCommand("getAllCspImages", noArgs, "gaci");
            jc.addCommand("getCspImage", idArg, "gci");

            jc.addCommand("getAllMachineTypes", noArgs, "gamt");
            jc.addCommand("getMachineType", idArg, "gmt");

            jc.addCommand("getAllVolumes", noArgs, "gav");
            jc.addCommand("getVolume", idArg, "gv");
            jc.addCommand("createVolume", attrsArg, "cv");
            jc.addCommand("updateVolume", idAndAttrsArg, "uv");
            jc.addCommand("deleteVolume", idArg, "dv");

            jc.addCommand("getAllInstances", noArgs, "gai");
            jc.addCommand("getInstance", idArg, "gi");
            jc.addCommand("updateInstance", idAndAttrsArg, "ui");
            jc.addCommand("getInstanceVolumes", idArg, "giv");
            jc.addCommand("createInstance", attrsArg, "ci");

            jc.addCommand("getAllWorkloads", noArgs, "gaw");
            jc.addCommand("getWorkload", idArg, "gw");
            jc.addCommand("createWorkload", attrsArg, "cw");
            jc.addCommand("updateWorkload", idAndAttrsArg, "uw");
            jc.addCommand("getWorkloadInstances", idArg, "gwi");
            jc.addCommand("deleteWorkload", idArg, "dw");

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
                .secretKey(args.secretKey).accessToken(args.token).build();
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
    }

    public static void main(String[] args) {
        try {
            new Main().run(args);
        } catch (BrktService.RuntimeHttpError e) {
            System.err.println(String.format("%d %s", e.status, e.message));
            if (e.payload.length > 0) {
                System.err.println(new String(e.payload));
            }
            System.exit(1);
        }
    }
}
