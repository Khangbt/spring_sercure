package com.example.demo.config;

import com.example.demo.exception.CustomExceptionHandler;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Optional;

@Configuration
@EnableJpaAuditing()
public class AuditingConfig {
    @Bean
    public AuditorAware auditorProvider() {
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<String> {
        @Autowired
        private UserRepository userRepository;

        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (null == authentication || !authentication.isAuthenticated()) {
                throw new CustomExceptionHandler("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            Object obj = authentication.getPrincipal();
            return Optional.ofNullable(Objects.nonNull(obj) ? ((UserDetails) obj).getUsername()
                    : "");
        }
    }
}
