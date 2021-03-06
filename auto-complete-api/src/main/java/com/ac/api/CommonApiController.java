package com.ac.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ac.svc.impl.AutoCompleteSvcFacade;
import com.ac.util.LoggerUtilities;

/**
 * Bundles all necessary APIs for auto-complete service.
 * 
 * @author sarvesh
 */
@RestController
public class CommonApiController {

	private static final Logger LOGGER = LogManager.getLogger(CommonApiController.class);

	/**
	 * @see com.ac.svc.impl.AutoCompleteSvcFacade
	 */
	@Autowired
	private AutoCompleteSvcFacade autoCompleteSvcFacade;

	@GetMapping("/api/ping")
	public String ping() {
		return "pong!";
	}

	/**
	 * @param type auto-complete component
	 * @param key  non-null keyword to search
	 * @param maxResult maximum number of suggestions needed 
	 * @return {@link java.util.List} list of {@code model} objects representing
	 *         {@code type}
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/api/search/{type}")
	@CrossOrigin(origins = "http://localhost:4200")
	public List search(@PathVariable(value = "type") String type, @RequestParam(value = "start") String key,
			@RequestParam(value = "atmost", required = false) Integer maxResult) {
		LOGGER.info(LoggerUtilities.getMessage("serving auto-complete request for {} with keyword {} and limit {}", type, key, maxResult));
		return autoCompleteSvcFacade.search(type, key, maxResult);
	}
}
