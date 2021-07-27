package com.memory.space.login;

import com.memory.space.member.Member;
import com.memory.space.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String login(@RequestParam("id") String id,
                        @RequestParam("password") String password){
        logger.info("login!!");
        logger.info("id:"+id+"    password:"+password);

        HttpSession httpSession;

        String validId = "";

//      List<Member> memberList = memberRepository.findByAll();
        List<Member> members = memberRepository.findById(id);
        //멤버출력
        if(members.size()==0){
            logger.info("등록 되지 않은 회원입니다.");
            return "redirect:/main";
        }
        for(Member member : members){
            logger.info("id :: " +member.getId());
        }

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
                           @RequestParam("password") String password){
        logger.info("register!!");
        String result = memberRepository.register(id,username,password);
        if(result == "success"){
            logger.info("회원가입 성공");
        }else{
            logger.info("회원가입 실패");
        }
        return "redirect:main";
    }
}
