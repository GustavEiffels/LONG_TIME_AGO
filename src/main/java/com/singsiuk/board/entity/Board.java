package com.singsiuk.board.entity;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Board extends BaseEntity{
    @Id
    //글 번호를 직접 만들지 못 할때 만들어 달라는 요청
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long bon;
    private  String title;
    private  String content;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Member writer;

    //title을 수정하는 메서드
    public void changeTitle(String title){
        this.title = title;
    }
    //content를 수정하는 메서드
    public void changeContent(String content){
        this.content = content;
    }
}