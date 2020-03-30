package com.enterprise_engineering.web.config;

import com.enterprise_engineering.prime.generator.PrimeGeneratorStrategySelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(false).
                favorParameter(true).
                parameterName("mediaType").
                defaultContentType(APPLICATION_JSON).
                mediaType("xml", APPLICATION_XML).
                mediaType("json", APPLICATION_JSON).
                useRegisteredExtensionsOnly(true);
    }

    @Bean
    PrimeGeneratorStrategySelector primeGeneratorStrategySelector() {
        return new PrimeGeneratorStrategySelector();
    }

}
