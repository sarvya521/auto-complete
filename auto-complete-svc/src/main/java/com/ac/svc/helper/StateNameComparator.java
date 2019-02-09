package com.ac.svc.helper;

import java.util.Comparator;

import com.ac.model.State;

/**
 * Custom implementation of java.util.Comparator. Compares
 * {@link com.ac.model.State} objects based on {@link #key} and
 * {@link com.ac.model.State#getName()}.
 * 
 * <p>
 * Final sort will have first all states whose {@code name} starts with given
 * {@link #key} followed by remaining states where {@link #key} lies in between
 * letters of the state {@code name}.
 * 
 * <p>
 * Examples:
 * <p>
 * &nbsp;&nbsp;Input: Initial order of states having name [Aasaam,
 * Madhyapradesh, Maharashtra, Tamilnadu, Bm, Maa, Mab]
 * <p>
 * &nbsp;&nbsp;Output: [Maa, Mab, Madhyapradesh, Maharashtra, Aasaam, Bm,
 * Tamilnadu]
 * 
 * @author sarvesh
 */
public class StateNameComparator implements Comparator<State> {

	private final String key;

	/**
	 * @param key non-null keyword used for comparing
	 *            {@link com.ac.model.State#getName()}
	 */
	public StateNameComparator(final String key) {
		if (key == null)
			throw new IllegalArgumentException("key can not be null");
		this.key = key.toLowerCase();
	}

	@Override
	public int compare(final State st1, final State st2) {
		String s1 = st1.getName();
		String s2 = st2.getName();
		if (s1.toLowerCase().startsWith(key) && s2.toLowerCase().startsWith(key)) {
			return s1.compareTo(s2);
		} else if (s1.toLowerCase().startsWith(key)) {
			return -1;
		} else if (s2.toLowerCase().startsWith(key)) {
			return 1;
		} else {
			return s1.compareTo(s2);
		}
	}

}
