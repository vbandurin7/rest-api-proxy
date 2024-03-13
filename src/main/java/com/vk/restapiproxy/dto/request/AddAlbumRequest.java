package com.vk.restapiproxy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddAlbumRequest(@JsonProperty("userId") long userId,
                              @JsonProperty("title") String title) implements AddRequest {
}
