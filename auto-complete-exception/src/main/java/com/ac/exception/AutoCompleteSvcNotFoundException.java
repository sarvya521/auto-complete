package com.ac.exception;

import com.ac.constant.AcError;

/**
 * Custom RuntimeException specific only for Auto-Complete services
 * 
 * @author sarvesh
 */
public class AutoCompleteSvcNotFoundException extends AutoCompleteSvcException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see java.lang.RuntimeException#RuntimeException(String)
	 */
    public AutoCompleteSvcNotFoundException(String message) {
        super(message);
    }

    /**
     * Calls {@code super constructor} @see java.lang.RuntimeException#RuntimeException(String)
     */
    public AutoCompleteSvcNotFoundException(AcError error) {
        super(error.msg());
    }
    
    /**
	 * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
	 */
    public AutoCompleteSvcNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Calls {@code super constructor} @see java.lang.RuntimeException#RuntimeException(String, Throwable)
     */
    public AutoCompleteSvcNotFoundException(AcError error, Throwable cause) {
        super(error.msg(), cause);
    }

    /**
	 * @see java.lang.RuntimeException#RuntimeException(Throwable)
	 */
    public AutoCompleteSvcNotFoundException(Throwable cause) {
        super(cause);
    }

}
