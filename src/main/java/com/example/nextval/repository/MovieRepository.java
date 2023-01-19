package com.example.nextval.repository;

import com.example.nextval.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository <Movie, Integer> {

}
