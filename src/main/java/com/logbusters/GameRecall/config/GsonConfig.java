package com.logbusters.GameRecall.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
class WebConfig implements WebMvcConfigurer {

    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create(); // Customizing Gson if needed
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Remove Jackson converter if added
        converters.removeIf(converter -> converter.getClass().getName().contains("Jackson"));

        // Add Gson converter
        converters.add(new GsonHttpMessageConverter(gson()));
    }
}
