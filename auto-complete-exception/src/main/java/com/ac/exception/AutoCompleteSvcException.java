package com.ac.exception;

import com.ac.constant.AcError;

import lombok.Getter;

/**
 * Custom RuntimeException specific only for Auto-Complete services
 * 
 * @author sarvesh
 */
public class AutoCompleteSvcException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * see {@link com.ac.constant.AcError}
	 */
	@Getter
	private AcError error;
	
	/**
	 * Added to hide the default public constructor.
	 */
	@SuppressWarnings("unused")
	private AutoCompleteSvcException() {}

	/**
	 * @see java.lang.RuntimeException#RuntimeException(String)
	 */
    public AutoCompleteSvcException(String message) {
        super(message);
    }

    /**
     * Calls {@code super constructor} @see java.lang.RuntimeException#RuntimeException(String)
     */
    public AutoCompleteSvcException(AcError error) {
        super(error.msg());
    }
    
    /**
	 * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
	 */
    public AutoCompleteSvcException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Calls {@code super constructor} @see java.lang.RuntimeException#RuntimeException(String, Throwable)
     */
    public AutoCompleteSvcException(AcError error, Throwable cause) {
        super(error.msg(), cause);
    }

    /**
	 * @see java.lang.RuntimeException#RuntimeException(Throwable)
	 */
    public AutoCompleteSvcException(Throwable cause) {
        super(cause);
    }

}
