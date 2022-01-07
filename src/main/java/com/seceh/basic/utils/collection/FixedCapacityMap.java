package com.seceh.basic.utils.collection;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class FixedCapacityMap<K, V> {

    private final Map<K, V> fixedCapacityCache;

    public FixedCapacityMap(int capacity) {
        fixedCapacityCache = Collections.synchronizedMap(new LRUHashMap<>(capacity));
    }

    public V get(K key) {
        return fixedCapacityCache.get(key);
    }

    public void put(K key, V value) {
        fixedCapacityCache.put(key, value);
    }

    public int size() {
        return fixedCapacityCache.size();
    }

    static class LRUHashMap<K, V> extends LinkedHashMap<K, V> {

        private final int capacity;

        public LRUHashMap(int capacity) {
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return super.size() > capacity;
        }
    }
}