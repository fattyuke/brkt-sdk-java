package com.brkt.client;

import com.brkt.client.util.BrktAuth;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class TestBrktAuth {

    @Test
    public void testBrktAuth() {
        BrktAuth auth = new BrktAuth("test.example.com", 80, "abc", "def");
        String value = auth.generateAuthHeader("POST", "/foo/bar");
        Pattern pat = Pattern.compile("MAC id=\"def\", ts=\"(\\d+)\", nonce=\"\\p{Alnum}+\", mac=\".+=\"");
        Matcher m = pat.matcher(value);
        assertTrue(m.matches());
    }
}
