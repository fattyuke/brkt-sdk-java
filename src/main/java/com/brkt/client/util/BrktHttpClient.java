package com.brkt.client.util;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Sends an HTTP request to the Bracket service and returns the response payload.
 */
public class BrktHttpClient {

    public static final byte[] NO_CONTENT = new byte[0];

    private final String rootUri;
    private final String secretKey;
    private final String accessToken;
    private final int timeoutMillis;

    private enum Method {
        GET, POST, DELETE
    }

    public static class Response {
        public final int status;
        public final String message;
        public final byte[] payload;

        public Response(int status, String message, byte[] payload) {
            this.status = status;
            this.message = message;
            this.payload = payload;
        }

        public String toString() {
            return String.format("%d %s", status, message);
        }
    }

    private BrktHttpClient(Builder builder) {
        rootUri = builder.rootUri;
        secretKey = builder.secretKey;
        accessToken = builder.accessToken;
        timeoutMillis = builder.timeoutMillis;
    }

    private HttpURLConnection newConnection(String path, Method method)
    throws IOException {
        URL url = new URL(rootUri + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(timeoutMillis);
        conn.setReadTimeout(timeoutMillis);

        int port = url.getPort();
        if (port < 0) {
            port = url.getDefaultPort();
        }

        BrktAuth auth = new BrktAuth(url.getHost(), port, secretKey, accessToken);
        String authValue = auth.generateAuthHeader(method.toString(), path);

        conn.setRequestMethod(method.toString());
        conn.setRequestProperty("Authorization", authValue);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");

        return conn;
    }

    private byte[] readPayload(HttpURLConnection conn) throws IOException {
        InputStream in = null;
        try {
            if (conn.getResponseCode() / 100 == 2) {
                in = conn.getInputStream();
            } else {
                in = conn.getErrorStream();
            }
            return ByteStreams.toByteArray(in);
        } finally {
            Closeables.closeQuietly(in);
        }
    }

    public Response get(String path) throws IOException {
        HttpURLConnection conn = newConnection(path, Method.GET);
        conn.connect();
        return new Response(conn.getResponseCode(), conn.getResponseMessage(), readPayload(conn));
    }

    public Response post(String path, byte[] requestPayload) throws IOException {
        HttpURLConnection conn = newConnection(path, Method.POST);
        conn.setDoOutput(true);
        if (requestPayload != null) {
            ByteStreams.copy(new ByteArrayInputStream(requestPayload), conn.getOutputStream());
        }
        conn.connect();
        return new Response(conn.getResponseCode(), conn.getResponseMessage(), readPayload(conn));
    }

    public Response delete(String path) throws IOException {
        HttpURLConnection conn = newConnection(path, Method.DELETE);
        conn.connect();
        return new Response(conn.getResponseCode(), conn.getResponseMessage(), readPayload(conn));
    }

    public static class Builder {
        private String rootUri;
        private String accessToken;
        private String secretKey;
        private int timeoutMillis = 10000;

        public Builder(String baseUri) {
            this.rootUri = baseUri;
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
            Preconditions.checkArgument(millis >= 0, "millis cannot be negative: " + millis);
            timeoutMillis = millis;
            return this;
        }

        public BrktHttpClient build() {
            Preconditions.checkNotNull(rootUri, "rootUri cannot be null");
            Preconditions.checkNotNull(accessToken, "accessToken cannot be null");
            Preconditions.checkNotNull(secretKey, "secretKey cannot be null");
            return new BrktHttpClient(this);
        }
    }
}
