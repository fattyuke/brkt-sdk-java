package com.brkt.client.util;

import java.io.IOException;
import java.io.InputStream;

public class Util {

    /**
     * Close the given stream, ignoring any exceptions.
     */
    public static void close(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
    }
}
