package ru.spbau.karlina.test1;

import ru.spbau.karlina.test1.list.List;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class HashMap<K, V> implements Map {
    private int hashTableMax = 4;
    private List<K, V>[] map;
    private int sizeOfPairs;

    public HashMap() {
        map = (List<K, V>[]) new Object[hashTableMax];
        makeInitialization(map, hashTableMax);
    }

    private void makeInitialization(List<K, V>[] array, int arraySize) {
        for (int i = 0; i < arraySize; ++i) {
            array[i] = new List<K, V>();
        }
    }

    private int getHash(K key) {
        return key.hashCode() % hashTableMax;
    }

    public int size() {
        return sizeOfPairs;
    }

    public boolean isEmpty() {
        if (sizeOfPairs == 0) {
            return true;
        }

        return false;
    }

    public boolean containsKey(Object o) {
        K key = (K)o;

        int hash = getHash(key);

        return map[hash].contains(key);
    }

    public boolean containsValue(Object o) {
        return false;
    }

    public Object get(Object o) {
        K key = (K)o;

        int hash = getHash(key);

        return map[hash].get(key);
    }

    public Object put(Object o, Object o2) {
        V value = (V)o;
        K key = (K)o2;

        int hash = getHash(key);

        if (map[hash].addToEnd(key, value) != null) {
            sizeOfPairs++;
            return value;
        }

        return null;
    }

    public Object remove(Object o) {
        K key = (K)o;

        int hash = getHash(key);

        V value = map[hash].remove(key);
        if (value != null) {
            sizeOfPairs--;
        }

        return value;
    }

    public void putAll(Map map) {
        Set<K> set = map.keySet();
        for (K key : set) {
            put(key, map.get(key));
        }
    }

    public void clear() {
       makeInitialization(map, hashTableMax);
       sizeOfPairs = 0;
    }

    public Set keySet() {
        Set<K> set = new Set<K>;

        for (List<K, V> list : map) {
            while (list.hasNext()) {
                    set.add(list.next());
                }
            }

        return set;
    }

    public Collection values() {
        Collection<V> collection = null;
        for (List<K, V> list : map) {
            while (list.hasNext()) {
                collection.add(list.get(list.next()));
            }
        }

        return collection;
    }

    public Set<Entry> entrySet() {
        return null;
    }
}
