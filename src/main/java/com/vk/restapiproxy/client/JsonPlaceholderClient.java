package com.vk.restapiproxy.client;

import com.vk.restapiproxy.dto.request.AddRequest;
import com.vk.restapiproxy.dto.request.UpdateRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JsonPlaceholderClient<T> {

    Optional<T> fetch(long id);
    Optional<List<T>> fetchAll();
    <S extends AddRequest> Optional<T> add(S addRequest);
    <S extends UpdateRequest> Optional<T> update(S updateRequestBody, long id);
    void delete(long id);
    Class<T> responseType();
}
