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

        public BrktRestClient build() {
            Preconditions.checkNotNull(rootUri, "rootUri cannot be null");
            Preconditions.checkNotNull(accessToken, "accessToken cannot be null");
            Preconditions.checkNotNull(secretKey, "secretKey cannot be null");

            BrktHttpClient httpClient = new BrktHttpClient.Builder(rootUri)
                    .secretKey(secretKey).accessToken(accessToken).build();
            return new BrktRestClient(httpClient);
        }
    }

    /**
     * Thrown when the server returns an unsuccessful HTTP status code.
     */
    public static class HttpError extends RuntimeException {
        public final int status;
        public final String message;

        public HttpError(int status, String message) {
            super("" + status + " " + message);
            this.status = status;
            this.message = message;
        }
    }

    /**
     * Thrown when an {@link IOException} occurs while talking to the server.
     */
    public static class RuntimeIoException extends RuntimeException {
        public RuntimeIoException(IOException cause) {
            super(cause);
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
     * Get the resource and deserialize to the an object of the given type.
     * @throws RuntimeIoException if an {@link IOException} was thrown
     * @throws HttpError if an HTTP error response was returned
     */
    public <T> T get(String path, Type type) {
        BrktHttpClient.Response response;
        try {
            response = httpClient.get(path);
        } catch (IOException e) {
            throw new RuntimeIoException(e);
        }

        if (response.status / 100 != 2) {
            throw new HttpError(response.status, response.message);
        }
        Reader reader = new InputStreamReader(new ByteArrayInputStream(response.payload));
        return gson.fromJson(reader, type);
    }

    /**
     * Get the resource and deserialize to the an object of the given type.
     * @throws RuntimeIoException if an {@link IOException} was thrown
     * @throws HttpError if an HTTP error response was returned
     */
    public <T> T get(String path, Class<T> myClass) {
        BrktHttpClient.Response response;
        try {
            response = httpClient.get(path);
        } catch (IOException e) {
            throw new RuntimeIoException(e);
        }

        if (response.status / 100 != 2) {
            throw new HttpError(response.status, response.message);
        }
        Reader reader = new InputStreamReader(new ByteArrayInputStream(response.payload));
        return gson.fromJson(reader, myClass);
    }
}
