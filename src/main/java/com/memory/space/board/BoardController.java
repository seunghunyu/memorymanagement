package com.memory.space.board;

import com.memory.space.member.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BoardRepository boardRepository;

    //게시판 메인 화면 이동
    @GetMapping(value = {"","/{pageSeq}"})
    public String goBoard(@PathVariable(required = false) Integer pageSeq,Model model){
        logger.info("board main page");
        int pageNum;
        //pathValue 별 페이지 표시 필요
        if(pageSeq == null){
            pageNum = 1;
        }else{
            pageNum = pageSeq;
        }

        double totalCnt = boardRepository.boardCount();
        List<Board> boardList = boardRepository.findByAll(pageNum , (int)totalCnt);

        /*
        if(boardList.size() > 0){
            for(int i=0;i<boardList.size();i++){
                logger.info(boardList.get(i).toString());
            }
        }
        */

        String pageCnt = Integer.toString((int)Math.ceil(totalCnt / 5));
        model.addAttribute("boardList",boardList);
        model.addAttribute("pageCnt",pageCnt);

        logger.info(boardList.toString());
        logger.info("총 게시글 갯수 : " + Double.toString(totalCnt));
        logger.info("총 게시글 페이지 갯수 : " + pageCnt);
        logger.info("총 게시글 페이지 갯수 : " + Double.toString(totalCnt / 5));
        return "board/boardList";
    }


    //게시글 등록 이동
    @GetMapping("/registerForm")
    public String registerForm(){
        logger.info("board register Form!!");
        return "board/boardForm";
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
            return "board/boardForm";
        }
        return "redirect:/board";
    }

    @GetMapping("/detail/{boardSeq}")
    public String item(@PathVariable Long boardSeq, Model model) {
        logger.info("게시글 상세보기");
        Board board = boardRepository.findBySeq(boardSeq);
        model.addAttribute("board", board);
        return "board/board";
    }

}
