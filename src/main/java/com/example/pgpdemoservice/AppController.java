package com.example.pgpdemoservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sop.SOP;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class AppController {
    private final ObjectMapper objectMapper;
    private final SOP sop;
    private final byte[] privateKeyBytes;
    private final byte[] publicKeyBytes;

    @GetMapping("/pk")
    public String getPublicKey() {
        return new String(publicKeyBytes);
    }

    @PostMapping("/enc")
    public String encryptBody(@RequestBody Object o) {
        try {
            String body = objectMapper.writeValueAsString(o);
            return CryptoUtils.encrypt(sop, body, publicKeyBytes);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PostMapping("/dec")
    public Object decryptBody(@RequestBody Object o) {
        return o;
    }
}
