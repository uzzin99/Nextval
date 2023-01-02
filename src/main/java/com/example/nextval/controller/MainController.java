package com.example.nextval.controller;

import com.example.nextval.entity.Member;
import com.example.nextval.service.NextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    private NextService nextService;

    //메인화면
    @GetMapping("/")
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
    @GetMapping("next/login")
    public String nextLogin() {

        return "login";
    }

    @PostMapping("next/loginpro")
    public String nextLoginPro(Member member, Model model, HttpServletRequest request) {
        System.out.println(member);

        member = nextService.loginUser(member.getUserid(),member.getUserpwd());
        if(member != null) {

            model.addAttribute("username",member.getUsername());

            return "content";
        }
        return "login";
    }

    @PostMapping("next/content")
    public String nextContent() {

        return "content";
    }
}
