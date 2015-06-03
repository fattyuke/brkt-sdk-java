Intro
-----

The **Bracket Java SDK** is a Java library for interacting with the Bracket
REST API.  The caller has the flexibility of making high-level calls that
return Java objects, or lower-level calls that return the response payload
as a `Map` of JSON elements or `byte[]`.

Sample Code
-----------

Here's some sample code that retrieves the list of image definitions
from the server:

    String rootUri = "http://10.95.0.10";
    String macKey = "6e08558938f346038c61716ccb98dacf";
    String accessToken = "60435a8b84104e25bbe13ad7b63f8695";

    BrktRestClient client = new BrktRestClient.Builder(rootUri)
        .macKey(macKey).accessToken(accessToken).build();
    BrktService service = new BrktService(client);
    for (ImageDefinition id : service.getAllImageDefinitions()) {
        System.out.println(id);
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
