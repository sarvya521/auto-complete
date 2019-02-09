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
import com.ac.model.State;
import com.ac.svc.AutoCompleteService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
public class StateAutoCompleteServiceImplTest {

	@Autowired
	@Qualifier("stateAutoCompleteService")
	AutoCompleteService<State> stateAutoCompleteService;

	@Test
	public void should_pass_search() {
		List<State> list = stateAutoCompleteService.search("m");
		
		State s1 = new State();
		s1.setId(1);
		s1.setName("Maharashtra");
		
		State s2 = new State();
		s2.setId(3);
		s2.setName("Tamilnadu");
		
		State s3 = new State();
		s3.setId(7);
		s3.setName("Madhyapradesh");
		
		State s4 = new State();
		s4.setId(8);
		s4.setName("Aasaam");
		
		List<State> expectedSortedList = new ArrayList<>();
		expectedSortedList.add(s3);
		expectedSortedList.add(s1);
		expectedSortedList.add(s4);
		expectedSortedList.add(s2);
		
		assertEquals(expectedSortedList, list);
	}
	
	@Test
	public void should_fail_search() {
		List<State> list = stateAutoCompleteService.search("z");
		assertTrue(list.isEmpty());
	}
}