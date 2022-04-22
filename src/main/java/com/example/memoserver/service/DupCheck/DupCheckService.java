package com.example.memoserver.service.DupCheck;

public interface DupCheckService
{

    /** Id 중복확인 method
     *  IdAndPwFragment : idDuplicate -----> */
    boolean idDuplicateCheck(String userId);


    /** Nick 중복확인 method
     *  JoinFragment */
    boolean nickDuplicateCheck(String userNick);


    /** 이메일 중복 확인 ---- return : String --- user_status */
    /** EmailFragment : emailCheck */
    String emailCheck(String user_email);
}
