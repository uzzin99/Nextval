package com.example.nextval.service;

import com.example.nextval.entity.*;
import com.example.nextval.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NextService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private TrailerRepository trailerRepository;

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
    public List<Movie> contentAction() {

        return movieRepository.findAction();
       /* return movieRepository.findAll();*/

    }

    public List<Movie> contentRomance(){

        return movieRepository.findRomance();
    }

    public List<Movie> contentAnimation(){

        return movieRepository.findAnimation();
    }

    public List<Movie> searchList(String keyword){

        return movieRepository.findByTitleContaining(keyword);
    }


    //팝업
    public Movie nextPopup(Integer id) {

        return movieRepository.findById(id).get();
    }

    public void reviewWrite(Review review) {

        reviewRepository.save(review);
    }

    public List<Review> reviewList(Integer id) {

        return reviewRepository.selectReview(id);
    }

//    public List<Movie> typeList() {
//
//        return movieRepository.contentList();
//    }


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
