package com.example.nextval.repository;

import com.example.nextval.entity.Movie;
import com.example.nextval.entity.Trailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrailerRepository extends JpaRepository<Trailer, Integer> {

    @Query(value = "SELECT * FROM next.trailer ORDER BY RAND() limit 1", nativeQuery = true)
    Trailer findTrailer();
}
