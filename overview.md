Intro
-----

The **Bracket Java SDK** is a Java library for interacting with the Bracket
REST API.  The caller has the flexibility of making high-level calls that
return Java objects, or lower-level calls that return the response payload
as a `Map` of JSON elements or `byte[]`.

Sample Code
-----------

Here's some sample code that updates the name of a billing group:

    String rootUri = "http://10.95.0.10";
    String macKey = "6e08558938f346038c61716ccb98dacf";
    String accessToken = "60435a8b84104e25bbe13ad7b63f8695";

    {@link com.brkt.client.util.BrktRestClient} client = new {@link com.brkt.client.util.BrktRestClient.Builder}(rootUri)
        .macKey(macKey).accessToken(accessToken).build();
    {@link com.brkt.client.BrktService} service = new BrktService(client);

    {@link com.brkt.client.BillingGroup} engineering = null;
    for (BillingGroup bg : service.getAllBillingGroups()) {
        if (bg.getName().equals("Engineering")) {
            engineering = bg;
        }
    }

    if (engineering != null) {
        Map<String, Object> attrs = {@link com.brkt.client.BillingGroupRequestBuilder#newUpdateRequest}
            .name("Product Development").build();
        service.updateBillingGroup(engineering.getId(), attrs);
    }

Components
----------

* {@link com.brkt.client.util.BrktHttpClient} is an HTTP-level interface to
the Bracket service.  It uses {@link com.brkt.client.util.BrktAuth} to
generate the `Authorization` OAuth2 header based on the user's MAC key and
access token.

* {@link com.brkt.client.util.BrktRestClient} is a higher-level client.
It accepts a request as a Java ``Map`` and uses the ``Gson`` library to
deserialize the server response to a plain Java object.

* {@link com.brkt.client.BrktService} contains high-level methods for
interacting with the Bracket service.

Installing
----------

To install the Java SDK, clone the git repo and run the Maven build. The
build places the JAR and Javadoc in the `target` directory.  **Note that
the git URL will change once we move the SDK to a public repo.**

    ~/test$ git clone git@github.int.brkt.com:boris/brkt-java-sdk.git
    Cloning into 'brkt-java-sdk'...
    remote: Counting objects: 917, done.
    remote: Compressing objects: 100% (428/428), done.
    remote: Total 917 (delta 532), reused 717 (delta 333)
    Receiving objects: 100% (917/917), 300.17 KiB | 0 bytes/s, done.
    Resolving deltas: 100% (532/532), done.
    Checking connectivity... done.

    ~/test$ cd brkt-java-sdk/

    ~/test/brkt-java-sdk (master)$ mvn install
    ...
    [INFO] Building brkt-java-sdk 1.0-SNAPSHOT
    ...
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 7.048 s
    [INFO] Finished at: 2015-06-09T11:33:58-07:00
    [INFO] Final Memory: 17M/81M
    [INFO] ------------------------------------------------------------------------

Command line interface
----------------------

The Bracket Java SDK JAR file is executable.  This allows you
to exercise the code in {@link com.brkt.client.BrktService} from the command
line.  You can find the CLI code in {@link com.brkt.client.util.Main}.

    ~/test/brkt-java-sdk (master)$ java -jar ./target/brkt-java-sdk-1.0-SNAPSHOT.jar --token 60435a8b84104e25bbe13ad7b63f8695 --key 6e08558938f346038c61716ccb98dacf --root-uri http://10.95.0.10 getAllWorkloads
    Workload{id=828196b0675f4ef78f858a51321040a9, name=NUC-7407b, requestedState=AVAILABLE}
    Workload{id=9d1bbef921b5486a9bf991b4460131b5, name=NUC-7407c, requestedState=AVAILABLE}
    Workload{id=442c8ad633994ee28fbf998a175cf5b9, name=NUC-7461a, requestedState=AVAILABLE}
