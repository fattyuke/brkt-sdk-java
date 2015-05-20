package com.brkt.client.util;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestMain {

    @Test
    public void testSplitParams() {
        List<String> paramStrings = Lists.newArrayList("a=b", "onetwothree=123");
        Map<String, Object> params = Main.splitParams(paramStrings);
        assertEquals(2, params.size());
        assertEquals("b", params.get("a"));
        assertEquals("123", params.get("onetwothree"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSplitParamsException() {
        List<String> paramStrings = Lists.newArrayList("a=b", "abc");
        Main.splitParams(paramStrings);
    }
}
