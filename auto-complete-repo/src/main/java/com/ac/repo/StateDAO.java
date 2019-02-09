package com.ac.repo;

import com.ac.dto.MstState;

import java.util.List;

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

}
