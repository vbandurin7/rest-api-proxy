package com.vk.restapiproxy.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AlbumResponse(@JsonProperty("userId") long userId, @JsonProperty("id") long id,
                            @JsonProperty("title") String title) {
}
