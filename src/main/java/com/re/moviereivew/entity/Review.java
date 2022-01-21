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


    // Fetch 를 설정하지 않으면 Review 정보를 가져올 때 Join 을 해서
    // 데이터를 가져온다
    // FetchType 을 Lazy로 설정하면 처음에는 가져오지 않는다.
    @ManyToOne(fetch = FetchType.LAZY)
    // 한사람이 여러개의 리뷰를 작성할 수 있기 때문
    private Member member;

    private int grade;

    private  String text;
}
