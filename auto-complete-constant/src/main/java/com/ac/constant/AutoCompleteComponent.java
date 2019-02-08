package com.ac.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum AutoCompleteComponent {
    CITY("city"),
    STATE("state");

    private final String component;

    private static final Map<String, AutoCompleteComponent> LOOKUP = new HashMap<String, AutoCompleteComponent>();

    static {
        for (AutoCompleteComponent c : AutoCompleteComponent.values()) {
            LOOKUP.put(c.value(), c);
        }
    }

    private AutoCompleteComponent(String component) {
        this.component = component;
    }

    public String value() {
        return this.component;
    }

    public static AutoCompleteComponent get(String component) {
        return LOOKUP.get(component);
    }
}
