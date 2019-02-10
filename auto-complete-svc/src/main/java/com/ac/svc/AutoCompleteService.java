package com.ac.svc;

import java.util.List;

/**
 * Generic auto complete service which is responsible for getting search results
 * for any component. Every service which is supposed to process auto complete
 * search; should implement this {@code interface}. Every service must have its
 * own Model class of type {@code <T>} so that service can process request for
 * given key see {@link #search(String)} and returns result as
 * {@link java.util.List} of {@code <T>}
 * 
 * <p>
 * Every implementing {@code Spring Service} must have unique {@code Qualifier}.
 * 
 * <p>
 * {@link com.ac.constant.AutoCompleteComponent} must have mapping of
 * {@code type} and it&#39;s service {@code Qualifier}.
 * 
 * <p>
 * This {@code type} should be exactly same as {@code PathVariable} of search
 * API.
 * 
 * @author sarvesh
 *
 * @param <T> Model class representing the type of auto complete service
 */
public interface AutoCompleteService<T> {

	/**
	 * Encapsulate core business logic of searching for a given key in database and
	 * returns the sorted result. {@code Database Entity} is queried for given key
	 * to get {@link java.util.List} list of {@code DTO} objects for specified
	 * {@code Database Entity}. This list of {@code DTO} objects is then copied to
	 * its related {@code List<T>} which is followed by sorting.
	 * 
	 * <p>
	 * Implementing service can have its custom {@link java.util.Comparator} for
	 * this sorting inside package {@link com.ac.svc.helper}
	 * 
	 * <p>
	 * Result {@code List<T>} can be stored in cache against the requested
	 * {@code key}.
	 * 
	 * <p>
	 * Caching mechanism can be implemented using
	 * {@link com.ac.util.SynchronizedInMemoryCache} which follows
	 * {@code Least Recently Used} algorithm in {@code synchronized} context
	 * 
	 * @param key non-null keyword to search.
	 * @return {@link java.util.List} list of {@code <T>}
	 */
	List<T> search(String key);
	
	/**
	 * Encapsulate core business logic of searching for a given key in database and
	 * returns the sorted result. {@code Database Entity} is queried for given key
	 * to get {@link java.util.List} sorted list of {@code DTO} objects for specified
	 * {@code Database Entity}. This sorted list of {@code DTO} objects is then copied to
	 * its related {@code List<T>}.
	 * 
	 * <p>
	 * Result {@code List<T>} can be stored in cache against the requested
	 * {@code key}.
	 * 
	 * <p>
	 * Caching mechanism can be implemented using
	 * {@link com.ac.util.SynchronizedInMemoryCache} which follows
	 * {@code Least Recently Used} algorithm in {@code synchronized} context
	 * 
	 * @param key non-null keyword to search.
	 * @param maxResult maximum number of suggestions needed 
	 * @return {@link java.util.List} list of {@code <T>}
	 */
	List<T> search(String key, Integer maxResult);
}
