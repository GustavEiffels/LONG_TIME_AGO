package com.singsiuk.board.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Member extends BaseEntity{
    @Id
    private String email;
    private String password;
    private String name;
}
