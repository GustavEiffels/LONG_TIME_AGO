    package com.singsiuk.guestbook.Entity;
    import lombok.Getter;
    import org.springframework.data.annotation.CreatedDate;
    import org.springframework.data.annotation.LastModifiedDate;
    import org.springframework.data.jpa.domain.support.AuditingEntityListener;

    import javax.persistence.Column;
    import javax.persistence.EntityListeners;
    import javax.persistence.MappedSuperclass;
    import java.time.LocalDateTime;

    // 상속을 위한 Entity class
    @MappedSuperclass
    // Entity 에 변화가 생기면 감지하도록 설정
    @EntityListeners(value={AuditingEntityListener.class})
    //공통 속성을 가지기 위해서 생성한 Entity

    // Get method 생성
    @Getter
    abstract class BaseEntity {
        // 만들어진 날짜를 저장
        @CreatedDate
        // 컬럼은 regdate 가 되고 수정은 되지 않음
        @Column(name="regdate", updatable = false)
        private LocalDateTime regDate;

        // 최근에 수정된 날짜를 저장
        @LastModifiedDate
        // 컬럼 이름
        @Column(name="moddate")
        private LocalDateTime modDate;


    }
