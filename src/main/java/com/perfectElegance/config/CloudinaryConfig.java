package com.perfectElegance.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private final String CLOUD_NAME = "dhsedgv2z";
    private final String API_KEY = "735869948299675";
    private final String API_SECRET = "Z5NUqbrmgslPbP0pFUVtSv5ePCo";
    @Bean
    public Cloudinary cloudinary(){

        Map<String,String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);
        return new Cloudinary(config);
    }

}
