package com.vk.restapiproxy.database.service;

import com.vk.restapiproxy.client.JsonPlaceholderClient;
import com.vk.restapiproxy.client.PostClient;
import com.vk.restapiproxy.dto.response.client.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class PostServiceTest extends AbstractServiceTest<PostResponse> {

    @Autowired
    private PostService postService;

    @MockBean
    private PostClient postClient;

    @Override
    protected Service<PostResponse> getService() {
        return postService;
    }

    @Override
    protected JsonPlaceholderClient<PostResponse> getClient() {
        return postClient;
    }

    @Override
    protected Class<PostResponse> responseType() {
        return PostResponse.class;
    }
}
