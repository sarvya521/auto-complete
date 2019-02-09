package com.ac.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SynchronizedInMemoryCacheTest {

	@Test
	public void testAddRemoveObjects() {
		/*
		 * Test with timeToLive = 200 second
		 * timerInterval = 5 second
		 * maxItems = 6
		 */
		SynchronizedInMemoryCache<String, String> cache = new SynchronizedInMemoryCache<>(200, 500, 6);
		
		cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");
        cache.put("Google", "Google");
        cache.put("Microsoft", "Microsoft");
        cache.put("IBM", "IBM");
        cache.put("Facebook", "Facebook");
 
        assertEquals(6, cache.size());

        cache.remove("IBM");
        
        assertEquals(5, cache.size());
        
        cache.put("Twitter", "Twitter");
        cache.put("SAP", "SAP");
        System.out.println("Two objects added but reached maxItems");
        
        assertEquals(6, cache.size());
	}
	
	@Test
	public void testExpiredCacheObjects() {
		/*
		 * Test with timeToLive = 1 second
		 * timerInterval = 1 second
		 * maxItems = 10
		 */
		SynchronizedInMemoryCache<String, String> cache = new SynchronizedInMemoryCache<>(1, 1, 10);
		
		cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");

        /*
         * Adding 3 seconds sleep.. Both above objects will be removed from
         * Cache because of timeToLiveInSeconds value
         */
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        System.out.println("Two objects are added but reached timeToLive");

        assertEquals(0, cache.size());
	}
	
	@Test
	public void testObjectsCleanupTime() {
		final int size = 500000;
		
		/*
		 * Test with timeToLiveInSeconds = 100 seconds
		 * timerIntervalInSeconds = 100 seconds
		 * maxItems = 500000
		 */
		SynchronizedInMemoryCache<String, String> cache = new SynchronizedInMemoryCache<>(100, 100, size);
		for (int i = 0; i < size; i++) {
            String value = Integer.toString(i);
            cache.put(value, value);
        }
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long start = System.currentTimeMillis();
        cache.cleanup();
        double finish = (double) (System.currentTimeMillis() - start) / 1000.0;
 
        System.out.println(String.format("Cleanup time for %d objects are %f seconds", size, finish) );
	}

}
