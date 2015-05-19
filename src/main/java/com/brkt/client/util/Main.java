package com.brkt.client.util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.brkt.client.*;
import com.brkt.client.util.BrktRestClient;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static class Arguments {
        // Without this, JCommand will throw an unintuitive exception if the user specifies an
        // unexpected argument.
        @Parameter
        List<String> parameters = new ArrayList<String>();

        @Parameter(names = { "--token" }, required = true, description = "Auth token")
        String token;

        @Parameter(names = { "--key" }, required = true, description = "Secret key")
        String secretKey;

        @Parameter(names = { "--root-uri"}, required = true, description = "Server root URI, e.g. http://example.com")
        String rootUri;

        @Parameter(names = { "-h", "--help"}, help = true, description = "Show usage")
        boolean help;
    }

    @Parameters()
    static class CommandGetOperatingSystems {}

    @Parameters()
    static class CommandGetImageDefinitions {}

    @Parameters()
    static class CommandGetCspImages {}

    @Parameters()
    static class CommandGetMachineTypes {}

    public static void main(String[] stringArgs) {
        Arguments args = new Arguments();
        JCommander jc = null;

        try {
            jc = new JCommander(args);
            jc.addCommand("getOperatingSystems", new CommandGetOperatingSystems(), "gos");
            jc.addCommand("getImageDefinitions", new CommandGetImageDefinitions(), "gid");
            jc.addCommand("getCspImages", new CommandGetCspImages(), "gci");
            jc.addCommand("getMachineTypes", new CommandGetMachineTypes(), "gmt");
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
    }
}
