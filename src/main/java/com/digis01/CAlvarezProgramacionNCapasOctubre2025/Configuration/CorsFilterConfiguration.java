package com.digis01.CAlvarezProgramacionNCapasOctubre2025.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfiguration {
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();
        
        corsConfig.addAllowedOriginPattern("http://localhost:*");
        
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("PUT");
        corsConfig.addAllowedMethod("PATCH");
        corsConfig.addAllowedMethod("DELETE");
        corsConfig.addAllowedMethod("OPTIONS"); 
        
        corsConfig.addAllowedHeader("*");
        
        corsConfig.setAllowCredentials(true);
        
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(source);
    }
}