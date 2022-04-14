package com.example.memoserver.repository;

import com.example.memoserver.entity.Board;
import com.example.memoserver.entity.Content;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ContentRepository extends JpaRepository<Content,Long>
{

    @Query(value = "select max(content_idx) as read_content_idx from Content where content_board_idx=:content_board_idx")
    int getMaxContentIdx(@Param("content_board_idx") Board content_board_idx);


    @Query(value =
            "select c.content_subject,\n" +
                    "u.user_nick_name as content_nick_name,\n" +
                    "date_format(c.content_write_date,'%Y-%m-%d') as content_write_date,\n" +
                    "c.content_text, c.content_image, \n" +
                    "c.content_image_url, " +
                    "c.content_writer_idx, " +
                    "c.content_board_idx " +
                    "from Content c, User u \n" +
                    "where c.content_writer_idx = u.user_idx\n" +
                    "and content_idx=:content_idx " +
                    "order by content_idx desc ", nativeQuery = true)
    Object getContentInfo(@Param("content_idx")Long content_idx);


    @Query(value =
            "select c.content_subject, \n" +
                    "date_format(c.content_write_date,'%Y-%m-%d') as content_write_date,\n" +
                    "c.content_text, " +
                    "c.content_image, \n" +
                    "c.content_image_url, " +
                    "c.content_writer_idx, " +
                    "c.content_board_idx, " +
                    "c.content_idx " +
                    "from Content c  \n" +
                    "where c.content_writer_idx=:user_idx " +
                    "order by c.content_idx desc", nativeQuery = true)
    List<Object> getPrivateUserContent(@Param("user_idx")Long user_idx);




    /**
     *  선택한 게시판 조회 ------------
     */
    @Query(value =
            "select c.content_idx,\n" +
                    "u.user_nick_name as content_nick_name, \n" +
                    "date_format(c.content_write_date,'%Y-%m-%d') as content_write_date, \n" +
                    "c.content_subject, " +
                    "c.content_image_url, " +
                    "c.content_image, " +
                    "c.content_board_idx  " +
                    "from Content c, User u \n" +
                    "where c.content_writer_idx = u.user_idx \n" +
                    "and c.content_board_idx=:content_board_idx ", nativeQuery = true)
    List<Object> getContentByContent_board_idx(@Param("content_board_idx")Board content_board_idx,
                                               Pageable pageable);


    /**
     * 전체 게시판 조회 -------------
     */
    @Query(value =
            "select c.content_idx, \n" +
                    "u.user_nick_name as content_nick_name, \n" +
                    "date_format(c.content_write_date,'%Y-%m-%d') as content_write_date, \n" +
                    "c.content_subject, " +
                    "c.content_image_url, " +
                    "c.content_image, " +
                    "c.content_board_idx " +
                    "from Content c,  User u \n" +
                    "where c.content_writer_idx = u.user_idx\n", nativeQuery = true)
    List<Object> getContentByContent_board_idx_T(Pageable pageable);

    /**
     *  글을 삭제하기 위한 method
     */
    @Transactional
    @Modifying
    @Query(value = "delete from Content where content_idx=:content_idx")
    int deleteContent(@Param("content_idx")Long content_idx);


    @Transactional
    @Modifying
    @Query(value = "update Content set content_subject=:content_subject," +
            "content_text=:content_text, " +
            "content_image=:content_image, " +
            "content_image_url=:content_image_url, " +
            "content_board_idx=:content_board_idx where content_idx=:content_idx")
    int contentUpdate(@Param("content_subject")String content_subject,
                      @Param("content_text")String content_text,
                      @Param("content_image")String content_image,
                      @Param("content_image_url")String content_image_url,
                      @Param("content_board_idx")Board content_board_idx,
                      @Param("content_idx")Long content_idx);


    /**
     *  선택한 게시판 조회 ------------
     */
//    @Query(value =
//            "select c.content_subject,\n" +
//                    "u.user_nick_name as content_nick_name,\n" +
//                    "date_format(c.content_write_date,'%Y-%m-%d') as content_write_date,\n" +
//                    "c.content_image_url, " +
//                    "c.content_idx, " +
//                    "c.content_writer_idx, " +
//                    "c.content_board_idx " +
//                    "from Content c, User u \n" +
//                    "where c.content_writer_idx = u.user_idx\n" +
//                    "and c.content_board_idx=:content_board_idx " +
//                    "order by c.content_idx desc " +
//                    "limite=:limit, 10 ", nativeQuery = true)
//    List<Object> getContentByContent_board_idx(@Param("content_board_idx")Board content_board_idx, @Param("limit")int limit);
//
//
//    /**
//     * 전체 게시판 조회 -------------
//     */
//    @Query(value =
//            "select c.content_subject,\n" +
//                    "u.user_nick_name as content_nick_name,\n" +
//                    "date_format(c.content_write_date,'%Y-%m-%d') as content_write_date,\n" +
//                    "c.content_image_url, " +
//                    "c.content_idx, " +
//                    "c.content_writer_idx, " +
//                    "c.content_board_idx " +
//                    "from Content c, User u \n" +
//                    "where c.content_writer_idx = u.user_idx\n" +
//                    "order by c.content_idx desc " +
//    List<Object> getContentByContent_board_idx_T(@Param("limit")int limit);

}