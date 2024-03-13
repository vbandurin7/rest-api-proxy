package com.vk.restapiproxy.database.service;

import com.vk.restapiproxy.client.UserClient;
import com.vk.restapiproxy.database.entity.Role;
import com.vk.restapiproxy.database.entity.User;
import com.vk.restapiproxy.database.repository.RoleRepository;
import com.vk.restapiproxy.database.repository.UserRepository;
import com.vk.restapiproxy.dto.request.AddRequest;
import com.vk.restapiproxy.dto.request.UpdateRequest;
import com.vk.restapiproxy.dto.response.client.UserResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserService extends AbstractService<UserResponse> {
    private static final String USER_CACHE_NAME = "user";
    private static final String USERS_CACHE_NAME = "users";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserClient userClient, UserRepository userRepository,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.client = userClient;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

        prepare();
    }

    @Override
    @Cacheable(value = USER_CACHE_NAME, key = "#id")
    public UserResponse find(long id) {
        return super.find(id);
    }

    @Override
    @Cacheable(USERS_CACHE_NAME)
    public List<UserResponse> findAll() {
        return super.findAll();
    }

    @Override
    @CacheEvict(USERS_CACHE_NAME)
    public <S extends AddRequest> UserResponse add(S addRequest) {
        return super.add(addRequest);
    }

    @Override
    @CachePut(value = USER_CACHE_NAME, key = "#id")
    @CacheEvict(value = USERS_CACHE_NAME, allEntries = true)
    public <S extends UpdateRequest> UserResponse update(S updateRequest, long id) {
        return super.update(updateRequest, id);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = USERS_CACHE_NAME, allEntries = true),
                    @CacheEvict(value = USER_CACHE_NAME, key = "#id")
            })
    public void delete(long id) {
        super.delete(id);
    }

    public void save(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            userRepository.save(user);
        }
    }

    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof User u) {
                return u;
            }
        }

        return null;
    }

    @Override
    public String entityName() {
        return "user";
    }

    private void prepare() {
        for (Role.Name name : Role.Name.values()) {
            if (roleRepository.countByName(name) == 0) {
                Role role = new Role();
                role.setName(name);
                roleRepository.save(role);
            }
        }

        // For convenience purposes
        setupUser("admin", Role.Name.ROLE_ADMIN);
        setupUser("poster", Role.Name.ROLE_POSTS_EDITOR);
        setupUser("pviewer", Role.Name.ROLE_USERS_VIEWER);
    }

    private void setupUser(String username, Role.Name... roles) {
        if (userRepository.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode("123"));
            for (Role.Name role : roles) {
                user.getRoles().add(roleRepository.findByName(role));
            }
            userRepository.save(user);
        }
    }
}
