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

    /*@Query(value = " SELECT a.*, b.* " +
            " FROM next.review a, next.movie b " +
            " WHERE a.id = :#{movie.id} ", nativeQuery = true)
    List<Review> selectReview(@Param(value = id)Movie movie);*/

    @Query("select m from Review m where m.id= :id")
    List<Review> selectReview(@Param("id") Integer id);

    /*List<Review> selectReview(Integer id);*/

    /*@Query
            (value = "select m " +
                "from Review m " +
                "where m.id = :id")
    List<Review> selectReview(@Param("id") Integer id);*/
}
