package com.brkt.client.util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.brkt.client.BrktService;
import com.brkt.client.OperatingSystem;
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
    static class CommandGetOperatingSystems {
    }

    public static void main(String[] stringArgs) {
        Arguments args = new Arguments();
        JCommander jc = null;

        try {
            jc = new JCommander(args);
            jc.addCommand("getOperatingSystems", new CommandGetOperatingSystems(), "gos");
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

        if (command.equals("getOperatingSystems")) {
            BrktRestClient client = new BrktRestClient.Builder(args.rootUri)
                    .secretKey(args.secretKey).accessToken(args.token).build();
            BrktService osService = new BrktService(client);
            for (OperatingSystem os : osService.getOperatingSystems()) {
                System.out.println(os);
            }
        }
    }
}
