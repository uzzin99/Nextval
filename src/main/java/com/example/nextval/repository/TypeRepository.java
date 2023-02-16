package com.example.nextval.repository;

import com.example.nextval.entity.Movie;
import com.example.nextval.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

    @Query("select m from Type m where m.id= :id")
    List<Type> selectType(@Param("id") Integer id);

    /*@Query("select m from Type m where m.typename='액션'")
    List<Type> listType();
*/
}
