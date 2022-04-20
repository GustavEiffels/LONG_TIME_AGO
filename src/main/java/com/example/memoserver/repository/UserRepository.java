package com.example.memoserver.repository;

import com.example.memoserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,Long>
{

    @Query(value ="select user_pw, user_idx from User where user_id=:userId")
    Object login(@Param("userId")String userId);

    @Query(value = "select user_id from User where user_id=:userId")
    String duplicateId(@Param("userId")String userId);

    @Query(value="select user_idx from User where user_nick_name=:nick")
    String duplicateNick(@Param("nick") String nick);


    /** 로그인 시 user 의 idx 로 auto login 값 들고오 기
     */
    @Query(value = "select user_auto_login from User where user_idx=:userIdx")
    String getAutoLogin(@Param("userIdx")Long userIdx);


    /**
     * 로그인 이후 유저의 Nick name 을 가져오는 method
     */
    @Query(value = "select user_nick_name from User where user_idx=:userIdx")
    String getUserNIck(@Param("userIdx")Long userIdx);


    /** 비밀번호 변경하기 전 본인 확인을 위해서 비밀번호를 받아오는 method
     */
    @Query(value = "select user_pw from User where user_idx=:user_idx")
    String getUserPassword(@Param("user_idx")Long user_idx);



    @Transactional
    @Modifying
    @Query(value = "update User set user_auto_login=:userAuto where user_id=:userId ")
    int autoLoginCheck(@Param("userAuto")int userAuto, @Param("userId")String userId);


    @Transactional
    @Modifying
    @Query(value = "update User set user_auto_login=:userAuto where user_idx=:userIdx ")
    int logout(@Param("userAuto")int userAuto, @Param("userIdx")Long userIdx);


    @Transactional
    @Modifying
    @Query(value = "update User set user_auto_login=:userAuto, user_pw=:newPw where user_idx=:userIdx")
    int updatePw(@Param("userAuto")int userAuto, @Param("newPw")String newPw, @Param("userIdx")Long userIdx);

    @Transactional
    @Modifying
    @Query(value = "update User set user_pw=:newPw where user_Email=:email")
    int updatePwByEmail(@Param("newPw")String newPw, @Param("email")String email);


    /**  Email Duplicate Check
     */
    @Query(value = "select user_Email from User where user_Email=:user_email")
    String emailCheck(@Param("user_email")String user_email);


    /**  google Account Check
     */
    @Query(value = "select user_nick_name from User where user_Email=:user_email")
    String googleCheck(@Param("user_email")String user_email);


    /** get GoogleAccount
     */
    @Query(value ="select user_idx, user_nick_name, user_auto_login from User where user_Email=:email")
    Object googleAccount(@Param("email")String email);


    /** Change user_auto_login
     */



    @Transactional
    @Modifying
    @Query(value = "update User set user_auto_login=:login where user_Email=:email")
    void googleAutoLogin(@Param("login")int login, @Param("email")String email);


    @Query(value = "select user_id from User where user_Email=:email")
    String getUserIdByEmail(@Param("email")String email);








}
