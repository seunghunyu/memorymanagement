package com.memory.space.member;

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
public class MemberRepository {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
    @Autowired
    public MemberRepository(DataSource datasource) {
        jdbcTemplate = new JdbcTemplate(datasource);
    }*/

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
        List<Member> member = jdbcTemplate.query("select * from member",memberRowMapper);
        if(member.size()==0){
            logger.info("member 가 없어요~");
        }else{
            logger.info(Integer.toString(member.size())+"  member 가 있어요~");
        }
        return member;
    }
    //특정회원 찾기
    public List<Member> findById(String id){
        List<Member> member = jdbcTemplate.query("select * from member where id = ?",memberRowMapper,id);
        logger.info(member.toString());
        return member;
    }

    //회원가입
    public String register(String id,String username,String password){
        String sql = "INSERT INTO MEMBER VALUES(?, ?, ?)";
        try {
            jdbcTemplate.update(sql, id, username, password);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            return "failed";
        }
    }
}
