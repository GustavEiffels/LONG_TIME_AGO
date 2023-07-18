package com.re.moviereivew.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "movie")
// 항상 movie 안에서 생성됨으로 Embeddable을 추가
@Embeddable
// movie 와 함께 생성 되고 수정 됨으로 별도의 생성 날짜와
// 수정 날짜를 가질 필요가 없다
public class MovieImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //이미지 구분 번호
    private Long inum;

    // uuid(이미지 파일 구분)
    private String uuid;

    // 이미지 이름
    private String imgName;

    // 경로
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

}
