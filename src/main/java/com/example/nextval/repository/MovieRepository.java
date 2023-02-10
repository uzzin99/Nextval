package com.example.nextval.repository;

import com.example.nextval.entity.Member;
import com.example.nextval.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository <Movie, Integer> {

    /*@Query("SELECT b FROM next.type a, next.movie b where typename='로맨스' and a.id = b.id")
    List<Movie> contentList();*/

    /*@Query("SELECT m, t FROM Movie m LEFT JOIN m.Type t on m.id = t.id and t.typename = '로맨스'")
    List<Movie> contentList();*/
}
