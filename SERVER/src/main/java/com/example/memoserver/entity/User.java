package com.example.memoserver.entity;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@DynamicInsert
@Table(name="User",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","user_nick_name"})})
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_idx;


    @Column(length = 100, nullable = false)
    private String user_Email;

    @Column(name="user_id",length = 100, nullable = false)
    private String user_id;

    @Column(length = 100, nullable = false)
    private String user_pw;

    @Column(columnDefinition = "varchar(2) check (user_auto_login in (0,1))" ,nullable = false)
    private int user_auto_login;

    @Column(name="user_nick_name",length = 100, nullable = false)
    private String user_nick_name;

    @Column(nullable = false)
    private String user_status;

}
