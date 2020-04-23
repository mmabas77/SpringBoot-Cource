package com.mmabas77.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("prod")
@PropertySource("classpath:prod.properties")
public class ProdConfig {

    @Value("${stripe.prod.private.key}")
    private String stripeProdKey;

    @Bean
    public String stripeKey() {
        return stripeProdKey;
    }
}
