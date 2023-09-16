package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        int key = model.getId();
        return memory.computeIfPresent(key, (k, v) -> {
            if (model.getVersion() != memory.get(key).getVersion()) {
                throw new OptimisticException("Version is not correct");
            }
            Base newModel = new Base(key, model.getVersion() + 1);
            newModel.setName(model.getName());
            return newModel;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Map<Integer, Base> getCache() {
        return Map.copyOf(memory);
    }
}