package com.example.nextval.controller;

import com.example.nextval.entity.Member;
import com.example.nextval.entity.Movie;
import com.example.nextval.service.NextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private NextService nextService;

    //메인화면
    @GetMapping("/next/main")
    public String main() {
        return "main";
    }

    //회원가입
    @GetMapping("/next/signin")
    public String nextSign() {

        return "signin";
    }

    @PostMapping("/next/signpro")
    public String nextSignPro(Member member, Model model) {

        nextService.write(member);

        model.addAttribute("message", "회원가입이 완료되었습니다..");

        model.addAttribute("searchUrl", "/mega/login");

        return "message";
    }

    //로그인
    @GetMapping("/next/login")
    public String nextLogin() {

        return "login";
    }

    @PostMapping("/next/loginpro")
    public String nextLoginPro(Member member, Model model, HttpServletRequest request) {

        HttpSession session =request.getSession();

        member = nextService.loginUser(member.getUserid(),member.getUserpwd());

        if(session.getAttribute("username") != null) {

//            model.addAttribute("username",member.getUsername());

            model.addAttribute("userid",session.getAttribute("userid"));
            model.addAttribute("id",session.getAttribute("id"));
            model.addAttribute("userpwd",session.getAttribute("userpwd"));
            model.addAttribute("username",session.getAttribute("username"));
            
            session.setAttribute("signIn", member);

            return "content";
        }

        return "login";
    }

    @GetMapping("/next/content")
    public String nextContent(Model model, Integer id) {

        model.addAttribute("list",nextService.contentList());


        return "content";
    }

    @GetMapping("/next/popup") // next/popup?id=1
    public String nextPopup(Model model, Integer id) {

        model.addAttribute("movie",nextService.nextPopup(id));

        return "popup";
    }


}
