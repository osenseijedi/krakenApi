package com.ptrader.connector.kraken.utils;

import java.util.Base64;

final class Base64Utils {

    private Base64Utils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Decode from Base64
     *
     * @param input data to decode
     * @return decoded data
     */
    static byte[] base64Decode(String input) {
        return Base64.getDecoder().decode(input);
    }

    /**
     * Encode into Base64
     *
     * @param data to encode
     * @return encoded data
     */
    static String base64Encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
