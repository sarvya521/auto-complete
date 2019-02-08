package com.ac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "mst_city")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MstCity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String name;
}
