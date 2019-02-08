package com.ac.constant;

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
