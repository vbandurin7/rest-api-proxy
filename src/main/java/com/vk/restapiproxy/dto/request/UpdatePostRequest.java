package com.vk.restapiproxy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatePostRequest(@JsonProperty("title") String title,
                                @JsonProperty("body") String body,
                                @JsonProperty("userId") Long userId) implements UpdateRequest {

}
