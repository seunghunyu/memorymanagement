package com.memory.space.home;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //main 로그인 화면 이동
    @GetMapping
    public String home(HttpSession session, Model model){
//    public String home(@ModelAttribute Object result){
        logger.info("default main home page entrance!");
//        if(model.asMap() != null){
        if(session.getAttribute("loginId") != null && !session.getAttribute("loginId").equals("")){
//            String result = (String)model.asMap().get("result");
//            String userId = (String)model.asMap().get("loginId");
//            String userName = (String)model.asMap().get("userName");
//            logger.info("result = {} , loginId = {} , userName = {}", result,userId,userName);
            logger.info("session result = {}, userId = {} , userName = {}",
                    session.getAttribute("result"), session.getAttribute("loginId"), session.getAttribute("userName"));
            model.addAttribute("result", session.getAttribute("result"));
            model.addAttribute("loginId", session.getAttribute("loginId"));
            model.addAttribute("userName", session.getAttribute("userName"));
        }
//        logger.info(pathVar);
//        logger.info(redirectAttributes.getAttribute("result") == null ? "null"  : redirectAttributes.getAttribute("result").toString());
//        logger.info(redirectAttributes.getFlashAttributes().get("result") == null ? "null"  : redirectAttributes.getFlashAttributes().get("result").toString());
        return "home/home";
    }


    @PostMapping("/loginHome")
    public String loginHome(RedirectAttributes redirectAttributes){

//        logger.info("result :::" + result);
        logger.info(redirectAttributes.getAttribute("result") == null ? "null"  : redirectAttributes.getAttribute("result").toString());
        logger.info(redirectAttributes.getFlashAttributes().get("result") == null ? "null"  : redirectAttributes.getFlashAttributes().get("result").toString());
        //if(redirectAttributes.getFlashAttributes() != null){
        if(redirectAttributes.getAttribute("result") != null){
            logger.info("redirectAttributes :::" + redirectAttributes.getFlashAttributes().get("result"));
//            logger.info("redirectAttributes :::" + redirectAttributes.getAttribute("result"));
        }else{
            logger.info("redirectAttributes not exist");
        }
        logger.info("loginHome page entrance!");
        return "home/home";
    }
    //로그인 사용자정보 변경 및 확인 페이지
    @GetMapping("/myInfo")
    public String myInfo(){
        logger.info("사용자정보 변경 및 확인 페이지로 이동!!");
        return "my/myInfo";
    }

    //로그인 사용자 비밀번호 변경
    @GetMapping("/myPassword")
    public String myPassword(){
        logger.info("비밀번호 변경 페이지로 이동!!");
        return "my/myPassword";
    }

    //about
    @GetMapping("/about")
    public String about(){
        logger.info("페이지 설명 페이지로 이동!!");
        return "home/about";
    }

    //contact
    @GetMapping("/contact")
    public String contact(){
        logger.info("계약 설명 페이지로 이동!!");
        return "home/contact";
    }
    //Login
    @GetMapping("/login")
    public String login(){
        logger.info("계약 설명 페이지로 이동!!");
        return "main/login";
    }
    //register
    @GetMapping("/register")
    public String register(){
        logger.info("계약 설명 페이지로 이동!!");
        return "main/registerForm";
    }
}
