package com.ptrader.connector.kraken.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

class ByteUtils {

    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    private ByteUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Convert string to bytes using UTF-8 charset
     *
     * @param input to convert
     * @return converted string to bytes array
     */
    static byte[] stringToBytes(String input) {
        return input.getBytes(UTF8_CHARSET);
    }

    /**
     * Concatenate arrays of bytes into one
     *
     * @param a first array
     * @param b second array
     * @return array of bytes concatenated
     */
    static byte[] concatArrays(byte[] a, byte[] b) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(a);
        outputStream.write(b);

        return outputStream.toByteArray();
    }
}
