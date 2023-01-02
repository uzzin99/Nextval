package com.example.nextval.service;

import com.example.nextval.entity.Member;
import com.example.nextval.repository.MembertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NextService {

    @Autowired
    private MembertRepository membertRepository;
    public void write(Member member) {

        membertRepository.save(member);
    }

    public Member loginUser(String userid, String userpwd) {
        Member member = membertRepository.selectUserInfo(userid,userpwd);
        System.out.println(member);
        return member;
    }

}
