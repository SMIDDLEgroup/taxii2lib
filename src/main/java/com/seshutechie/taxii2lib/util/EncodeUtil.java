package com.seshutechie.taxii2lib.util;

import java.util.Base64;

public class EncodeUtil {

    public static String base64Encode(String value) {
        return new String(Base64.getEncoder().encode(value.getBytes()));
    }

    public static String base64Decode(String encoded) {
        return new String(Base64.getDecoder().decode(encoded));
    }
}
