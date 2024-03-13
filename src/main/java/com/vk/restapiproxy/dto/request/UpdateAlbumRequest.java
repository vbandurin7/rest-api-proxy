package com.vk.restapiproxy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateAlbumRequest(@JsonProperty("userId") long userId,
                                 @JsonProperty("title") String title) implements UpdateRequest {

}
