package com.memory.space.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    String id;
    String username;
    String password;

    public Member(){}

    public Member(String id,String username,String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
