package com.singsiuk.board.repository;

import com.singsiuk.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>,SearchBoardRepository {
    // Member 와 Board Entity 의 Join을 수행하는 method를 생성
    // Board Entity 에는 Member  Entit와 연관관계를 갖는 writer 가 존재

    // bon 에 해당하는 Board 를 가져올 때 Member에 대한 정보도 가져오기
    // w 가 member table
    // on 이 들어가지 않음
    @Query("select b,w from Board b  left join b.writer w where b.bon=:bon")
    Object getBoardWithWriter(@Param("bon")Long bon);

    // Reply 와 Board Entity 의 Join을 수행하는 method를 생성
    // Board Entity 에는 Reply  Entity와 연관관계를 가지고 있지 않음
    // 양쪽에 공통된 속성을 찾아야한다
    // reply 가 Board 의 정보를 Board 라는 속성으로 가지고 있음
    // bon 에 해당하는 Board 를 가져올 때 Member에 대한 정보도 가져오기
    @Query("select b,r from Board b  left join Reply r on r.board = b  where b.bon=:bon")
    // 하나의 게시글에 댓글이 여러개 있을 수 있어서 Return Type 은 List
    List<Object[]> getBoardWithReply(@Param("bon")Long bon);



    // 목록을 보기 위한 method
    // JPQL 에서는 Page 단위로 리턴시 CountQuery 가 필수
    @Query(value = "select b, w, count(r)" +
            "from Board b Left Join b.writer w Left Join Reply  r On r.board =b" +
            " group by b ",
            countQuery = "select count(b) from Board b")
    Page<Object []> getBoardWithReplyCount(Pageable pageable);


    //게시글 상세보기를 위한 method
    @Query("select b, w, count(r) from Board b left join b.writer w " +
            "left  join  Reply r on r.board = b where b.bon = :bon")
    // Object 라서 CountQuery 가 필요 없기 때문에
    // Value를 생략해도 됨( 구분하려고 쓰는거라서 )
    Object getBoardByBon(@Param("bon")Long bon);



}
