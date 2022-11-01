package com.memory.space.home;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //main 로그인 화면 이동
    @GetMapping
    public String home(){
        logger.info("home 화면");
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
}
