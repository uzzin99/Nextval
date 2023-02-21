package com.example.nextval.controller;

import com.example.nextval.entity.*;
import com.example.nextval.repository.ReviewRepository;
import com.example.nextval.repository.TrailerRepository;
import com.example.nextval.repository.TypeRepository;
import com.example.nextval.service.NextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private NextService nextService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private TrailerRepository trailerRepository;

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

        model.addAttribute("actionList",nextService.contentAction());

        model.addAttribute("romanceList",nextService.contentRomance());

        model.addAttribute("animationList",nextService.contentAnimation());

        String userid = (String) session.getAttribute("userid");

        model.addAttribute("username",session.getAttribute("username"));

        model.addAttribute("rand",trailerRepository.findTrailer());

        return "content";
    }

    @GetMapping("/next/search")
    public String nextSearch(String keyword, Model model) {

        List<Movie> searchList = nextService.searchList(keyword);

        model.addAttribute("searchList", searchList);

        System.out.println(searchList);

        System.out.println(searchList.size());

        return "search";
    }

    // 영화 팝업창
    @GetMapping("/next/popup") // next/popup?id=1
    public String nextPopup(Model model, Integer id, HttpServletRequest request) {

        HttpSession session=request.getSession();

        model.addAttribute("userid",session.getAttribute("userid"));

        model.addAttribute("movie",nextService.nextPopup(id));

        /*System.out.println(nextService.nextPopup(id));*/

        model.addAttribute("list",nextService.reviewList(id));

        if (reviewRepository.countReview(id) == 0){

            System.out.println("정보없음");

        } else {

            model.addAttribute("count",reviewRepository.countReview(id));

            model.addAttribute("avg",reviewRepository.avgReview(id));
        }

        model.addAttribute("type",typeRepository.selectType(id));

        /*System.out.println(typeRepository.selectType(id));*/

        return "popup";
    }

    @PostMapping("/next/popup/pro")
    public String nextPopupPro(@RequestBody Review review) {

        nextService.reviewWrite(review);

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
    public String nextBoardWritePro(Board board, Model model){

        nextService.boardWrite(board);

        model.addAttribute("message", "게시글 작성 완료");
        model.addAttribute("searchUrl", "/next/board/list");

        return "message";
    }

    @GetMapping("/next/board/list")
    public String nextBoardList(Model model, HttpServletRequest request,
                                @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                String searchKeyword) {

        HttpSession session=request.getSession();

        model.addAttribute("userid", session.getAttribute("userid"));

        Page<Board> list= null;

        if(searchKeyword == null) {
            list = nextService.boardList(pageable);
        } else  {
            list = nextService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

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

            model.addAttribute("message", "게시글 수정 완료");
            model.addAttribute("searchUrl", "/next/board/list");

        } else {

            model.addAttribute("message", "게시글 작성 실패");
            model.addAttribute("searchUrl", "/next/board/list");

            return "message";
        }

        return "message";
    }
}
