package com.re.moviereivew.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name="m_member")
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 회원 구분 번호
    private Long mid;

    // email
    private String email;

    // pw
    private String pw ;

    // nickname
    private String nickname;
}
