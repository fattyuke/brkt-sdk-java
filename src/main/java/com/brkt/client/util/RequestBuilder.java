/*
* Copyright 2015 Bracket Computing, Inc. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License").
* You may not use this file except in compliance with the License.
* A copy of the License is located at
*
* https://github.com/brkt/brkt-sdk-java/blob/master/LICENSE
*
* or in the "license" file accompanying this file. This file is
* distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
* CONDITIONS OF ANY KIND, either express or implied. See the 
* License for the specific language governing permissions and
* limitations under the License.
*/

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
