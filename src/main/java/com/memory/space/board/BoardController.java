package com.memory.space.board;

import com.memory.space.member.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    final BoardRepository boardRepository = new BoardRepository();

    //게시판 메인 화면 이동
    @GetMapping
    public String main(){
        logger.info("board main page");
        List<Board> boardList = boardRepository.findByAll();

        return "/html/board/boardList.html";
    }

    //게시글 등록 이동
    @GetMapping("/register")
    public String registerForm(){
        logger.info("board register Form!!");
        return "/html/board/boardForm.html";
    }

}
