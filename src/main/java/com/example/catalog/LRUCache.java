package com.example.catalog;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > this.capacity;
    }

    public V get(Object key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("Invalid key");
        }
        return super.get(key);
    }

    public void set(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Invalid key");
        }
        super.put(key, value);
    }

    public void clear() {
        super.clear();
    }

    public int size() {
        return super.size();
    }
}