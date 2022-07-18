package com.memory.space.my;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
public class MyPageController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //main 로그인 화면 이동
    @GetMapping
    public String myPage(){
        logger.info("MyPage 화면");
        return "my/myPage";
    }
}
