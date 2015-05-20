package com.brkt.client.util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.brkt.client.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

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

        @Parameter(names = { "-h", "--help"}, help = true, description = "Show usage")
        boolean help;
    }

    @Parameters() static class CommandGetOperatingSystems {}
    @Parameters() static class CommandGetImageDefinitions {}
    @Parameters() static class CommandGetCspImages {}
    @Parameters() static class CommandGetMachineTypes {}
    @Parameters() static class CommandGetVolumes {}

    @Parameters()
    static class CommandUpdateVolume {
        @Parameter(description = "<volumeId> field1=value field2=value...")
        List<String> args = Lists.newArrayList();
    }

    private static final Pattern PAT_FIELD_VALUE = Pattern.compile("([^=]+)=(.*)");

    static Map<String, Object> splitParams(List<String> paramStrings) {
        Map<String, Object> params = Maps.newHashMap();
        for (String keyValue : paramStrings) {
            Matcher m = PAT_FIELD_VALUE.matcher(keyValue);
            if (!m.matches()) {
                throw new IllegalArgumentException("'" + keyValue + "' is not in the format \"field=value\".");
            }
            params.put(m.group(1), m.group(2));
        }
        return params;
    }

    public static void main(String[] stringArgs) {
        Arguments args = new Arguments();
        JCommander jc = null;

        CommandUpdateVolume updateVolume = new CommandUpdateVolume();

        try {
            jc = new JCommander(args);
            jc.addCommand("getOperatingSystems", new CommandGetOperatingSystems(), "gos");
            jc.addCommand("getImageDefinitions", new CommandGetImageDefinitions(), "gid");
            jc.addCommand("getCspImages", new CommandGetCspImages(), "gci");
            jc.addCommand("getMachineTypes", new CommandGetMachineTypes(), "gmt");
            jc.addCommand("getVolumes", new CommandGetVolumes(), "gv");
            jc.addCommand("updateVolume", updateVolume, "uv");
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

        String command = jc.getParsedCommand();
        if (command == null) {
            System.err.println("Command not specified.  See usage (-h) for details.");
            System.exit(1);
        }

        BrktRestClient client = new BrktRestClient.Builder(args.rootUri)
                .secretKey(args.secretKey).accessToken(args.token).build();
        BrktService service = new BrktService(client);

        if (command.equals("getOperatingSystems")) {
            for (OperatingSystem os : service.getOperatingSystems()) {
                System.out.println(os);
            }
        }
        if (command.equals("getImageDefinitions")) {
            for (ImageDefinition id : service.getImageDefinitions()) {
                System.out.println(id);
            }
        }
        if (command.equals("getCspImages")) {
            for (CspImage ci : service.getCspImages()) {
                System.out.println(ci);
            }
        }
        if (command.equals("getMachineTypes")) {
            for (MachineType mt : service.getMachineTypes()) {
                System.out.println(mt);
            }
        }
        if (command.equals("getVolumes")) {
            for (Volume v : service.getVolumes()) {
                System.out.println(v);
            }
        }
        if (command.equals("updateVolume")) {
            if (updateVolume.args.size() < 2) {
                System.err.println("This command requires at least 2 arguments.");
                System.exit(1);
            }
            String id = updateVolume.args.get(0);
            List<String> fieldValues = updateVolume.args.subList(1, updateVolume.args.size());
            Map<String, Object> elements = splitParams(fieldValues);
            System.out.println(service.updateVolume(id, elements));
        }
    }
}
