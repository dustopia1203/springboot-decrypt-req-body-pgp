package com.example.pgpdemoservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sop.SOP;

import java.io.IOException;

@Component
@Order(1)
@RequiredArgsConstructor
public class DecryptReqBodyFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final SOP sop;
    private final byte[] privateKeyBytes;
    private final byte[] publicKeyBytes;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new DecryptReqBodyRequestWrapper(request, sop, privateKeyBytes, publicKeyBytes, objectMapper), response);
    }
}
