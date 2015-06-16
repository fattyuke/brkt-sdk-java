/*
* Copyright 2015 Bracket Computing, Inc. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License").
* You may not use this file except in compliance with the License.
* A copy of the License is located at
*
* https://github.com/brkt/brkt-java-sdk/blob/master/LICENSE
*
* or in the "license" file accompanying this file. This file is
* distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
* CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and
* limitations under the License.
*/

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
