package com.ac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "mst_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MstState {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String name;
}
