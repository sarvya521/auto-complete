package com.ac.svc;

import java.util.List;

public interface AutoCompleteService<T> {

    List<T> search(String key);
}
