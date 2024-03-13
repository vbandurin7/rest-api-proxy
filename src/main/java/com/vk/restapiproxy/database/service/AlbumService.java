package com.vk.restapiproxy.database.service;

import com.vk.restapiproxy.client.AlbumClient;
import com.vk.restapiproxy.dto.request.AddRequest;
import com.vk.restapiproxy.dto.request.UpdateRequest;
import com.vk.restapiproxy.dto.response.client.AlbumResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

public class AlbumService extends AbstractService<AlbumResponse> {
    private static final String ALBUM_CACHE_NAME = "album";
    private static final String ALBUMS_CACHE_NAME = "albums";

    public AlbumService(AlbumClient albumClient) {
        this.client = albumClient;
    }

    @Override
    @Cacheable(value = ALBUM_CACHE_NAME, key = "#id")
    public AlbumResponse find(long id) {
        return super.find(id);
    }

    @Override
    @Cacheable(ALBUMS_CACHE_NAME)
    public List<AlbumResponse> findAll() {
        return super.findAll();
    }

    @Override
    @CacheEvict(ALBUMS_CACHE_NAME)
    public <S extends AddRequest> AlbumResponse add(S addRequest) {
        return super.add(addRequest);
    }

    @Override
    @CachePut(value = ALBUM_CACHE_NAME, key = "#id")
    @CacheEvict(value = ALBUMS_CACHE_NAME, allEntries = true)
    public <S extends UpdateRequest> AlbumResponse update(S updateRequest, long id) {
        return super.update(updateRequest, id);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = ALBUMS_CACHE_NAME, allEntries = true),
                    @CacheEvict(value = ALBUM_CACHE_NAME, key = "#id")
            })
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public String entityName() {
        return "album";
    }
}
