package com.vk.restapiproxy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Address(@JsonProperty("street") String street,
                      @JsonProperty("suite") String suite,
                      @JsonProperty("zipcode") String zipcode,
                      @JsonProperty("geo") Geo geo) {
}
