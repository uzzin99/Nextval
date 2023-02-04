package com.example.nextval.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer reviewid;
    private Integer movieid;
    private Integer rating;
    private String content;
    private String userid;
}
