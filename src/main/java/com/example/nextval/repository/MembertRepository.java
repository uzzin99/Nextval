package com.example.nextval.repository;

import com.example.nextval.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MembertRepository extends JpaRepository <Member, Integer> {

    //Member selectUserInto(String userid, String userpwd);
    @Query("select u from Member u where u.userid=:userid and u.userpwd=:userpwd")
    Member selectUserInfo(@Param("userid")String nickname, @Param("userpwd")String pwd);

}
