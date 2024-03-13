package com.vk.restapiproxy.client;

import com.vk.restapiproxy.dto.response.client.UserResponse;

public class UserClient extends AbstractClient<UserResponse> {

    public UserClient(String url) {
        init(url);
        ENDPOINT = "/users";
    }

    @Override
    public Class<UserResponse> responseType() {
        return UserResponse.class;
    }
}
