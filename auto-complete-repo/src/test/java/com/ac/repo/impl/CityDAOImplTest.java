package com.ac.repo.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ac.Application;
import com.ac.dto.MstCity;
import com.ac.repo.CityDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
public class CityDAOImplTest {

	@Autowired
	private CityDAO cityDAO;

	@Test
	public void should_pass_getCities_without_limit() {
		List<MstCity> list = cityDAO.getCities("n");
		assertEquals(5, list.size());

		MstCity c1 = new MstCity();
		c1.setId(5);
		c1.setName("Pune");

		MstCity c2 = new MstCity();
		c2.setId(6);
		c2.setName("Noida");

		MstCity c3 = new MstCity();
		c3.setId(3);
		c3.setName("New Delhi");

		MstCity c4 = new MstCity();
		c4.setId(7);
		c4.setName("Chennai");

		MstCity c5 = new MstCity();
		c5.setId(4);
		c5.setName("Banglore");

		List<MstCity> expectedList = new ArrayList<>();
		expectedList.add(c1);
		expectedList.add(c2);
		expectedList.add(c3);
		expectedList.add(c4);
		expectedList.add(c5);

		assertTrue(list.containsAll(expectedList));
	}
	
	@Test
	public void should_pass_getCities_with_limit() {
		List<MstCity> list = cityDAO.getCities("n", 3);
		assertEquals(3, list.size());

		MstCity c2 = new MstCity();
		c2.setId(6);
		c2.setName("Noida");

		MstCity c3 = new MstCity();
		c3.setId(3);
		c3.setName("New Delhi");

		MstCity c5 = new MstCity();
		c5.setId(4);
		c5.setName("Banglore");

		List<MstCity> expectedSortedList = new ArrayList<>();
		expectedSortedList.add(c3);
		expectedSortedList.add(c2);
		expectedSortedList.add(c5);

		assertTrue(list.containsAll(expectedSortedList));
	}

	@Test
	public void should_fail_getStates() {
		List<MstCity> list = cityDAO.getCities("z");
		assertTrue(list.isEmpty());
	}

}
