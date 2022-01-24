package com.sin.club.entity;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EntityListeners(value={AuditingEntityListener.class})
@Table(name="User",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email","id","nick"})})
@DynamicInsert
public class ClubMember extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long code;

    @Column(name="id", length = 255, nullable = false)
    private String id;

    @Column(name="email",length = 255, nullable = false)
    private String email;

    @Column(name="pw",length = 255, nullable = false)
    private String pw;

    @Column(name="nick",length = 50, nullable = false)
    private String nick;

    @Column(name="birthday",nullable = false)
    private LocalDate birthday;

    @Column(name="gender",columnDefinition = "varchar(2) check (gender in ('m','f'))" ,nullable = false)
    private String gender;

    @Column(columnDefinition = " varchar(5) default '회원'")
    private String status;


    public void changePw(String user_pw){
        this.pw = user_pw;
    }

    public void changeNick(String nick){
        this.nick = nick;
    }

    public void changeGender(String gender){
        this.gender = gender;
    }

    public void changeStatus(String status) {
        this.status = status;
    }

    public void changeBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }


    // 권한을 하나만 가지는 경우
//    private ClubMemberRole rols;


//    권한을 여러개 가질 수 있는 경우

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<ClubMemberRole> roleSet = new HashSet<>();

    // 권한을 추가하는 method
    public void addMemberRole(ClubMemberRole clubMemberRole){
        roleSet.add(clubMemberRole);
    }

}
