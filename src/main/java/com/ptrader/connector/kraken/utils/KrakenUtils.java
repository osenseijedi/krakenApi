package com.ptrader.connector.kraken.utils;

import com.ptrader.connector.kraken.KrakenApiException;

public class KrakenUtils {

    public static String generateNonce() {
        return String.valueOf(System.currentTimeMillis() * 1000);
    }

    public static String generateSignature(String path, String nonce, String secret, String postData) throws KrakenApiException {
        // Algorithm: HMAC-SHA512 of (URI path + SHA256(nonce + POST data)) and base64 decoded secret API key

        String hmacDigest;

        try {
            byte[] bytePath = ByteUtils.stringToBytes(path);
            byte[] sha256 = CryptoUtils.sha256(nonce + postData);
            byte[] hmacMessage = ByteUtils.concatArrays(bytePath, sha256);

            byte[] hmacKey = Base64Utils.base64Decode(secret);

            hmacDigest = Base64Utils.base64Encode(CryptoUtils.hmacSha512(hmacKey, hmacMessage));
        } catch (Throwable ex) {
            throw new KrakenApiException("unable to generate signature");
        }
        return hmacDigest;
    }
}
