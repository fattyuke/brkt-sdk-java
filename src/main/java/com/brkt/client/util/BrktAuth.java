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

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class BrktAuth {
    private final String hostname;
    private final int port;
    private final String macKey;
    private final String accessToken;

    public static class CryptoException extends RuntimeException {
        public CryptoException(Throwable cause) {
            super(cause);
        }
    }

    public BrktAuth(String hostname, int port, String macKey, String accessToken) {
        this.hostname = hostname;
        this.port = port;
        this.macKey = macKey;
        this.accessToken = accessToken;
    }

    private static String computeSignature(String baseString, String keyString) {
        byte[] keyBytes = keyString.getBytes();
        SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            byte[] baseStringBytes = baseString.getBytes();
            return new String(Base64.encodeBase64(mac.doFinal(baseStringBytes)));
        } catch (InvalidKeyException  e) {
            throw new CryptoException(e);
        } catch (NoSuchAlgorithmException  e) {
            throw new CryptoException(e);
        }
    }

    /**
     * Generate the value for the {@code Authorization} HTTP header.
     * @throws CryptoException in the unlikely event that JDK encryption is not configured properly.
     */
    public String generateAuthHeader(String httpMethod, String path) {
        // Generate nonce and current timestamp in seconds.
        String uuidString = UUID.randomUUID().toString();
        String nonce = uuidString.replaceAll("-", "");
        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        // Generate the message and Authorization header value.
        String message = String.format(
                "%d\n%s\n%s\n%s\n%s\n%d\n\n",
                currentTimeSeconds, nonce, httpMethod.toUpperCase(), path.toLowerCase(), hostname, port);
        String signature = BrktAuth.computeSignature(message, this.macKey);
        return String.format(
                "MAC id=\"%s\", ts=\"%d\", nonce=\"%s\", mac=\"%s\"",
                accessToken, currentTimeSeconds, nonce, signature);
    }
}
