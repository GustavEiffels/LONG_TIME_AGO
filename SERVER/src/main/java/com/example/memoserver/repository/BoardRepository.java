package com.example.memoserver.repository;

import com.example.memoserver.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long>
{
    @Query(value = "select * from Board order by board_idx ", nativeQuery = true)
    List<Board> getAllBoard();

}
