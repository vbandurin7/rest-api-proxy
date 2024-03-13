package com.vk.restapiproxy.database.service;

import com.vk.restapiproxy.client.JsonPlaceholderClient;
import com.vk.restapiproxy.dto.request.AddRequest;
import com.vk.restapiproxy.dto.request.UpdateRequest;
import com.vk.restapiproxy.exception.CreationFailedException;
import com.vk.restapiproxy.exception.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T> implements Service<T> {

    protected JsonPlaceholderClient<T> client;

    @Override
    public T find(long id) {
        Optional<T> response = client.fetch(id);
        if (response.isEmpty()) {
            throw new NotFoundException("%s with id %d doesn't exist".formatted(entityName(), id));
        }
        return response.get();
    }

    @Override
    public List<T> findAll() {
        Optional<List<T>> responseList = client.fetchAll();
        return responseList.orElse(Collections.emptyList());
    }

    @Override
    public <S extends AddRequest> T add(S addRequest) {
        Optional<T> response = client.add(addRequest);
        if (response.isEmpty()) {
            throw new CreationFailedException("Unable to create %s:".formatted(entityName()) + addRequest.toString());
        }
        return response.get();
    }

    @Override
    public <S extends UpdateRequest> T update(S updateRequest, long id) {
        Optional<T> response = client.update(updateRequest, id);
        if (response.isEmpty()) {
            throw new CreationFailedException("Unable to update %s:".formatted(entityName()) + updateRequest.toString());
        }
        return response.get();
    }

    @Override
    public void delete(long id) {
        client.delete(id);
    }

    public abstract String entityName();
}
