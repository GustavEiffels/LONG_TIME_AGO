package com.singsiuk.board.repository;

import com.singsiuk.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    //테스트용 method
    public Board search();

    // 목록보기를 위한 method
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
    // 3개의 Entity Join
    // 1 : N 의경우 Join 하는 Code를 생성


}
