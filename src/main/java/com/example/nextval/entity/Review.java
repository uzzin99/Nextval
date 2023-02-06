package com.example.nextval.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer reviewid;
    private Integer id;
    private Integer rating;
    private String content;
    private String userid;

    /*@ManyToOne
    @JoinColumn(name="id")
    private Movie movie;*/
}
