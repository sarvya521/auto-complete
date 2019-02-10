package com.ac.svc.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ac.Application;
import com.ac.model.City;
import com.ac.svc.AutoCompleteService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
public class CityAutoCompleteServiceImplTest {
	
	@Autowired
	@Qualifier("cityAutoCompleteService")
	AutoCompleteService<City> cityAutoCompleteService;

	@Test
	public void should_pass_search_without_limit() {
		List<City> list = cityAutoCompleteService.search("n");
		
		City c1 = new City();
		c1.setId(5);
		c1.setName("Pune");
		
		City c2 = new City();
		c2.setId(6);
		c2.setName("Noida");
		
		City c3 = new City();
		c3.setId(3);
		c3.setName("New Delhi");
		
		City c4 = new City();
		c4.setId(7);
		c4.setName("Chennai");
		
		City c5 = new City();
		c5.setId(4);
		c5.setName("Banglore");
		
		List<City> expectedSortedList = new ArrayList<>();
		expectedSortedList.add(c3);
		expectedSortedList.add(c2);
		expectedSortedList.add(c5);
		expectedSortedList.add(c4);
		expectedSortedList.add(c1);
		
		assertEquals(expectedSortedList, list);
	}
	
	@Test
	public void should_pass_search_with_limit() {
		List<City> list = cityAutoCompleteService.search("n", 3);
		City c2 = new City();
		c2.setId(6);
		c2.setName("Noida");
		
		City c3 = new City();
		c3.setId(3);
		c3.setName("New Delhi");
		
		City c5 = new City();
		c5.setId(4);
		c5.setName("Banglore");
		
		List<City> expectedSortedList = new ArrayList<>();
		expectedSortedList.add(c3);
		expectedSortedList.add(c2);
		expectedSortedList.add(c5);
		
		assertEquals(expectedSortedList, list);
	}
	
	@Test
	public void should_fail_search() {
		List<City> list = cityAutoCompleteService.search("z");
		assertTrue(list.isEmpty());
	}
}
