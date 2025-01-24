package com.example.pgpdemoservice;

import sop.SOP;

public class CryptoUtils {
    public static String encrypt(SOP sop, String plain, byte[] publicKeyBytes) {
        try {
            byte[] encryptedBytes = sop.encrypt()
                    .withCert(publicKeyBytes)
                    .plaintext(plain.getBytes())
                    .toByteArrayAndResult().getBytes();
            return new String(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static String decrypt(SOP sop, String encrypted, byte[] privateKeyBytes, byte[] publicKeyBytes) {
        try {
            byte[] decryptedBytes = sop.decrypt()
                    .withKey(privateKeyBytes)
                    .verifyWithCert(publicKeyBytes)
                    .ciphertext(encrypted.getBytes())
                    .toByteArrayAndResult().getBytes();
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
