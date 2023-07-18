package com.sin.club.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMember extends User {
    private String email;
    private String name;
    private boolean fromSocial;

    public ClubAuthMember(String username , String password ,
                          Collection<?extends GrantedAuthority> authorities){
        //상위 클래스의 생성자 호출
        super(username,password,authorities);
        this.email = username;
        this.fromSocial= isFromSocial();

    }
}
