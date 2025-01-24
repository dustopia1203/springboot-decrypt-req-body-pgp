package com.example.pgpdemoservice;

import org.pgpainless.sop.SOPImpl;
import sop.SOP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {
        SOP sop = new SOPImpl();

//        byte[] privateKeyBytes = sop.generateKey()
//                .userId("John Doe <john.doe@pgpainless.org>")
//                .generate()
//                .getBytes();
//        System.out.println(new String(privateKeyBytes));
//
//        byte[] publicKeyBytes = sop.extractCert()
//                .key(privateKeyBytes)
//                .getBytes();
//        System.out.println(new String(publicKeyBytes));
//
//        Files.write(Paths.get("private-key.asc"), privateKeyBytes);
//        Files.write(Paths.get("public-key.asc"), publicKeyBytes);

        byte[] privateKeyBytes = Files.readAllBytes(Paths.get("private-key.asc"));
        byte[] publicKeyBytes = Files.readAllBytes(Paths.get("public-key.asc"));

//        System.out.println(new String(privateKeyBytes));
//        System.out.println(new String(publicKeyBytes));

        String plain = "";
        System.out.println(plain);
        System.out.println(LocalDateTime.now());

//        byte[] encryptedBytes = sop.encrypt()
//                .withCert(publicKeyBytes)
//                .plaintext(plaintext)
//                .toByteArrayAndResult().getBytes();
//        System.out.println(new String(encryptedBytes));
//
//        byte[] decryptedBytes = sop.decrypt()
//                .withKey(privateKeyBytes)
//                .verifyWithCert(publicKeyBytes)
//                .ciphertext(encryptedBytes)
//                .toByteArrayAndResult().getBytes();
//        System.out.println(new String(decryptedBytes));

        String encrypted = CryptoUtils.encrypt(sop, plain, publicKeyBytes);
        System.out.println(encrypted);
        System.out.println(LocalDateTime.now());

        String decrypted = CryptoUtils.decrypt(sop, encrypted, privateKeyBytes, publicKeyBytes);
        System.out.println(decrypted);
        System.out.println(LocalDateTime.now());
    }
}