package com.ac.exception;

import com.ac.constant.AcError;

public class AutoCompleteSvcException extends RuntimeException {

    private AcError error;

    public AutoCompleteSvcException(String message) {
        super(message);
    }

    public AutoCompleteSvcException(AcError error) {
        super(error.msg());
    }

    public AutoCompleteSvcException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoCompleteSvcException(AcError error, Throwable cause) {
        super(error.msg(), cause);
    }

    public AutoCompleteSvcException(Throwable cause) {
        super(cause);
    }

    protected AutoCompleteSvcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
