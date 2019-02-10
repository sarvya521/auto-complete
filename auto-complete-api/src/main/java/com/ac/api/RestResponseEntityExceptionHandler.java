package com.ac.api;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ac.exception.AutoCompleteSvcException;
import com.ac.exception.AutoCompleteSvcNotFoundException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOGGER = LogManager.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler({ AutoCompleteSvcNotFoundException.class })
	public ResponseEntity<Object> handleResourceNotFound(AutoCompleteSvcNotFoundException ax,
			NativeWebRequest request) {
		LOGGER.error(ax);
		@SuppressWarnings("rawtypes")
		Map responseBody = Collections.singletonMap("error",
				"auto-complete search is not implemented for requested type " + getPathVariables(request).get("type"));
		return handleExceptionInternal(ax, responseBody, new HttpHeaders(), HttpStatus.NOT_IMPLEMENTED, request);
	}

	@ExceptionHandler({ AutoCompleteSvcException.class })
	public ResponseEntity<Object> handleValidations(AutoCompleteSvcException ax, WebRequest request) {
		LOGGER.error(ax);
		@SuppressWarnings("rawtypes")
		Map responseBody = Collections.singletonMap("error",
				ax.getError() != null ? ax.getError().msg() : ax.getMessage());
		return handleExceptionInternal(ax, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getPathVariables(NativeWebRequest request) {
		HttpServletRequest httpServletRequest = request.getNativeRequest(HttpServletRequest.class);
		return (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
	}
}
