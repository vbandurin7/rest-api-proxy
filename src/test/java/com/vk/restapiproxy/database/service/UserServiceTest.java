package com.vk.restapiproxy.database.service;

import com.vk.restapiproxy.client.JsonPlaceholderClient;
import com.vk.restapiproxy.client.UserClient;
import com.vk.restapiproxy.dto.response.client.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class UserServiceTest extends AbstractServiceTest<UserResponse> {

    @Autowired
    private UserService userService;

    @MockBean
    private UserClient userClient;

    @Override
    protected Service<UserResponse> getService() {
        return userService;
    }

    @Override
    protected JsonPlaceholderClient<UserResponse> getClient() {
        return userClient;
    }

    @Override
    protected Class<UserResponse> responseType() {
        return UserResponse.class;
    }
}
