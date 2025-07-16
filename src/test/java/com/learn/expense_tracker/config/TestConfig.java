package com.learn.expense_tracker.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.*;

import com.learn.expense_tracker.security.MethodSecurityCheckAspect;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public MethodSecurityCheckAspect methodSecurityCheckAspect() {
        return mock(MethodSecurityCheckAspect.class); // Disable AOP check
    }
}
