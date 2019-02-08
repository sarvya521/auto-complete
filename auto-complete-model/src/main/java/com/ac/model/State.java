package com.ac.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class State {

    private Integer id;

    private String name;
}
