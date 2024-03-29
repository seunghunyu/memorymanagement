package com.memory.space.board;

import ch.qos.logback.core.util.FileUtil;
import com.memory.space.member.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BoardRepository boardRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    //게시판 메인 화면 이동
    @GetMapping(value = {"","/{pageSeq}"})
    public String goBoard(HttpSession session,
                          @PathVariable(required = false) Integer pageSeq,
                          Model model){
        //session이 없을떄 메인으로 이동
        if(session.getAttribute("loginId")==null){
            logger.info("세션이 없습니다. 로그인해주세요.");
            return "main/login";
        }
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
                           @RequestParam("content") String content,
                           @RequestParam MultipartFile[] uploadFile,
                           Model model) throws IllegalStateException, IOException {
        logger.info("board register!!");

        String result = boardRepository.register(title,content,"admin","admin",uploadFile);
        if(result.equals("success")){
            logger.info("게시글 등록성공");
        }else{
            logger.info("등록 실패");
            return "board/boardForm";
        }
        if(uploadFile != null){
            if(fileUpload(uploadFile) == true){
                logger.info("파일업로드 실패");
            }else{
                logger.info("파일업로드 성공");
            }
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


    @PostMapping("/imgUpload")
    public String imgUpload(@RequestParam MultipartFile[] uploadFile, Model model) throws IllegalStateException, IOException {
        logger.info("이미지 업로드");
        String basePath = "C:\\Temp\\upload";
        MultipartFile[] fileList = uploadFile;
        for(int i = 0 ; i < fileList.length ; i++){
            logger.info("fileName:"+fileList[i].getOriginalFilename());
            String targetPath = basePath + "\\" + fileList[i].getOriginalFilename();

            Path copyOfLocation = Paths.get(basePath + File.separator + StringUtils.cleanPath(fileList[i].getOriginalFilename()));

            //File target  = new File(uploadPath,fileList[i].getOriginalFilename());

            Files.copy(fileList[i].getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);
        }
        return "redirect:/board";
    }

    private boolean fileUpload(MultipartFile[] uploadFile) throws IllegalStateException, IOException {
        logger.info("프로퍼티 upload path ::"+uploadPath);
        try {
            String basePath = "C:\\Temp\\upload";

            basePath = uploadPath;

            MultipartFile[] fileList = uploadFile;
            for (int i = 0; i < fileList.length; i++) {
                logger.info("fileName:" + fileList[i].getOriginalFilename());
                String targetPath = basePath + "\\" + fileList[i].getOriginalFilename();
                Path copyOfLocation = Paths.get(basePath + File.separator + StringUtils.cleanPath(fileList[i].getOriginalFilename()));
                logger.info("copyOfLocation :::::: "+copyOfLocation.toString());

                fileList[i].transferTo(new File(targetPath));

                //File target  = new File(uploadPath,fileList[i].getOriginalFilename());
                Files.copy(fileList[i].getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            return true;
        }catch(Exception e){
            return false;
        }
    }

    //내가 등록한 게시글로 이동
    @GetMapping("/myBoard")
    public String myBoard(Model model){
        int pageNum = 1;  //임시로 세팅
        double totalCnt = boardRepository.boardCount();
        List<Board> boardList = boardRepository.findByAll(pageNum , (int)totalCnt);

        String pageCnt = Integer.toString((int)Math.ceil(totalCnt / 5));
        model.addAttribute("boardList",boardList);
        logger.info("내가 등록한 게시글 목록으로 이동!!");
        return "my/myBoard";
    }

    //내가 등록한 게시글로 이동
    @GetMapping("/pictureBoard")
    public String pictureBoard(Model model){
        //int pageNum = 1;  //임시로 세팅
        //double totalCnt = boardRepository.boardCount();
        //List<Board> boardList = boardRepository.findByAll(pageNum , (int)totalCnt);

        //String pageCnt = Integer.toString((int)Math.ceil(totalCnt / 5));
        //model.addAttribute("boardList",boardList);
        logger.info("pictureBoard 상세보기 화면으로 이동!!");
        return "pictureBoard/board";
    }

    @GetMapping("/addContentList")
    public String addContentList(Model model){
        logger.info("만들기 리스트");
        return "board/addContentList";
    }

}
