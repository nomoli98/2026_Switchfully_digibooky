package com.application.digibookyproject.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository <T>{
    private final Map<UUID, T> storedItems = new ConcurrentHashMap<>();

    public List<T> getAll() {
        return storedItems.values().stream().toList();
    }

    public Optional<T> get(UUID id) {
        return Optional.ofNullable(storedItems.get(id));
    }

    protected T saveWithId(UUID id, T item) {
        storedItems.put(id, item);
        return item;
    }
}
