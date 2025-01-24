package com.example.pgpdemoservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.http.HttpMethod;
import sop.SOP;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public class DecryptReqBodyRequestWrapper extends HttpServletRequestWrapper {
    private final String body;

    public DecryptReqBodyRequestWrapper(HttpServletRequest request, SOP sop, byte[] privateKeyBytes, byte[] publicKeyBytes, ObjectMapper objectMapper) {
        super(request);
        this.body = processRequestBody(request, sop, privateKeyBytes, publicKeyBytes, objectMapper);
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String processRequestBody(HttpServletRequest request, SOP sop, byte[] privateKeyBytes, byte[] publicKeyBytes, ObjectMapper objectMapper) {
        try {
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            if (!body.isBlank() && Objects.equals(HttpMethod.valueOf(request.getMethod()), HttpMethod.POST) && !Objects.equals(request.getRequestURI(), "/enc")) {
                JsonNode root = objectMapper.readTree(body);
                if (root.has("data")) {
                    String encrypted = root.get("data").asText();
                    return CryptoUtils.decrypt(sop, encrypted, privateKeyBytes, publicKeyBytes);
                }
            }
            return body;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
