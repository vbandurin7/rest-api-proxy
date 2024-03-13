package com.vk.restapiproxy.database.service;

import com.vk.restapiproxy.client.JsonPlaceholderClient;
import com.vk.restapiproxy.dto.request.UpdateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
public abstract class AbstractServiceTest<T> {
    private static final long ID = 1;

    private JsonPlaceholderClient<T> client;
    private Service<T> service;

    protected abstract Service<T> getService();
    protected abstract JsonPlaceholderClient<T> getClient();
    protected abstract Class<T> responseType();

    @BeforeEach
    void setup() {
        service = getService();
        client = getClient();
    }

    @AfterEach
    void clearCache() {
        service.delete(ID);
    }

    @Test
    void find_callTwice_fetchOnce() {
        // given
        AtomicInteger placeholderFetchCounter = new AtomicInteger();
        Optional<T> mockResponse = Optional.of(Mockito.mock(responseType()));
        when(client.fetch(ID)).thenAnswer(invocation -> {
            placeholderFetchCounter.incrementAndGet();
            return mockResponse;
        });

        // when
        service.find(ID);
        service.find(ID);

        // then
        assertEquals(1, placeholderFetchCounter.get());
    }

    @Test
    void findAll_callTwice_fetchOnce() {
        // given
        AtomicInteger placeholderFetchCounter = new AtomicInteger();
        Optional<List<T>> mockResponse = Optional.of(List.of(Mockito.mock(responseType())));
        when(client.fetchAll()).thenAnswer(invocation -> {
            placeholderFetchCounter.incrementAndGet();
            return mockResponse;
        });

        // when
        service.findAll();
        service.findAll();

        // then
        assertEquals(1, placeholderFetchCounter.get());
    }

    @Test
    void findAndUpdate_cacheUpdated_fetchTwice() {
        // given
        AtomicInteger placeholderRequestCounter = new AtomicInteger();
        Optional<T> mockResponse = Optional.of(Mockito.mock(responseType()));
        UpdateRequest updateRequest = Mockito.mock(UpdateRequest.class);
        when(client.fetch(ID)).thenAnswer(invocation -> {
            placeholderRequestCounter.incrementAndGet();
            return mockResponse;
        });
        when(client.update(updateRequest, ID)).thenAnswer(invocation -> {
            placeholderRequestCounter.incrementAndGet();
            return mockResponse;
        });

        // when
        service.find(ID);
        service.update(updateRequest, ID);
        service.find(ID);

        // then
        assertEquals(2, placeholderRequestCounter.get());
    }

    @Test
    void findAndDelete_cacheDeleted_fetchThreeTimes() {
        // given
        AtomicInteger placeholderRequestCounter = new AtomicInteger();
        Optional<T> mockResponse = Optional.of(Mockito.mock(responseType()));
        when(client.fetch(ID)).thenAnswer(invocation -> {
            placeholderRequestCounter.incrementAndGet();
            return mockResponse;
        });
        doAnswer(invocation -> {
            placeholderRequestCounter.incrementAndGet();
            return null;
        }).when(client).delete(ID);

        // when
        service.find(ID);
        service.delete(ID);
        service.find(ID);

        // then
        assertEquals(3, placeholderRequestCounter.get());
    }

    @Test
    void findAllAndUpdate_cacheDeleted_fetchThreeTimes() {
        // given
        AtomicInteger placeholderRequestCounter = new AtomicInteger();
        Optional<List<T>> fetchAllMockResponse = Optional.of(List.of(Mockito.mock(responseType())));
        Optional<T> updateMockResponse = Optional.of(Mockito.mock(responseType()));
        UpdateRequest updateRequest = Mockito.mock(UpdateRequest.class);
        when(client.fetchAll()).thenAnswer(invocation -> {
            placeholderRequestCounter.incrementAndGet();
            return fetchAllMockResponse;
        });
        when(client.update(updateRequest, ID)).thenAnswer(invocation -> {
            placeholderRequestCounter.incrementAndGet();
            return updateMockResponse;
        });

        // when
        service.findAll();
        service.update(updateRequest, ID);
        service.findAll();

        // then
        assertEquals(3, placeholderRequestCounter.get());
    }
}
