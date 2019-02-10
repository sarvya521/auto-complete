package com.ac.repo;

import java.util.List;

import com.ac.dto.MstState;

/**
 * Repository to handle all operations on {@code table mst_state}
 * @see com.ac.dto.MstState
 * 
 * @author sarvesh
 */
public interface StateDAO {

	/**
	 * 
	 * @param keyword keyword to search in {@link com.ac.dto.MstState#getName()} 
	 * @return {@link java.util.List} of {@link com.ac.dto.MstState} whose {@code name} contains given {@code keyword}
	 */
    List<MstState> getStates(String keyword);

    /**
	 * 
	 * @param keyword keyword to search in {@link com.ac.dto.MstState#getName()} 
	 * @param maxResult maximum number of suggestions needed 
	 * @return {@link java.util.List} of {@link com.ac.dto.MstState} whose {@code name} contains given {@code keyword}
	 */
	List<MstState> getStates(String keyword, int maxResult);
}
