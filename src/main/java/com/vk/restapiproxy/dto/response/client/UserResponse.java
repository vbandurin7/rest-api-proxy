package com.vk.restapiproxy.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vk.restapiproxy.dto.Address;
import com.vk.restapiproxy.dto.Company;

public record UserResponse(@JsonProperty("id") long id,
                           @JsonProperty("name") String name,
                           @JsonProperty("username") String username,
                           @JsonProperty("email") String email,
                           @JsonProperty("address") Address address,
                           @JsonProperty("phone") String phone,
                           @JsonProperty("website") String website,
                           @JsonProperty("company") Company company) {
}
