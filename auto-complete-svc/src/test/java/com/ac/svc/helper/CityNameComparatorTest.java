package com.ac.svc.helper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ac.model.City;

public class CityNameComparatorTest {

	@Test
	public void should_pass_sorting() {
		List<City> list = new ArrayList<>();
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
		
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		
		//System.out.println(list);
		
		list.sort(new CityNameComparator("n"));
		
		//System.out.println(list);
		
		List<City> expectedSortedList = new ArrayList<>();
		expectedSortedList.add(c3);
		expectedSortedList.add(c2);
		expectedSortedList.add(c5);
		expectedSortedList.add(c4);
		expectedSortedList.add(c1);
		
		assertEquals(expectedSortedList, list);
	}

}
