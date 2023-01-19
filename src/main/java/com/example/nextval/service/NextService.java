package com.example.nextval.service;

import com.example.nextval.entity.Member;
import com.example.nextval.entity.Movie;
import com.example.nextval.repository.MemberRepository;
import com.example.nextval.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NextService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MovieRepository movieRepository;

    public void write(Member member) {

        memberRepository.save(member);
    }

    public Member loginUser(String userid, String userpwd) {
        Member member = memberRepository.selectUserInfo(userid,userpwd);
        System.out.println(member);
        return member;
    }

    //슬라이드 이미지 처리
    public List<Movie> contentList() {

        return movieRepository.findAll();

    }
    //슬라이드 이미지 클릭 시 view 처리
    public Movie nextPopup(Integer id) {

        return movieRepository.findById(id).get();
    }


}
