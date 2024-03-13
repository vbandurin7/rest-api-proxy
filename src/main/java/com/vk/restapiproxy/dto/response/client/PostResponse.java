package com.vk.restapiproxy.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostResponse(@JsonProperty("userId") long userId, @JsonProperty("id") long id,
                           @JsonProperty("title") String title, @JsonProperty("body") String body) {
}
