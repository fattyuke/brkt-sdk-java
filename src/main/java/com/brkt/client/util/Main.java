package com.brkt.client.util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.brkt.client.BrktService;
import com.brkt.client.CspImage;
import com.brkt.client.ImageDefinition;
import com.brkt.client.MachineType;
import com.brkt.client.OperatingSystem;
import com.brkt.client.Volume;
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

        @Parameter(names = { "--all-fields" }, description = "Print all fields")
        boolean allFields;

        @Parameter(names = { "-h", "--help"}, help = true, description = "Show usage")
        boolean help;
    }

    @Parameters() static class CommandGetAllOperatingSystems {}
    @Parameters() static class CommandGetAllImageDefinitions {}
    @Parameters() static class CommandGetAllCspImages {}
    @Parameters() static class CommandGetAllMachineTypes {}
    @Parameters() static class CommandGetAllVolumes {}

    @Parameters()
    static class CommandGetVolume {
        @Parameter(description = "<volumeId>")
        List<String> args = Lists.newArrayList();
    }

    @Parameters()
    static class CommandCreateVolume {
        @Parameter(description = "<field1=value> [field2=value ...]")
        List<String> args = Lists.newArrayList();
    }

    @Parameters()
    static class CommandUpdateVolume {
        @Parameter(description = "<volumeId> <field1=value> [field2=value ...]")
        List<String> args = Lists.newArrayList();
    }

    @Parameters()
    static class CommandDeleteVolume {
        @Parameter(description = "<volumeId>")
        List<String> args = Lists.newArrayList();
    }

    private static final Pattern PAT_FIELD_VALUE = Pattern.compile("([^=]+)=(.*)");

    static Map<String, Object> splitParams(List<String> paramStrings, int beginIndex) {
        Map<String, Object> params = Maps.newHashMap();
        for (int i = beginIndex; i < paramStrings.size(); i++) {
            String keyValue = paramStrings.get(i);
            Matcher m = PAT_FIELD_VALUE.matcher(keyValue);
            if (!m.matches()) {
                throw new IllegalArgumentException("'" + keyValue + "' is not in the format \"field=value\".");
            }
            params.put(m.group(1), m.group(2));
        }
        return params;
    }

    private static void assertMinArgs(List<String> args, int min) {
        if (args.size() < min) {
            System.err.println("This command requires " + min + " arguments.");
            System.exit(1);
        }
    }

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

        CommandGetVolume getVolume = new CommandGetVolume();
        CommandCreateVolume createVolume = new CommandCreateVolume();
        CommandUpdateVolume updateVolume = new CommandUpdateVolume();
        CommandDeleteVolume deleteVolume = new CommandDeleteVolume();

        try {
            jc = new JCommander(args);
            jc.addCommand("getAllOperatingSystems", new CommandGetAllOperatingSystems(), "gaos");
            jc.addCommand("getAllImageDefinitions", new CommandGetAllImageDefinitions(), "gaid");
            jc.addCommand("getAllCspImages", new CommandGetAllCspImages(), "gaci");
            jc.addCommand("getAllMachineTypes", new CommandGetAllMachineTypes(), "gamt");
            jc.addCommand("getAllVolumes", new CommandGetAllVolumes(), "gav");
            jc.addCommand("getVolume", getVolume, "gv");
            jc.addCommand("createVolume", createVolume, "cv");
            jc.addCommand("updateVolume", updateVolume, "uv");
            jc.addCommand("deleteVolume", deleteVolume, "dv");
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

        if (command.equals("getAllOperatingSystems")) {
            for (OperatingSystem os : service.getOperatingSystems()) {
                printObject(os);
            }
        }
        if (command.equals("getAllImageDefinitions")) {
            for (ImageDefinition id : service.getImageDefinitions()) {
                printObject(id);
            }
        }
        if (command.equals("getAllCspImages")) {
            for (CspImage ci : service.getCspImages()) {
                printObject(ci);
            }
        }
        if (command.equals("getAllMachineTypes")) {
            for (MachineType mt : service.getMachineTypes()) {
                printObject(mt);
            }
        }

        // Volume.
        if (command.equals("getAllVolumes")) {
            for (Volume v : service.getAllVolumes()) {
                printObject(v);
            }
        }
        if (command.equals("getVolume")) {
            assertMinArgs(getVolume.args, 1);
            String id = getVolume.args.get(0);
            printObject(service.getVolume(id));
        }
        if (command.equals("createVolume")) {
            assertMinArgs(createVolume.args, 1);
            Map<String, Object> elements = splitParams(createVolume.args, 0);
            printObject(service.createVolume(elements));
        }
        if (command.equals("updateVolume")) {
            assertMinArgs(updateVolume.args, 2);
            String id = updateVolume.args.get(0);
            Map<String, Object> elements = splitParams(updateVolume.args, 1);
            printObject(service.updateVolume(id, elements));
        }
        if (command.equals("deleteVolume")) {
            assertMinArgs(deleteVolume.args, 1);
            String id = deleteVolume.args.get(0);
            printObject(service.deleteVolume(id));
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
