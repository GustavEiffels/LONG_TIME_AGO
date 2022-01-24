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
public class ClubMember extends BaseEntity{
    @Id
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;
    //권한을 하나만 가지는 경우
    //private ClubMemberRole rols;

    //권한을 여러개 가질 수 있는 경우
    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<ClubMemberRole> roleSet = new HashSet<>();

    //권한을 추가하는 메서드
    public void addMemberRole(ClubMemberRole clubMemberRole){
        roleSet.add(clubMemberRole);
    }
}