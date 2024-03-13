package com.vk.restapiproxy.client;

import com.vk.restapiproxy.dto.response.client.PostResponse;

public class PostClient extends AbstractClient<PostResponse> {

    public PostClient(String url) {
        init(url);
        ENDPOINT = "/posts";
    }

    @Override
    public Class<PostResponse> responseType() {
        return PostResponse.class;
    }
}
