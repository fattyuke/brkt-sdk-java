package com.brkt.client.util;

import com.google.common.base.Preconditions;
import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Map;

public class BrktRestClient {

    private final BrktHttpClient httpClient;
    private final Gson gson;

    private BrktRestClient(BrktHttpClient httpClient) {
        this.httpClient = httpClient;
        gson = newGson();
    }

    public static class Builder {
        private String rootUri;
        private String accessToken;
        private String secretKey;
        private Integer timeoutMillis;

        public Builder (String rootUri) {
            this.rootUri = rootUri;
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public Builder timeoutMillis(int millis) {
            this.timeoutMillis = millis;
            return this;
        }

        public BrktRestClient build() {
            Preconditions.checkNotNull(rootUri, "rootUri cannot be null");
            Preconditions.checkNotNull(accessToken, "accessToken cannot be null");
            Preconditions.checkNotNull(secretKey, "secretKey cannot be null");

            BrktHttpClient.Builder builder = new BrktHttpClient.Builder(rootUri)
                    .secretKey(secretKey).accessToken(accessToken);
            if (timeoutMillis != null) {
                builder.timeoutMillis(timeoutMillis);
            }
            return new BrktRestClient(builder.build());
        }
    }

    /**
     * Thrown when the server returns an unsuccessful HTTP status code.
     */
    public static class HttpError extends Exception {
        public final int status;
        public final String message;

        public HttpError(int status, String message) {
            super("" + status + " " + message);
            this.status = status;
            this.message = message;
        }
    }

    public static Gson newGson() {
        // Custom serialization for ISO 8601 timestamps.
        JsonSerializer timestampSerializer = new JsonSerializer<Timestamp>() {
            public JsonElement serialize(Timestamp ts, Type typeOfSrc, JsonSerializationContext context) {
                DateTime dt = new DateTime(ts.getTime()).withZone(DateTimeZone.UTC);
                return new JsonPrimitive(dt.toString());
            }
        };

        JsonDeserializer<Timestamp> timestampDeserializer = new JsonDeserializer<Timestamp>() {
            @Override
            public Timestamp deserialize(
                    JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
                    throws JsonParseException {
                DateTime dt = new DateTime(jsonElement.getAsJsonPrimitive().getAsString());
                return new Timestamp(dt.getMillis());

            }
        };

        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Timestamp.class, timestampSerializer)
                .registerTypeAdapter(Timestamp.class, timestampDeserializer)
                .create();
    }

    /**
     * Get the resource and deserialize to an object of the given type.
     */
    public <T> T get(String path, Type type) throws IOException, HttpError {
        BrktHttpClient.Response response = httpClient.get(path);
        if (response.status / 100 != 2) {
            throw new HttpError(response.status, response.message);
        }
        Reader reader = new InputStreamReader(new ByteArrayInputStream(response.payload));
        return gson.fromJson(reader, type);
    }

    /**
     * Get the resource and deserialize to an object of the given type.
     */
    public <T> T get(String path, Class<T> myClass) throws IOException, HttpError {
        BrktHttpClient.Response response = httpClient.get(path);
        if (response.status / 100 != 2) {
            throw new HttpError(response.status, response.message);
        }
        Reader reader = new InputStreamReader(new ByteArrayInputStream(response.payload));
        return gson.fromJson(reader, myClass);
    }

    /**
     * Post an element map to the server and deserialize to an object of the given type.
     */
    public <T> T post(String path, Class<T> myClass, Map<String, Object> elements)
            throws IOException, HttpError {
        byte[] requestPayload = BrktHttpClient.NO_CONTENT;
        if (elements != null) {
            String json = gson.toJson(elements);
            requestPayload = json.getBytes();
        }
        BrktHttpClient.Response response = httpClient.post(path, requestPayload);
        if (response.status / 100 != 2) {
            throw new HttpError(response.status, response.message);
        }
        Reader reader = new InputStreamReader(new ByteArrayInputStream(response.payload));
        return gson.fromJson(reader, myClass);
    }
}
