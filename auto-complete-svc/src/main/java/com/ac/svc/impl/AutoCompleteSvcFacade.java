package com.ac.svc.impl;

import com.ac.constant.AcError;
import com.ac.constant.AutoCompleteComponent;
import com.ac.exception.AutoCompleteSvcException;
import com.ac.svc.AutoCompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AutoCompleteSvcFacade {

    @Autowired
    @Qualifier("cityAutoCompleteService")
    private AutoCompleteService cityAutoCompleteService;

    @Autowired
    @Qualifier("stateAutoCompleteService")
    private AutoCompleteService stateAutoCompleteService;

    public List search(String type, String key) throws AutoCompleteSvcException {
        AutoCompleteComponent component = AutoCompleteComponent.get(type);
        switch (component) {
            case CITY:
                return cityAutoCompleteService.search(key);
            case STATE:
                return stateAutoCompleteService.search(key);
        }
        throw new AutoCompleteSvcException(AcError.INVALID_COMPONENT);
    }
}
