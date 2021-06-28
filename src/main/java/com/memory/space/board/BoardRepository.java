package com.memory.space.board;

import com.memory.space.member.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
            logger.info("memberRowMapper");
            Board board = new Board();

            Long seq;
            String title;
            String desc;
            String id;
            String username;
            String updateDate;

            board.setSeq(rs.getLong("seq"));
            board.setTitle(rs.getString("title"));
            board.setContent(rs.getString("content"));
            board.setId(rs.getString("id"));
            board.setUsername(rs.getString("username"));
            board.setUpdateDate(rs.getString("updateDate"));

            return board;
        }
    };
    //게시글모두 불러오기
    public List<Board> findByAll(){
        List<Board> board = jdbcTemplate.query("SELECT * FROM BOARD ORDER BY SEQ DESC",boardRowMapper);
        return board;
    }

    //게시글 등록
    public String register(String title,String content,String id,String username){
        String sql = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, TO_CHAR(SYSDATE,'YYYYMMDD'))";
        try {
            jdbcTemplate.update(sql, title, content, id, username);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            return "failed";
        }
    }
}
