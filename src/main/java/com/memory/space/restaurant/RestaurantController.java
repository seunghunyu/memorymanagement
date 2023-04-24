package com.memory.space.restaurant;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String list(@RequestParam("name") String menu, Model model){ //ajax로 넘겨받은 data 항목 menu로 받기
        logger.info("식당 리스트 화면");
        logger.info(menu);
        //1.food 2.cafe 3.pub
        //메뉴에따라 model 조합을 다르게해서 화면에 던지기
        //model.addAttribute("menu", menu);
        return "restaurant/list";
    }

}


