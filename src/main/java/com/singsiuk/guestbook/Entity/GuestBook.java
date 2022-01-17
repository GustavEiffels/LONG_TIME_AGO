package com.singsiuk.guestbook.Entity;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuestBook extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(length = 100, nullable = false)
    private String writer;

    // 지금 Get 만 가능
    // Title 은 수정이 되었으면 좋겠다는 생각
    // Setter 생성
    public void changeTitle(String title){
        this.title=title;
    }
    // content 도 수정해야한다는 생각
    // Content 수정 Setter 생성
    public void changeContent(String content){
        this.content=content;
    }

}
