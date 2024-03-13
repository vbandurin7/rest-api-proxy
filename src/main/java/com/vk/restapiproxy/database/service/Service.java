package com.vk.restapiproxy.database.service;

import com.vk.restapiproxy.dto.request.AddRequest;
import com.vk.restapiproxy.dto.request.UpdateRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Service<T> {
    T find(long id);
    List<T> findAll();
    <S extends AddRequest> T add(S addRequest);
    <S extends UpdateRequest> T update(S updateRequestBody, long id);
    void delete(long id);
    String entityName();
}
