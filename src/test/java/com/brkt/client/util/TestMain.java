package com.brkt.client.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestMain {

    @Test
    public void testSplitParams() {
        List<String> paramStrings = Lists.newArrayList("id123", "a=b", "onetwothree=123");
        Map<String, Object> params = Main.splitAttrs(paramStrings, 1);
        assertEquals(2, params.size());
        assertEquals("b", params.get("a"));
        assertEquals("123", params.get("onetwothree"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSplitParamsException() {
        List<String> paramStrings = Lists.newArrayList("a=b", "abc");
        Main.splitAttrs(paramStrings, 0);
    }

    @Test
    public void testConvertKeys() {
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put("attrOne", 1);
        attrs.put("AttrTwo", 2);
        attrs.put("attr_three", 3);
        attrs.put("attrfour", 4);

        attrs = Main.convertKeysToUnderscore(attrs);
        assertEquals(4, attrs.size());
        assertEquals(1, attrs.get("attr_one"));
        assertEquals(2, attrs.get("attr_two"));
        assertEquals(3, attrs.get("attr_three"));
        assertEquals(4, attrs.get("attrfour"));
    }
}
