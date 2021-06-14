package com.memory.space.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String main(){
        logger.info("main page입니다.");
        return "html/main/login.html";
    }
}
