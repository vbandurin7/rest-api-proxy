package com.vk.restapiproxy.client;

import com.vk.restapiproxy.dto.request.AddRequest;
import com.vk.restapiproxy.dto.request.UpdateRequest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

public abstract class AbstractClient<T> implements JsonPlaceholderClient<T> {
    protected WebClient webClient;
    protected String ENDPOINT;

    protected void init(String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    @Override
    public Optional<T> fetch(long id) {
        return webClient.get()
                .uri(ENDPOINT + "/{id}", id)
                .retrieve()
                .bodyToMono(responseType())
                .blockOptional();
    }

    @Override
    public Optional<List<T>> fetchAll() {
        return webClient.get()
                .uri(ENDPOINT)
                .retrieve()
                .bodyToFlux(responseType())
                .collectList()
                .blockOptional();
    }

    @Override
    public <S extends AddRequest> Optional<T> add(S addRequest) {
        return webClient.post()
                .uri(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addRequest)
                .retrieve()
                .bodyToMono(responseType())
                .blockOptional();
    }

    @Override
    public <S extends UpdateRequest> Optional<T> update(S updateRequestBody, long id) {
        return webClient.put()
                .uri(ENDPOINT + "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateRequestBody)
                .retrieve()
                .bodyToMono(responseType())
                .blockOptional();
    }

    @Override
    public void delete(long id) {
        webClient.delete()
                .uri(ENDPOINT + "/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public abstract Class<T> responseType();
}
