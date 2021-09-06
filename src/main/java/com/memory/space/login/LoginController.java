package com.memory.space.login;

import com.memory.space.member.Member;
import com.memory.space.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class LoginController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberRepository memberRepository;

    //main 로그인 화면 이동
    @GetMapping
    public String goMain(){
        logger.info("로그인 page");
        return "main/login";
    }

    //로그인 기능 수행 -> 로그인 후 게시판이동
    @PostMapping("/loginGo")
    public String login(HttpSession session,
                        @RequestParam("id") String id,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes,
                        Model model){
        logger.info("login!!");
        logger.info("id:"+id+"    password:"+password);

        String validId = "";

//      List<Member> memberList = memberRepository.findByAll();
        Member member = memberRepository.findById(id,password);
        //멤버출력
        if(member==null){
            logger.info("등록 되지 않은 회원입니다.");
            redirectAttributes.addAttribute("login_fail","등록 되지 않은 회원입니다.");
            //model.addAttribute("login_fail","등록 되지 않은 회원입니다.");
            //model.addAttribute("err","asd");
            //logger.info("login_fail :::"+model.getAttribute("login_fail").toString());
            return "redirect:/main";
        }else{
            session.setAttribute("loginId",member.getId());
        }

        logger.info("로그인ID : "+(String)session.getAttribute("loginId"));

        return "redirect:/board";
        //redirect 안붙이면 405에러...왜일까....
    }

    //회원가입 페이지 이동
    @GetMapping("/register")
    public String registerForm(){
        logger.info("register Form!!");
        return "main/registerForm";
    }

    //회원가입 수행
    @PostMapping("/register")
    public String register(@RequestParam("id") String id,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password) {
        logger.info("register!!");
        String result = memberRepository.register(id, username, password);
        if(result == "success") {
            logger.info("회원가입 성공");
        }else if(result == "exist"){
            logger.info("이미 존재하는 아이디 입니다.");
            return "redirect:register";
        }else{
            logger.info("회원가입 실패");
            return "redirect:register";
        }
        return "redirect:main";
    }
}
