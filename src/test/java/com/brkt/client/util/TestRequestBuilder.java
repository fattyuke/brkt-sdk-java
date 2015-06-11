package com.brkt.client.util;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestRequestBuilder {

    @Test
    public void testBuild() {
        RequestBuilder builder = new RequestBuilder();
        Map<String, Object> attrs = builder.attr("one", 1).attr("two", 2).build();
        assertEquals(2, attrs.size());
        assertEquals(1, attrs.get("one"));
        assertEquals(2, attrs.get("two"));
    }
}
