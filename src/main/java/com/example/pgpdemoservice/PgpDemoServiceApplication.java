package com.example.pgpdemoservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pgpainless.sop.SOPImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sop.SOP;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class PgpDemoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PgpDemoServiceApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public SOP sop() {
        return new SOPImpl();
    }

    @Bean
    public byte[] publicKeyBytes() {
        try {
            return Files.readAllBytes(Paths.get("public-key.asc"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Bean
    public byte[] privateKeyBytes() {
        try {
            return Files.readAllBytes(Paths.get("private-key.asc"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
