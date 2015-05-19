package com.brkt.client.util;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BrktHttpClient {

    private static final byte[] NO_CONTENT = new byte[0];

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

    private final String rootUri;
    private final String secretKey;
    private final String accessToken;

    private BrktHttpClient(Builder builder) {
        rootUri = builder.rootUri;
        secretKey = builder.secretKey;
        accessToken = builder.accessToken;
    }

    public Response get(String path) throws IOException {
        URL url = new URL(rootUri + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        int port = url.getPort();
        if (port < 0) {
            port = url.getDefaultPort();
        }
        BrktAuth auth = new BrktAuth(url.getHost(), port, secretKey, accessToken);
        String authValue = auth.generateAuthHeader("GET", path);

        conn.setRequestProperty("Authorization", authValue);
        conn.setRequestProperty("Accept", "application/json");
        conn.connect();
        int statusCode = conn.getResponseCode();
        byte[] payload = NO_CONTENT;
        if (statusCode / 100 == 2) {
            InputStream in = null;
            try {
                in = conn.getInputStream();
                payload = ByteStreams.toByteArray(in);
            } finally {
                Util.close(in);
            }
        }
        return new Response(statusCode, conn.getResponseMessage(), payload);
    }

    public static class Builder {
        private String rootUri;
        private String accessToken;
        private String secretKey;

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

        public BrktHttpClient build() {
            Preconditions.checkNotNull(rootUri, "rootUri cannot be null");
            Preconditions.checkNotNull(accessToken, "accessToken cannot be null");
            Preconditions.checkNotNull(secretKey, "secretKey cannot be null");
            return new BrktHttpClient(this);
        }
    }
}
