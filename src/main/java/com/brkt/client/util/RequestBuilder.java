package com.brkt.client.util;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Builds a {@code Map} of attributes that represents the request payload.
 */
public class RequestBuilder {

    private Map<String, Object> attrs = Maps.newHashMap();

    /**
     * Add an attribute to this request.
     */
    public RequestBuilder attr(String fieldName, Object value) {
        attrs.put(fieldName, value);
        return this;
    }

    /**
     * Build a {@code Map} that contains all of the added attributes.
     */
    public Map<String, Object> build() {
        // Return a new Map, so that the caller can't modify the internal map.
        return Maps.newHashMap(attrs);
    }
}
