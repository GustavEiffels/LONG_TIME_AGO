package com.re.moviereivew.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString(exclude = {"movie","member"})
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // review 번호
    private Long reviewnum;

    @ManyToOne(fetch = FetchType.LAZY)
    // 영화 하나에 많은 리뷰가 생성
    private Movie movie;


    @ManyToOne(fetch = FetchType.LAZY)
    // 한사람이 여러개의 리뷰를 작성할 수 있기 때문
    private Member member;

    private int grade;

    private  String text;
}
