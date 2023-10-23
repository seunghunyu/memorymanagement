package com.memory.space.login;

import com.memory.space.member.Member;
import com.memory.space.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.catalina.filters.RemoteIpFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class LoginController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberRepository memberRepository;

    //main 로그인 화면 이동
    @GetMapping
    public String goMain(){
        logger.info("login page");
        return "main/login";
    }

    //로그인 기능 수행 -> 로그인 후 게시판이동
    @PostMapping("/loginGo")
    public String login(HttpSession session,
                        @RequestParam("id") String id,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes,
                        Model model){
        logger.info("loginGo!!");
        logger.info("id:"+id+"    password:"+password);


//      List<Member> memberList = memberRepository.findByAll();
        Member member = memberRepository.findById(id,password);
        String validId = "";
        //멤버출력
        if(member==null){
            logger.info("@@@@not register member!!!!!");
//            redirectAttributes.addAttribute("result","login_fail"); //GET 방식으로 URI 뒤에 붙어서 전달
//            Map<String,String> resultMap = new HashMap<>();
//            resultMap.put("result", "login_fail");
//            redirectAttributes.addFlashAttribute(resultMap);
            redirectAttributes.addFlashAttribute("result","login_fail");  //POST 방식처럼 URI에 보여지지 않으며 휘발성
//            return "redirect:/home";
            return "redirect:/home";
//            return "home/home";
//            return "forward:/home/loginHome";
        }else{

            session.setAttribute("loginId",member.getId());
            session.setAttribute("userName",member.getUsername());
            session.setAttribute("result", "login_success");
            memberRepository.insertHist(id);
//            redirectAttributes.addFlashAttribute("result","login_success");
//            redirectAttributes.addFlashAttribute("loginId",member.getId());
//            redirectAttributes.addFlashAttribute("userName",member.getUsername());
            logger.info("@@@@LOGIN ID : "+(String)session.getAttribute("loginId"));
            return "redirect:/home";

        }
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
    //로그인 이력
    public String insertHist(String id){
        return "true";
    }
}
