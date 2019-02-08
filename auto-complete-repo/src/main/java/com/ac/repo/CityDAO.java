package com.ac.repo;

import com.ac.dto.MstCity;

import java.util.List;

public interface CityDAO {

    List<MstCity> getCities(String keyword);

}
