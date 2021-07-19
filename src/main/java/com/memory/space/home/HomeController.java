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
}
