package com.memory.space.board;

import com.memory.space.member.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    final BoardRepository boardRepository = new BoardRepository();

    //게시판 메인 화면 이동
    @GetMapping
    public String main(Model model){
        logger.info("board main page");
        List<Board> boardList = boardRepository.findByAll();

        if(boardList.size() > 0){
            model.addAttribute("boardList",boardList);
        }
        logger.info(boardList.toString());
        return "/html/board/boardList.html";
    }


    //게시글 등록 이동
    @GetMapping("/registerForm")
    public String registerForm(){
        logger.info("board register Form!!");

        return "/html/board/boardForm.html";
    }

    //게시글 등록
    @PostMapping("/register")
    public String register(@RequestParam("title") String title,
                           @RequestParam("content") String content){
        logger.info("board register!!");

        String result = boardRepository.register(title,content,"admin","admin");
        if(result.equals("success")){
            logger.info("게시글 등록성공");
        }else{
            logger.info("등록 실패");
            return "/html/board/boardForm.html";
        }
        return "redirect:/html/board/boardList.html";
    }

}
