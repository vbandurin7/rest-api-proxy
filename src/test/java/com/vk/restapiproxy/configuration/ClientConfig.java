package com.vk.restapiproxy.configuration;

import com.vk.restapiproxy.client.AlbumClient;
import com.vk.restapiproxy.client.PostClient;
import com.vk.restapiproxy.client.UserClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public PostClient postClient(ApplicationProperties applicationProperties) {
        return new PostClient(applicationProperties.baseUrl());
    }

    @Bean
    public UserClient userClient(ApplicationProperties applicationProperties) {
        return new UserClient(applicationProperties.baseUrl());
    }

    @Bean
    public AlbumClient albumClient(ApplicationProperties applicationProperties) {
        return new AlbumClient(applicationProperties.baseUrl());
    }
}
