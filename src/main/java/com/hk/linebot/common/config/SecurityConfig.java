package com.hk.linebot.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hk.linebot.common.enums.ResponseCode;
import com.hk.linebot.common.response.RestApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private ObjectMapper objectMapper;

    public SecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public UnauthEntryPoint unauthEntryPoint() {
        return new UnauthEntryPoint(objectMapper);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.authorizeHttpRequests(registry -> registry
                                .requestMatchers(HttpMethod.POST, "/v1/lineBot/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/v1/lineBot/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/callback").permitAll()
                                .anyRequest().authenticated()
        ).csrf(AbstractHttpConfigurer::disable)
        .securityMatcher("/swagger-ui.html/lineBot")
        .exceptionHandling().authenticationEntryPoint(unauthEntryPoint());
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }

    public class UnauthEntryPoint implements AuthenticationEntryPoint {

        private ObjectMapper objectMapper;

        public UnauthEntryPoint(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public void commence(
                HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
                throws IOException, ServletException {

            RestApiResponse body = RestApiResponse.builder().build();
            body.setDesc(ResponseCode.SYSTEM_ERROR.getDescription());
            String json = objectMapper.writeValueAsString(body);

            PrintWriter out = response.getWriter();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(Integer.parseInt(ResponseCode.SYSTEM_ERROR.getCode()));

            out.print(json);
            out.flush();
        }
    }
}