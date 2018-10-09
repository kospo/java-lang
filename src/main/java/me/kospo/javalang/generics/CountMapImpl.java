package me.kospo.javalang.generics;

import java.util.HashMap;
import java.util.Map;

public class CountMapImpl<E> implements CountMap<E> {
    private final Map<E, Integer> map = new HashMap<>();

    @Override
    public void add(E o) {
        map.put(o, getCount(o) + 1);
    }

    @Override
    public int getCount(E o) {
        return map.getOrDefault(o, 0);
    }

    @Override
    public int remove(E o) {
        return map.remove(o);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void addAll(CountMap<? extends E> source) {
        for (Map.Entry<? extends E, Integer> entry : source.toMap().entrySet()) {
            map.put(entry.getKey(), getCount(entry.getKey()) + entry.getValue());
        }
    }

    @Override
    public Map<E, Integer> toMap() {
        return map;
    }

    @Override
    public void toMap(Map<? super E, Integer> destination) {
        destination.putAll(map);
    }
}
