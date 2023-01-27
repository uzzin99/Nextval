package com.example.nextval.service;

import com.example.nextval.entity.Board;
import com.example.nextval.entity.Member;
import com.example.nextval.entity.Movie;
import com.example.nextval.repository.BoardRepository;
import com.example.nextval.repository.MemberRepository;
import com.example.nextval.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NextService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BoardRepository boardRepository;

    //회원가입,로그인
    public void write(Member member) {

        memberRepository.save(member);
    }

    public Member loginUser(String userid, String userpwd) {
        Member member = memberRepository.selectUserInfo(userid,userpwd);
        //System.out.println(member);
        return member;
    }

    //컨텐츠
    public List<Movie> contentList() {

        return movieRepository.findAll();

    }

    public Movie nextPopup(Integer id) {

        return movieRepository.findById(id).get();
    }

    //게시판
    public void boardWrite(Board board) {

        boardRepository.save(board);
    }

    public Page<Board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();
    }

}
