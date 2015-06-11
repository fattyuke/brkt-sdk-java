package com.brkt.client;

import com.brkt.client.util.BrktRestClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

public class TestBrktRestClient {

    class TimestampHolder {
        Timestamp timestamp;

        TimestampHolder(long longTimestamp) {
            timestamp = new Timestamp(longTimestamp);
        }
    }

    /**
     * Test converting a timestamp field to and from an ISO 8601 string.
     */
    @Test
    public void testJsonTimestamp() {
        Gson gson = BrktRestClient.newGson();
        String json = "{ \"timestamp\": \"2015-03-31T22:34:51.270584+00:00\" }";
        TimestampHolder th = gson.fromJson(json, TimestampHolder.class);

        JsonObject jsonObj = gson.toJsonTree(th).getAsJsonObject();
        String iso8601 = jsonObj.get("timestamp").getAsString();
        assertEquals("2015-03-31T22:34:51.270Z", iso8601); // JodaTime doesn't handle nanoseconds.
    }

    @Test
    public void testHttpErrorMessage() {
        // Null message.
        BrktRestClient.HttpError error = new BrktRestClient.HttpError(400, null, null);
        assertEquals("400", error.getMessage());

        // Short message, no payload.
        error = new BrktRestClient.HttpError(400, "Short message", null);
        assertEquals("400 Short message", error.getMessage());

        // Short message, payload.
        error = new BrktRestClient.HttpError(400, "Short message", "{ 1: 2 }".getBytes());
        assertEquals("400 Short message { 1: 2 }", error.getMessage());

        // Long message.
        byte[] payload = new byte[200];
        for (int i = 0; i < 200; i++) {
            payload[i] = 'x';
        }
        error = new BrktRestClient.HttpError(400, "Short message", payload);
        String expected = "400 Short message " + new String(payload);
        expected = expected.substring(0, 200) + "...";
        assertEquals(expected, error.getMessage());
    }
}
