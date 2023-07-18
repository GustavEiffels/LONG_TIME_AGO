package com.reboot.exerc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String U_uid;
    private String U_nick;
    private String U_email;
    private String U_pw;
    private String U_id;
    private String U_role;
}
