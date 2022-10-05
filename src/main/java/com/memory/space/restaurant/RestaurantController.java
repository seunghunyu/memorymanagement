package com.memory.space.restaurant;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/register")
    public String registerRestaurant(){
        logger.info("식당 등록하기 화면");
        return "restaurant/register";
    }
}


