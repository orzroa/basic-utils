package com.seceh.basic.utils.collection;

import java.util.*;
import java.util.function.Predicate;

public class FixedCapacityList<E> {

    private final int capacity;
    private final List<E> fixedCapacityCache = Collections.synchronizedList(new LinkedList<>());

    public FixedCapacityList(int capacity) {
        this.capacity = capacity;
    }

    public void add(E e) {
        if (fixedCapacityCache.size() == capacity) {
            fixedCapacityCache.remove(0);
        }
        fixedCapacityCache.add(e);
    }

    public FixedCapacityList<E> remove(Predicate<E> p) {
        synchronized (fixedCapacityCache) {
            fixedCapacityCache.removeIf(p);
            return this;
        }
    }

    public int size() {
        return fixedCapacityCache.size();
    }

    public List<E> unwrap() {
        return fixedCapacityCache;
    }
}
