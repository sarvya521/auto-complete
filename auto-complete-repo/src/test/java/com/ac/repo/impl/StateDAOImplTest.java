package com.ac.repo.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ac.Application;
import com.ac.dto.MstState;
import com.ac.repo.StateDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class StateDAOImplTest {

	@Autowired
	private StateDAO stateDAO;
	
	@Test
	public void should_pass_getStates() {
		List<MstState> list = stateDAO.getStates("m");
		assertEquals(4, list.size());

		MstState s1 = new MstState();
		s1.setId(1);
		s1.setName("Maharashtra");

		MstState s2 = new MstState();
		s2.setId(3);
		s2.setName("Tamilnadu");

		MstState s3 = new MstState();
		s3.setId(7);
		s3.setName("Madhyapradesh");

		MstState s4 = new MstState();
		s4.setId(8);
		s4.setName("Aasaam");

		List<MstState> expectedList = new ArrayList<>();
		expectedList.add(s3);
		expectedList.add(s1);
		expectedList.add(s4);
		expectedList.add(s2);

		assertTrue(list.containsAll(expectedList));
	}

	@Test
	public void should_fail_getStates() {
		List<MstState> list = stateDAO.getStates("z");
		assertTrue(list.isEmpty());
	}

}
