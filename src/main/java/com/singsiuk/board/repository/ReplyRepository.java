package com.singsiuk.board.repository;

import com.singsiuk.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    // 삽입 삭제 갱신에는 Modifying을 써줘야한다.
    @Modifying
    // 글번호를 가지고 댓글을 삭제하는 method
    @Query("delete from Reply r where r.board.bon = :bon")
    public void deleteByBon(Long bon);

}
