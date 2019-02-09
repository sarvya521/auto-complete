package com.ac.util;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Custom implementation of Apache commons LRUMap
 * {@link org.apache.commons.collections4.map.LRUMap}. 
 * 
 * <p>
 * All operations on this
 * cache object are synchronized over this LRUMap see
 * {@link SynchronizedInMemoryCache#cacheMap}. 
 * 
 * <p>
 * Implemented with optional async
 * process to clear cached objects which are not accessed in last
 * {@link SynchronizedInMemoryCache#timeToLive} milliseconds. Given
 * timerInterval value, every instance will create one Daemon thread
 * {@link Thread} which regularly triggers cleanup process after every
 * timerInterval seconds. see
 * {@link #SynchronizedInMemoryCache(long, long, int) } see {@link #cleanup()}
 * 
 * <p>All arguments to all task methods must be non-null.
 * 
 * @author sarvesh
 * 
 * @param <K> the type of keys maintained by this cache
 * @param <T> the type of mapped values
 */
public class SynchronizedInMemoryCache<K, T> {
	
	private static final Logger LOG = Logger.getLogger("SynchronizedInMemoryCacheLogger");

	/**
	 * time in milliseconds. see {@link #cleanup()} every cached object which is not
	 * accessed in last {@link SynchronizedInMemoryCache#timeToLive} milliseconds
	 * will be deleted from cache
	 */
	private long timeToLive;

	/**
	 * holds actual cached objects see
	 * {@link org.apache.commons.collections4.map.LRUMap}
	 */
	@SuppressWarnings("rawtypes")
	private LRUMap cacheMap;

	/**
	 * wrapper class created for holding value to be cached.
	 * {@link CacheObject#value} It also stores last accessed time
	 * {@link CacheObject#lastAccessed}
	 */
	protected class CacheObject {
		/**
		 * holds the last accessed time
		 */
		public long lastAccessed = System.currentTimeMillis();
		
		/**
		 * value to be associated with the specified key in cache
		 */
		public T value;

		/**
		 * @param value see {@link CacheObject#value}
		 * @throws IllegalArgumentException {@code value} is null
		 */
		protected CacheObject(final T value) {
			if(value == null) throw new IllegalArgumentException("value can not be null");
			this.value = value;
		}
	}

	/**
	 * Added to hide the default public constructor.
	 */
	@SuppressWarnings("unused")
	private SynchronizedInMemoryCache() {
	}

	/**
	 * @param timeToLive    Time in seconds. see
	 *                      {@link SynchronizedInMemoryCache#timeToLive} if value is
	 *                      0 then {@link #cleanup()} should be called manually to
	 *                      clear the cache
	 * @param timerInterval Time in seconds. Time interval to trigger cleanup
	 *                      process of cached objects see {@link #cleanup()}
	 * @param maxItems      no of max objects to be cached at a time
	 * @throws IllegalArgumentException if any of the parameter value is negative or
	 *                                  maxItems is less than 1
	 */
	@SuppressWarnings("rawtypes")
	public SynchronizedInMemoryCache(final long timeToLive, final long timerInterval, final int maxItems) {
		if (timeToLive < 0 || timerInterval < 0 || maxItems < 1) {
			throw new IllegalArgumentException(
					"Cache could not be initialized because of invalid constructor arguments");
		}
		if(timeToLive == 0) {
			LOG.warning("cache cleanup process will not be triggered automatically. Cache should be cleared manually.");
		}
		this.timeToLive = timeToLive * 1000;

		cacheMap = new LRUMap(maxItems);

		if (timeToLive > 0 && timerInterval > 0) {

			/*
			 * Daemon thread which will keep triggering cleanup process after every
			 * timerInterval seconds
			 */
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
	
    /**
     * Maps the specified key to the specified value in this table.
     * Neither the key nor the value can be null.
     *
     * <p>The value can be retrieved by calling the {@code get} method
     * with a key that is equal to the original key.
	 *
	 * @param key   key with which the specified value is to be associated
	 * @param value value to be associated with the specified key. This is value is
	 *              wrapped into instance of CacheObject see
	 *              {@link CacheObject}
	 * @throws NullPointerException if the specified key or value is null
	 */
	@SuppressWarnings("unchecked")
	public void put(final K key, final T value) {
		if (key == null || value == null) throw new NullPointerException();
		synchronized (cacheMap) {
			cacheMap.put(key, new CacheObject(value));
		}
	}

    /**
     * Returns the value to which the specified key is mapped..
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code key.equals(k)},
     * then this method returns {@code v}.
     * (There can be at most one such mapping.)
     * 
     * @see #put(Object, Object)
     *
     * @throws NullPointerException if the specified key is null
	 */
	@SuppressWarnings("unchecked")
	public T get(final K key) {
		if (key == null) throw new NullPointerException();
		synchronized (cacheMap) {
			CacheObject c = (CacheObject) cacheMap.get(key);

			if (c == null)
				return null;
			else {
				c.lastAccessed = System.currentTimeMillis();
				return c.value;
			}
		}
	}

    /**
     * Removes the key (and its corresponding value) from this cache.
     * This method does nothing if the key is not in the cache.
     *
     * @param  key the key that needs to be removed
     * @throws NullPointerException if the specified key is null
     */
	public void remove(final K key) {
		if (key == null) throw new NullPointerException();
		synchronized (cacheMap) {
			cacheMap.remove(key);
		}
	}
	
    /**
     * Returns the number of key-value mappings in this cache.
     *
     * @return the number of key-value mappings in this cache
     */
	public int size() {
		synchronized (cacheMap) {
			return cacheMap.size();
		}
	}

	/**
	 * clears the cache completely or partially based on {@link SynchronizedInMemoryCache#timeToLive} value
	 * If {@code timeToLive} is 0 then whole cache will be cleared
	 */
	public void cleanup() {
		if (timeToLive == 0) {
			manualCleanup();
			return;
		}

		autoCleanup();
	}
	
	/**
	 * clears the cache completely.
	 */
	private void manualCleanup() {
		synchronized (cacheMap) {
			cacheMap.clear();
		}
	}
	
	/**
	 * Clears cached objects which are not accessed in last
	 * {@link SynchronizedInMemoryCache#timeToLive} milliseconds
	 */
	@SuppressWarnings("unchecked")
	private void autoCleanup() {
		long now = System.currentTimeMillis();
		ArrayList<K> deleteKey = null;

		synchronized (cacheMap) {

			@SuppressWarnings("rawtypes")
			MapIterator itr = cacheMap.mapIterator();

			deleteKey = new ArrayList<K>((cacheMap.size() / 2) + 1);
			K key = null;
			CacheObject c = null;

			while (itr.hasNext()) {
				key = (K) itr.next();
				c = (CacheObject) itr.getValue();

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
