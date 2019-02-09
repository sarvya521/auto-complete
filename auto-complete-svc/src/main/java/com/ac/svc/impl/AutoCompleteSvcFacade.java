package com.ac.svc.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ac.constant.AcError;
import com.ac.constant.AutoCompleteComponent;
import com.ac.exception.AutoCompleteSvcException;
import com.ac.svc.AutoCompleteService;
import com.ac.util.LoggerUtilities;

/**
 * Hybrid of Facade and Abstract Factory design pattern.
 * Client does not need to know about each auto-complete service implemented in system.
 * Hence all auto-complete service components are loosely coupled to client.
 * 
 * <p> This class acts as a entry point to every implemented {@link com.ac.svc.AutoCompleteService}.
 * 
 * @author sarvesh
 */
@Component
public class AutoCompleteSvcFacade {
	
	private static final Logger LOGGER = LogManager.getLogger(AutoCompleteSvcFacade.class);
	
	@Autowired
	private ApplicationContext context;
	
	/**
	 * Entry point to every implemented
	 * {@link com.ac.svc.AutoCompleteService#search(String)}
	 * 
	 * @param type component for which keyword will be searched
	 * @param key  non-null keyword to search.
	 * @return java.util.List list of model objects representing given type
	 * @throws AutoCompleteSvcException If No {@link com.ac.svc.AutoCompleteService}
	 *                                  implementation found for given type
	 */
	@SuppressWarnings("rawtypes")
	public List search(String type, String key) throws AutoCompleteSvcException {
    	LOGGER.info(LoggerUtilities.getMessage("fetching auto complete results for {} with keyword {}", type, key));
        String acServiceQualifierName = AutoCompleteComponent.getService(type);
        AutoCompleteService acService = context.getBean(acServiceQualifierName, AutoCompleteService.class);
        if(acService == null) {
        	LOGGER.error(LoggerUtilities.getMessage("auto complete is not available for component {}", type));
            throw new AutoCompleteSvcException(AcError.INVALID_COMPONENT);
        }
        return acService.search(key);
    }
	
    /*@Autowired
    @Qualifier("cityAutoCompleteService")
    private AutoCompleteService<City> cityAutoCompleteService;

    @Autowired
    @Qualifier("stateAutoCompleteService")
    private AutoCompleteService<State> stateAutoCompleteService;

    public List search(String type, String key) throws AutoCompleteSvcException {
    	LOGGER.info(LoggerUtilities.getMessage("fetching auto complete results for {} with keyword {}", type, key));
        AutoCompleteComponent component = AutoCompleteComponent.get(type);
        switch (component) {
            case CITY:
                return cityAutoCompleteService.search(key);
            case STATE:
                return stateAutoCompleteService.search(key);
        }
        LOGGER.error(LoggerUtilities.getMessage("auto complete is not available for component {}", type));
        throw new AutoCompleteSvcException(AcError.INVALID_COMPONENT);
    }*/
}
