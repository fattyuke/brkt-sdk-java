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

/**
 * Sends a REST request to the Bracket service and deserializes the response
 * into one or more Java objects.
 */
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
        private String macKey;
        private Integer timeoutMillis;

        public Builder (String rootUri) {
            this.rootUri = rootUri;
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder macKey(String macKey) {
            this.macKey = macKey;
            return this;
        }

        public Builder timeoutMillis(int millis) {
            this.timeoutMillis = millis;
            return this;
        }

        public BrktRestClient build() {
            Preconditions.checkNotNull(rootUri, "rootUri cannot be null");
            Preconditions.checkNotNull(accessToken, "accessToken cannot be null");
            Preconditions.checkNotNull(macKey, "macKey cannot be null");

            BrktHttpClient.Builder builder = new BrktHttpClient.Builder(rootUri)
                    .macKey(macKey).accessToken(accessToken);
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
        /**
         * The HTTP status code returned by the server.
         */
        public final int status;

        /**
         * The message from the status line returned by the server.
         */
        public final String message;

        /**
         * The payload returned by the server.
         */
        public final byte[] payload;

        public HttpError(int status, String message, byte[] payload) {
            this.status = status;
            this.message = message;
            if (payload == null) {
                this.payload = BrktHttpClient.NO_CONTENT;
            } else {
                this.payload = payload;
            }
        }

        @Override
        public String getMessage() {
            StringBuilder buf = new StringBuilder().append(status);
            if (message != null) {
                buf.append(' ').append(message);
            }

            // The payload doesn't provide anything useful for 404.
            if (status != 404 && payload.length > 0) {
                buf.append(' ').append(new String(payload));
            }
            if (buf.length() <= 200) {
                return buf.toString();
            }
            return buf.substring(0, 200) + "...";
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
            throw new HttpError(response.status, response.message, response.payload);
        }
        Reader reader = new InputStreamReader(new ByteArrayInputStream(response.payload));
        return gson.fromJson(reader, type);
    }

    /**
     * Post an element map to the server and deserialize to an object of the given type.
     */
    public <T> T post(String path, Type type, Map<String, Object> attrs)
            throws IOException, HttpError {
        byte[] requestPayload = BrktHttpClient.NO_CONTENT;
        if (attrs != null) {
            String json = gson.toJson(attrs);
            requestPayload = json.getBytes();
        }
        BrktHttpClient.Response response = httpClient.post(path, requestPayload);
        if (response.status / 100 != 2) {
            throw new HttpError(response.status, response.message, response.payload);
        }
        Reader reader = new InputStreamReader(new ByteArrayInputStream(response.payload));
        return gson.fromJson(reader, type);
    }

    /**
     * Delete the resource.  If {@code myClass} is not {@code null}, deserialize
     * to an object of the given type.
     */
    public <T> T delete(String path, Type type) throws IOException, HttpError {
        BrktHttpClient.Response response = httpClient.delete(path);
        if (response.status / 100 != 2) {
            throw new HttpError(response.status, response.message, response.payload);
        }
        if (type != null) {
            Reader reader = new InputStreamReader(new ByteArrayInputStream(response.payload));
            return gson.fromJson(reader, type);
        }
        return null;
    }

}
