package com.imagine.another_arts.test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TestEntity {
    @Id @GeneratedValue
    Long id;
}
