package com.memory.space.member;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository   //
@Transactional(readOnly = true)
@Slf4j
public class MemberRepository {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Member> memberRowMapper = new RowMapper<Member>() {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            logger.info("memberRowMapper");
            Member member = new Member();
            member.setId(rs.getString("id"));
            member.setUsername(rs.getString("username"));
            member.setPassword(rs.getString("password"));
            return member;
        }
    };
    //회원모두 불러오기
    public List<Member> findByAll(){
        List<Member> member = jdbcTemplate.query("SELECT * FROM MEMBER",memberRowMapper);
        if(member.size()==0){
            logger.info("member 가 없어요~");
        }else{
            logger.info(Integer.toString(member.size())+"  member 가 있어요~");
        }
        return member;
    }
    //특정회원 찾기
    public Member findById(String id,String password){
        Member member;
        try {
            member = jdbcTemplate.queryForObject("SELECT * FROM MEMBER WHERE ID = ? and password = ?", memberRowMapper, id, password);
            logger.info(member.toString());
        }catch(Exception e){
            logger.info("아이디 혹은 비밀번호가 틀렸습니다.");
            return null;
        }
        return member;
    }

    //회원가입
    public String register(String id,String username,String password){
        String sql = "INSERT INTO MEMBER VALUES(?, ?, ?)";
        try {
            int userCnt = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM MEMBER WHERE ID = ?",Integer.class,id);
            if(userCnt > 0){
                return "exist";
            }
            jdbcTemplate.update(sql, id, username, password);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            return "failed";
        }
    }

    //로그인이력
    public String insertHist(String id){
        String sel_nm_qry = "SELECT USERNAME FROM MEMBER WHERE ID = ? ";
        String inst_qry = "INSERT INTO LOGIN_HIST(ID, NAME, LOGIN_DT) VALUES(?, ?, sysdate)";
        try{
            String username = jdbcTemplate.queryForObject(sel_nm_qry,String.class,id);
            if(username.equals("")) {
                jdbcTemplate.update(inst_qry, id, username);
                return "success";
            }else{
                log.info("존재 하지 않는 회원입니다.");
                return "failed";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "failed";
        }
    }
}
