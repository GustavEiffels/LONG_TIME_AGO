package com.team.team_project.repository;

import com.team.team_project.dto.UserDTO;
import com.team.team_project.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> ,QuerydslPredicateExecutor<User> {
    public User findByCode(Long code);

    /***
     * 회원 가입을 위한 method
     *
     * */

    // 모든 email 을 찾는 Method
    @Query(value = "select email from User" )
    List<String> getAllEmail();

    //Server 측에서 nick name 중복 있는지 확인하기 위해서
    //모든 닉네임을 들고오는 method
    @Query(value = "select nick from User")
    List<String> getAllNick();

    @Query(value = "select id from User")
    List<String> getALLId();



    @Query(value = "select u.id, u.pw, u.nick, u.status, u.code , u.birthday from User u where u.id =:id")
    Object findById(@Param("id") String id);


    @Query(value = "select email, pw, nick, status, code, birthday from User")
    List<Object[]> getUserInfo();


    // --------FindServiceImpl------------------------------------ User 정보 찾기 기능 ----------------------------------------

    /**
     * nick name 을 Parameter 로 받아서
     * id, email, status 를 return
     *
     * 사용 목적 :
     * 닉네임으로 사용자의 아이디와 Email 을 마스킹 해서 View 에 표출
     */
    @Query(value = "select u.id, u.email, u.status from User u where u.nick = :nick")
    Object findByNick(@Param("nick") String nick);

    @Query(value = "select code, email, pw, nick, status, birthday from User")
    List<Object[]> findAllByEmail();

    @Query(value = "select u.code , u.id, u.pw, u.nick, u.status, u.birthday  from User u where u.id =:id")
    Object findAllById(@Param("id") String id);

    // --------FindServiceImpl------------------------------------ User 정보 찾기 기능 ----------------------------------------








    @Query(value = "select u.id, u.email from User u where u.code = :code")
    Object getIdAndEmailByCode(@Param("code")Long code);


    @Query(value = "select email, status from User")
    List<Object []> return_Email_And_Status_List();


    // pw 변경 servie method 에 사용 // id 가 일치하면 id return
    @Query(value = "select id, status from User where id = :id ")
    Object  input_Id_Return_Id_And_Status(@Param("id")String id);

    @Query(value = "select id, email, gender, birthday, code from User where nick = :nick ")
    Object  bringUserData(@Param("nick")String nick);






    // --------------------------- 사용자의 비밀번호를 가져오는 method
    @Query(value = "select pw from User where nick=:nick")
    String brinUserPw(@Param("nick")String nick);

    // --------------------------- 사용자의 비밀번호를 가져오는 method
    @Query(value = "select pw from User where code=:code")
    String getPw(@Param("code")Long code);



    @Query(value = "select u from User u")
    User bringAllData();




    // 비밀번호 변경 하기
    // String
    @Modifying
    @Transactional
    @Query(value = "update User set pw =:pw where code =:code")
    void changingPw(@Param("pw")String pw, @Param("code")Long code);


    // 회원 탈퇴시 method
    // session 에 있는 method 에 있는
    // nick 을 사용해서
    // status 를 expiring 으로 변경


    // =========== 회원 탈퇴 ============================================

    // -------- 회원 닉네임을 가지고 회원 계정 상태를 변경
    @Modifying
    @Transactional
    @Query(value = "update User set status=:status where nick=:nick")
    int unSubscribe(@Param("status") String status, @Param("nick")String nick);


    @Modifying
    @Transactional
    @Query(value = "update User set status=:status where code=:code")
    int unSub(@Param("status") String status, @Param("code")Long code);


    // -------- 회원 닉네임을 삽입해서 마지막 수정 날짜를 변경 --------------
    @Modifying
    @Transactional
    @Query(value = "update User set modDate=:modDate where nick=:nick")
    int updateModate(@Param("modDate") LocalDateTime modDate, @Param("nick")String nick);

    @Modifying
    @Transactional
    @Query(value = "update User set modDate=:modDate where code=:code")
    int updateModDateTemp(@Param("modDate") LocalDateTime modDate, @Param("code")Long code);





    // 회원정보 수정 =====================================================================================================
    // 비밀번호 까지 같이 바꿀 경우 -----------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "update User set nick=:nickCh, pw=:pw, birthday=:birthday, gender=:gender where nick=:nick")
    int changeUserInfo(@Param("nick")String nick,
                       @Param("nickCh")String nickCh,
                       @Param("pw")String pw,
                       @Param("gender")String gender,
                       @Param("birthday")LocalDate birthday);

    @Modifying
    @Transactional
    @Query(value = "update User set nick=:nickCh, birthday=:birthday, gender=:gender where nick=:nick")
    int changeUserInfoExceptPw(@Param("nick")String nick,
                       @Param("nickCh")String nickCh,
                       @Param("gender")String gender,
                       @Param("birthday")LocalDate birthday);
    // 비밀번호 까지 같이 바꿀 경우 -----------------------------------------------
    // 회원정보 수정 =====================================================================================================

    // nick name 을 입력받으면 code 를 리턴
    @Query(value = "select code from User where nick=:nick")
    Long getUserCodeByNick(@Param("nick")String nick);


    /***
     * pw 변경할 때
     * parameter 로  answer , context , birthday
     * */


    /** 휴면 계정인 사용자의 state 를 '회원' 으로 변경하기 위한 method ----------
     */
    @Modifying
    @Transactional
    @Query(value = "update User set status=:status, pw=:pw, modDate=:modDate where code=:code")
    int unSubCancel(@Param("status")String status,
                       @Param("pw")String pw,
                       @Param("modDate") LocalDateTime modDate,
                       @Param("code")Long code);
}
