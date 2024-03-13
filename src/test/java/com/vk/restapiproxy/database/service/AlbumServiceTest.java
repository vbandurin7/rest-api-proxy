package com.vk.restapiproxy.database.service;

import com.vk.restapiproxy.client.AlbumClient;
import com.vk.restapiproxy.client.JsonPlaceholderClient;
import com.vk.restapiproxy.dto.response.client.AlbumResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class AlbumServiceTest extends AbstractServiceTest<AlbumResponse> {

    @Autowired
    private AlbumService albumService;

    @MockBean
    private AlbumClient albumClient;

    @Override
    protected Service<AlbumResponse> getService() {
        return albumService;
    }

    @Override
    protected JsonPlaceholderClient<AlbumResponse> getClient() {
        return albumClient;
    }

    @Override
    protected Class<AlbumResponse> responseType() {
        return AlbumResponse.class;
    }
}
