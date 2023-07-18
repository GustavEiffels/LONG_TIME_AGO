package com.example.memoserver.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long user_idx;

    private String user_id;

    private String user_pw;

    private String user_email;

    private int user_auto_login;

    private String user_nick_name;

    private String user_status;


}

