package com.ac.constant;

/**
 * Enum holding all error messages.
 * 
 * @author sarvesh
 */
public enum AcError {
    INVALID_COMPONENT("auto complete is not available for this component");
	
    private final String msg;

    private AcError(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return this.msg;
    }
}
