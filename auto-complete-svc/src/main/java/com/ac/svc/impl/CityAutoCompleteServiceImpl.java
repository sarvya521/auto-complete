package com.ac.svc.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ac.dto.MstCity;
import com.ac.model.City;
import com.ac.repo.CityDAO;
import com.ac.svc.AutoCompleteService;
import com.ac.svc.helper.CityNameComparator;
import com.ac.util.SynchronizedInMemoryCache;

/**
 * Implementation of {@link com.ac.svc.AutoCompleteService} for type City.
 * See {@link com.ac.model.City}
 * 
 * @author sarvesh
 */
@Service
@Qualifier("cityAutoCompleteService")
public class CityAutoCompleteServiceImpl implements AutoCompleteService<City> {
	
	private static final Logger LOGGER = LogManager.getLogger(CityAutoCompleteServiceImpl.class);

    @Autowired
    private CityDAO cityDAO;

    @Autowired
    private ApplicationContext context;

    /**
     * See {@link com.ac.util.SynchronizedInMemoryCache}
     */
    private SynchronizedInMemoryCache<String, List<City>> cache;

	/**
	 * Initializes cache for storing auto-complete search results {@code List<City>}
	 * against search keyword {@code key}. Caching mechanism is implemented using
	 * {@link com.ac.util.SynchronizedInMemoryCache}. Settings needed for
	 * initializing {@link com.ac.util.SynchronizedInMemoryCache} are configured in
	 * {@code environment properties}. 
	 * 
	 * <p>
	 * Caching can only be activated by all three mandatory settings
	 * {@code cache.city.timeToLive}, {@code cache.city.timerInterval}, and
	 * {@code cache.city.maxItems}.
	 * 
	 * <p>
	 * If city specific cache settings are not found
	 * in {@code environment properties}, then default cache settings are used.
	 * Default settings are {@code cache.default.timeToLive}, {@code cache.default.timerInterval}, and
	 * {@code cache.default.maxItems}
	 */
    @PostConstruct
    public void initCache() {
		LOGGER.info("initializing cache for city auto-complete results");
		String str_timeToLive, str_timerInterval, str_maxItems;
		str_timeToLive = context.getEnvironment().getProperty("cache.city.timeToLive");
		if (str_timeToLive != null) {
			str_timerInterval = context.getEnvironment().getProperty("cache.city.timerInterval");
			str_maxItems = context.getEnvironment().getProperty("cache.city.maxItems");
		} else {
			LOGGER.info(
					"city specific cache settings not found in application.properties. Applying default cache settings");
			str_timeToLive = context.getEnvironment().getProperty("cache.default.timeToLive");
			str_timerInterval = context.getEnvironment().getProperty("cache.default.timerInterval");
			str_maxItems = context.getEnvironment().getProperty("cache.default.maxItems");
		}
		if (str_timeToLive == null || str_timerInterval == null || str_maxItems == null) {
			LOGGER.error("deafult cache settings not found");
			return;
		}
		LOGGER.info("cache for city auto-complete results initialized");
		cache = new SynchronizedInMemoryCache<>(Long.valueOf(str_timeToLive), Long.valueOf(str_timerInterval),
				Integer.valueOf(str_maxItems));
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * {@link com.ac.svc.helper.CityNameComparator} is custom {@link java.util.Comparator} used for sorting.
     * 
     * @return {@link java.util.List} list of {@link com.ac.model.City}
     */
    @Transactional
    @Override
    public List<City> search(final String key) {
		List<City> result = null;
		if (cache != null && (result = cache.get(key)) != null) {
			LOGGER.info("returning auto-complete city cache result for key {}", key);
			return result;
		}
		List<MstCity> cities = cityDAO.getCities(key);
		result = cities.stream().map(new Function<MstCity, City>() {
			@Override
			public City apply(MstCity mstCity) {
				City city = new City();
				prepareModelFromDto(city, mstCity);
				return city;
			}
		}).collect(Collectors.toList());

		result.sort(new CityNameComparator(key));

		if (cache != null) {
			cache.put(key, result);
		}
		return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @return {@link java.util.List} list of {@link com.ac.model.City}
     */
    @Transactional
	@Override
	public List<City> search(String key, Integer maxResult) {
    	List<City> result = null;
		if (cache != null && (result = cache.get(key)) != null) {
			LOGGER.info("returning auto-complete city cache result for key {}", key);
			return result.size() > maxResult ? result.subList(0, maxResult) : result;
		}
		List<MstCity> cities = cityDAO.getCities(key, maxResult);
		result = cities.stream().map(new Function<MstCity, City>() {
			@Override
			public City apply(MstCity mstCity) {
				City city = new City();
				prepareModelFromDto(city, mstCity);
				return city;
			}
		}).collect(Collectors.toList());
		
		if (cache != null) {
			cache.put(key, result);
		}
		return result;
	}
    
    private void prepareModelFromDto(final City cityBean, final MstCity cityDto) {
        cityBean.setId(cityDto.getId());
        cityBean.setName(cityDto.getName());
    }
}
