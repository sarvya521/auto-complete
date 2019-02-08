package com.ac.repo;

import com.ac.dto.MstState;

import java.util.List;

public interface StateDAO {

    List<MstState> getStates(String keyword);

}
