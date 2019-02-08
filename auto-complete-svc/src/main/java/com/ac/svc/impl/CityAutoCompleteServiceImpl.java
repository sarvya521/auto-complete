package com.ac.svc.impl;

import com.ac.dto.MstCity;
import com.ac.model.City;
import com.ac.repo.CityDAO;
import com.ac.svc.AutoCompleteService;
import com.ac.util.AutoCompleteInMemoryCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Qualifier("cityAutoCompleteService")
@Service
public class CityAutoCompleteServiceImpl implements AutoCompleteService<City> {

    @Autowired
    private CityDAO cityDAO;

    @Autowired
    private ApplicationContext context;

    private AutoCompleteInMemoryCache<String, List<City>> cache;

    @PostConstruct
    public void initCache() {
        String str_timeToLive, str_timerInterval, str_maxItems;
        str_timeToLive = context.getEnvironment().getProperty("cache.city.timeToLive");
        if (str_timeToLive != null) {
            str_timerInterval = context.getEnvironment().getProperty("cache.city.timerInterval");
            str_maxItems = context.getEnvironment().getProperty("cache.city.maxItems");
        } else {
            str_timeToLive = context.getEnvironment().getProperty("cache.default.timeToLive");
            str_timerInterval = context.getEnvironment().getProperty("cache.default.timerInterval");
            str_maxItems = context.getEnvironment().getProperty("cache.default.maxItems");
        }
        cache = new AutoCompleteInMemoryCache<>(Long.valueOf(str_timeToLive), Long.valueOf(str_timerInterval), Integer.valueOf(str_maxItems));
    }

    @Transactional
    @Override
    public List<City> search(final String key) {
        List<City> result = null;
        if ((result = cache.get(key)) != null) {
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
        cache.put(key, result);
        return result;
    }

    private void prepareModelFromDto(final City cityBean, final MstCity cityDto) {
        cityBean.setId(cityDto.getId());
        cityBean.setName(cityDto.getName());
    }
}
