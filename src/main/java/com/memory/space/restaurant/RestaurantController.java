package com.memory.space.restaurant;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(Model model, String menu){
        logger.info("식당 리스트 화면");
        logger.info(menu);
        //1.food 2.cafe 3.pub
        //model.addAttribute("menu", menu);
        return "restaurant/list";
    }

}


