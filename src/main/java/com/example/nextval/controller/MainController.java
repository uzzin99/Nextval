package com.example.nextval.controller;

import com.example.nextval.entity.Board;
import com.example.nextval.entity.Member;
import com.example.nextval.entity.Movie;
import com.example.nextval.service.NextService;
import org.hibernate.type.StringNVarcharType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private NextService nextService;

    // 메인화면
    @GetMapping("/next/main")
    public String main() {
        return "main";
    }

    // 회원가입
    @GetMapping("/next/signin")
    public String nextSign() {

        return "signin";
    }

    @PostMapping("/next/signpro")
    public String nextSignPro(Member member, Model model) {

        nextService.write(member);

        model.addAttribute("message", "회원가입이 완료되었습니다..");

        model.addAttribute("searchUrl", "/next/login");

        return "message";
    }

    // 로그인
    @GetMapping("/next/login")
    public String nextLogin() {

        return "login";
    }

    @PostMapping("/next/loginpro")
    public String nextLoginPro(Member member, Model model, HttpServletRequest request) {

        HttpSession session=request.getSession();

        member = nextService.loginUser(member.getUserid(),member.getUserpwd());

        if(member != null) {

            session.setAttribute("userid",member.getUserid());
            session.setAttribute("username",member.getUsername());

            return "redirect:/next/content";
        }

        return "login";
    }

    // 컨텐츠
    @GetMapping("/next/content")
    public String nextContent(Model model, HttpServletRequest request) {

        HttpSession session=request.getSession();

        model.addAttribute("list",nextService.contentList());

        String userid = (String) session.getAttribute("userid");
        model.addAttribute("username",session.getAttribute("username"));

        return "content";
    }

    @GetMapping("/next/popup") // next/popup?id=1
    public String nextPopup(Model model, Integer id) {

        model.addAttribute("movie",nextService.nextPopup(id));

        return "popup";
    }

    // 게시판
    @GetMapping("/next/board/write")
    public String nextBoardWrite(Model model, HttpServletRequest request) {

        HttpSession session=request.getSession();

        model.addAttribute("userid",session.getAttribute("userid"));

        return "boardwrite";
    }

    @PostMapping("/next/boardwritepro")
    public String nextBoardWritePro(Board board){

        nextService.boardWrite(board);

        System.out.println(board);

        return "";
    }

    @GetMapping("/next/board/list")
    public String nextBoardList(Model model) {

        model.addAttribute("list", nextService.boardList());

        return "boardlist";
    }

    @GetMapping("/next/board/view") // next/boardview?id=1
    public String nextBoardView(Model model, Integer id) {

        model.addAttribute("board",nextService.boardView(id));

        return "boardview";
    }

    @GetMapping("/next/board/modify/{id}")
    public String nextBoardModify(@PathVariable("id") Integer id,
                                  Model model) {

        model.addAttribute("board", nextService.boardView(id));

        return "boardmodify";

    }

    @PostMapping("/next/board/update/{id}")
    public String nextBoardUpdate(@PathVariable("id") Integer id, Board board,
                                  Model model, HttpServletRequest request) {

        HttpSession session=request.getSession();

        if (session.getAttribute("userid").equals(board.getUserid())) {

            Board boardTemp = nextService.boardView(id);

            boardTemp.setTitle(board.getTitle());
            boardTemp.setContent(board.getContent());
            boardTemp.setDate(board.getDate());
            boardTemp.setUserid(board.getUserid());

            nextService.boardWrite(boardTemp);

        } else {

            System.out.println("실패");

            return "";
        }

        return "redirect:/next/board/list";
    }
}
