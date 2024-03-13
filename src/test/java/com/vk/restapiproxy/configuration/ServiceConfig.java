package com.vk.restapiproxy.configuration;

import com.vk.restapiproxy.client.AlbumClient;
import com.vk.restapiproxy.client.PostClient;
import com.vk.restapiproxy.client.UserClient;
import com.vk.restapiproxy.database.repository.AuditRepository;
import com.vk.restapiproxy.database.repository.ResourceTypeRepository;
import com.vk.restapiproxy.database.repository.RoleRepository;
import com.vk.restapiproxy.database.repository.UserRepository;
import com.vk.restapiproxy.database.service.AlbumService;
import com.vk.restapiproxy.database.service.AuditService;
import com.vk.restapiproxy.database.service.PostService;
import com.vk.restapiproxy.database.service.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@Profile("test")
@Import(ClientConfig.class)
public class ServiceConfig {

    @Bean
    public PostService postService(PostClient postClient) {
        return new PostService(postClient);
    }

    @Bean
    public UserService userService(UserClient userClient, UserRepository userRepository,
                                   RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return new UserService(userClient, userRepository, roleRepository, passwordEncoder);
    }

    @Bean
    public AlbumService albumService(AlbumClient albumClient) {
        return new AlbumService(albumClient);
    }

    @Bean
    public AuditService auditService(AuditRepository auditRepository, ResourceTypeRepository resourceTypeRepository) {
        return new AuditService(auditRepository, resourceTypeRepository);
    }
}
