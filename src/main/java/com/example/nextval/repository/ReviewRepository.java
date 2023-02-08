package com.example.nextval.repository;


import com.example.nextval.entity.Movie;
import com.example.nextval.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository  extends JpaRepository<Review, Integer> {

    @Query("select m from Review m where m.id= :id")
    List<Review> selectReview(@Param("id") Integer id);

    @Query("SELECT COUNT(u) FROM Review u WHERE u.id=:id")
    long countReview(@Param("id") Integer id);

    @Query("SELECT round(avg(u.rating),1) FROM Review u WHERE u.id=:id")
    double avgReview(@Param("id") Integer id);



}
