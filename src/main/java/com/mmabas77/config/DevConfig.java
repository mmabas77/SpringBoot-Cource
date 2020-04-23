package com.mmabas77.config;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.ServletRegistration;

@Configuration
@Profile("dev")
@PropertySource("classpath:dev.properties")
public class DevConfig {

    @Value("${stripe.test.private.key}")
    private String stripeDevKey;

    @Bean
    public ServletRegistrationBean h2ServletRegistration() {
        ServletRegistrationBean servletRegistrationBean =
                new ServletRegistrationBean(new WebServlet());
        servletRegistrationBean.addUrlMappings("/console/*");
        return servletRegistrationBean;
    }

    @Bean
    public String stripeKey(){
        return stripeDevKey;
    }
}
