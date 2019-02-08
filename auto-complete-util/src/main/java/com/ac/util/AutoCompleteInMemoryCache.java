package com.ac.util;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;

import java.util.ArrayList;

public class AutoCompleteInMemoryCache<K, T> {

    private long timeToLive;
    private LRUMap cacheMap;

    protected class AutoCompleteCacheObject {
        public long lastAccessed = System.currentTimeMillis();
        public T value;

        protected AutoCompleteCacheObject(T value) {
            this.value = value;
        }
    }

    public AutoCompleteInMemoryCache(long timeToLive, final long timerInterval, int maxItems) {
        this.timeToLive = timeToLive * 1000;

        cacheMap = new LRUMap(maxItems);

        if (timeToLive > 0 && timerInterval > 0) {

            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(timerInterval * 1000);
                        } catch (InterruptedException ex) {
                        }
                        cleanup();
                    }
                }
            });

            t.setDaemon(true);
            t.start();
        }
    }

    public void put(K key, T value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new AutoCompleteCacheObject(value));
        }
    }

    @SuppressWarnings("unchecked")
    public T get(K key) {
        synchronized (cacheMap) {
            AutoCompleteCacheObject c = (AutoCompleteCacheObject) cacheMap.get(key);

            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }

    public void remove(K key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    @SuppressWarnings("unchecked")
    public void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;

        synchronized (cacheMap) {
            MapIterator itr = cacheMap.mapIterator();

            deleteKey = new ArrayList<K>((cacheMap.size() / 2) + 1);
            K key = null;
            AutoCompleteCacheObject c = null;

            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (AutoCompleteCacheObject) itr.getValue();

                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }

        for (K key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }

            Thread.yield();
        }
    }
}
