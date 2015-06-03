package com.brkt.client.util;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Builds a {@code Map} of attributes that represents the request payload.
 * Throws {@link MissingFieldsError} if the caller attempts to build a request
 * and doesn't specify all required fields.
 */
public class RequestBuilder {

    public static class MissingFieldsError extends RuntimeException {
        public final Set<String> fieldNames;

        public MissingFieldsError(Collection<String> fieldNames) {
            if (fieldNames != null) {
                this.fieldNames = ImmutableSet.copyOf(fieldNames);
            } else {
                this.fieldNames = Collections.emptySet();
            }
        }

        @Override
        public String getMessage() {
            return "Missing fields: " + Joiner.on(", ").join(fieldNames);
        }
    }

    private Map<String, Object> attrs = Maps.newHashMap();
    private Set<String> requiredFields = Sets.newHashSet();

    public RequestBuilder requiredFields(String... fieldNames) {
        if (fieldNames != null) {
            requiredFields.addAll(Arrays.asList(fieldNames));
        }
        return this;
    }

    /**
     * Add an attribute to this request.
     */
    public RequestBuilder attr(String fieldName, Object value) {
        attrs.put(fieldName, value);
        return this;
    }

    /**
     * Build a {@code Map} that contains all of the added attributes.
     *
     * @throws com.brkt.client.util.RequestBuilder.MissingFieldsError
     * if any required fields are missing
     */
    public Map<String, Object> build() {
        // Check required fields.
        Set<String> missingFields = Sets.newHashSet(requiredFields);
        missingFields.removeAll(attrs.keySet());
        if (!missingFields.isEmpty()) {
            throw new MissingFieldsError(missingFields);
        }

        return attrs;
    }
}
