package com.memory.space.board;

import com.memory.space.member.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class BoardRepository {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Board> boardRowMapper = new RowMapper<Board>() {
        @Override
        public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
            Board board = new Board();

            Long seq;
            String title;
            String desc;
            String id;
            String username;
            String updateDate;
            String fileName;

            board.setSeq(rs.getLong("seq"));
            board.setTitle(rs.getString("title"));
            board.setContent(rs.getString("content"));
            board.setId(rs.getString("id"));
            board.setUsername(rs.getString("username"));
            board.setUpdateDate(rs.getString("updateDate"));
            board.setFileName(rs.getString("file_Nm"));

            return board;
        }
    };
    //게시글모두 불러오기
    public List<Board> findByAll(Integer startNum, int totalCnt){
        int pageBoardCnt = 5; // 한 페이지에 5개씩 보여야함
        int start;
        int end;


        if(startNum == null) {
            end = pageBoardCnt;
            start = 1;
        }else {
            end  = startNum * pageBoardCnt;
            start = end - pageBoardCnt+1;
        }
        logger.info("시작 rownum:"+Integer.toString(start)+"/종료 rownum:"+Integer.toString(end));
        String qry = "";
        qry += " SELECT * ";
        qry += " FROM ";
        qry += " ( ";
        qry += " SELECT /*+ INDEX(b PK1) */ ";
        qry += "   ROWNUM AS RNUM, b.* ";
        qry += "  FROM BOARD b ";
        qry += " WHERE ROWNUM <= "+ Integer.toString(end);
        qry += " ) ";
        qry += " WHERE " + Integer.toString(start) +"<= RNUM ";
        List<Board> board = jdbcTemplate.query(qry,boardRowMapper);
        //List<Board> board = jdbcTemplate.queryForList(qry,Board.class);

        return board;
    }
    //게시글 갯수 가져오기
    public double boardCount(){
        Double totalCnt = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM BOARD ORDER BY SEQ DESC",Double.class);
        return totalCnt;
    }

    //게시글 등록
    public String register(String title, String content, String id, String username, MultipartFile[] uploadFile){
        String fileNm = "";
        //upload파일이 있으면 파일명조합
        if(uploadFile.length > 0){
            for(int i = 0 ;  i < uploadFile.length ; i++){
                fileNm = fileNm + ";" + uploadFile[i].getOriginalFilename();
            }
            fileNm = fileNm.substring(1);
        }
        String sql = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, ? TO_CHAR(SYSDATE,'YYYYMMDD'))";
        try {
            jdbcTemplate.update(sql, title, content, id, username, fileNm);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            return "failed";
        }
    }
    //게시글 상세 정보 가져오기
    public Board findBySeq(long boardSeq){
        logger.info("게시글 상세정보 가져오기");
        Board board = jdbcTemplate.queryForObject("SELECT * FROM BOARD WHERE SEQ = "+ Long.toString(boardSeq),boardRowMapper);
        return board;
    }
}
