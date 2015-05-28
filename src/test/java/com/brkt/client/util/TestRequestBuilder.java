package com.brkt.client.util;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestRequestBuilder {

    private RequestBuilder newBuilder() {
        return new RequestBuilder().requiredFields("one", "two");
    }

    @Test
    public void testBuild() {
        RequestBuilder builder = newBuilder();
        Map<String, Object> attrs = builder.attr("one", 1).attr("two", 2).build();
        assertEquals(2, attrs.size());
        assertEquals(1, attrs.get("one"));
        assertEquals(2, attrs.get("two"));
    }

    @Test
    public void testRequiredFields() {
        newBuilder().attr("one", null).attr("two", null).build();

        try {
            newBuilder().attr("one", null).attr("three", null).build();
            fail("MissingFieldsError was not thrown");
        } catch (RequestBuilder.MissingFieldsError e) {
            assertEquals(1, e.fieldNames.size());
            assertTrue(e.fieldNames.contains("two"));
        }
    }
}
