package com.memory.space.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
    //게시판 번호,제목,설명,작성자,작성자명,수정일자
    Long seq;
    String title;
    String content;
    String id;
    String username;
    String updateDate;

    public Board(){}

    public Board(Long seq, String title, String content, String id, String username, String updateDate) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.id = id;
        this.username = username;
        this.updateDate = updateDate;
    }

    public String toString(){
        return "seq : "+ Long.toString(seq) + " title : "+ title + " content : " + content ;
    }
}