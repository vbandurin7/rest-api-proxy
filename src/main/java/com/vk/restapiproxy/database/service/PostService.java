package com.vk.restapiproxy.database.service;

import com.vk.restapiproxy.client.PostClient;
import com.vk.restapiproxy.dto.request.AddRequest;
import com.vk.restapiproxy.dto.request.UpdateRequest;
import com.vk.restapiproxy.dto.response.client.PostResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

public class PostService extends AbstractService<PostResponse> {
    private static final String POST_CACHE_NAME = "post";
    private static final String POSTS_CACHE_NAME = "posts";

    public PostService(PostClient postClient) {
        this.client = postClient;
    }

    @Override
    @Cacheable(value = POST_CACHE_NAME, key = "#id")
    public PostResponse find(long id) {
        return super.find(id);
    }

    @Override
    @Cacheable(POSTS_CACHE_NAME)
    public List<PostResponse> findAll() {
        return super.findAll();
    }

    @Override
    @CacheEvict(POSTS_CACHE_NAME)
    public <S extends AddRequest> PostResponse add(S addRequest) {
        return super.add(addRequest);
    }

    @Override
    @CachePut(value = POST_CACHE_NAME, key = "#id")
    @CacheEvict(value = POSTS_CACHE_NAME, allEntries = true)
    public <S extends UpdateRequest> PostResponse update(S updateRequest, long id) {
        return super.update(updateRequest, id);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = POSTS_CACHE_NAME, allEntries = true),
                    @CacheEvict(value = POST_CACHE_NAME, key = "#id")
            })
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public String entityName() {
        return "post";
    }
}
