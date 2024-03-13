package com.vk.restapiproxy.client;

import com.vk.restapiproxy.dto.response.client.AlbumResponse;

public class AlbumClient extends AbstractClient<AlbumResponse> {

    public AlbumClient(String url) {
        init(url);
        ENDPOINT = "/albums";
    }

    @Override
    public Class<AlbumResponse> responseType() {
        return AlbumResponse.class;
    }
}
