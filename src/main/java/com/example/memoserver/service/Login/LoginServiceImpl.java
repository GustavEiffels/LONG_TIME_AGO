package com.example.memoserver.service.Login;

import com.example.memoserver.entity.User;
import com.example.memoserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService
{
    private final UserRepository userRepository;

    /** Login 을 위한 method  */
    /** LoginFragment ----> login */
    @Override
    public Map<String,Object> login(String userId, String userPw)
    {

        /**
         *  Object[0] = userPw
         *  Object[1] = user_nick_name
         */
        Map<String,Object> result = new HashMap<>();


        Object loginRes = userRepository.login(userId);

        Object[] loginArr = (Object[]) loginRes;


        // 로그인 결과가 없을 때
        if(loginArr==null)
        {
            result.put("errorMessage","Not Exist Account");
        }
        // 계정 상태가 탈퇴 상태일 때,
        else if( loginArr[2].equals("NotAvailable") )
        {
            result.put("errorMessage", "NotAvailable");
        }

        // 계정이 존재할 때
        else
        {
            // 입력한 비밀번호가 맞을 때 ,
            if(  ( loginArr[0] ).equals(userPw) )
            {
                result.put("userIdx",loginArr[1]);
            }
            // 입력한 비밀번호가 맞지 않을 때 ,
            else
            {
                result.put("errorMessage","password is different");
            }
        }
        return result;
    }

    @Override
    public int autoUpdate(int userAuto, String userId)
    {
        return  userRepository.autoLoginCheck(userAuto, userId);
    }

    @Override
    public String getUserAutoInfo(Long userIdx)
    {

        return  userRepository.getAutoLogin(userIdx);
    }


    @Override
    public String getUserNick(Long userIdx)
    {
        return userRepository.getUserNIck(userIdx);
    }

    @Override
    public void logout(Long user_idx, int user_auto_login)
    {
        userRepository.logout(user_auto_login, user_idx);
    }







    /** --- true ---> email 사용 불가능
     *  --- false ---> email 사용 가능
     */
    @Override
    public String emailCheck(String user_email)
    {
        /** available ---> 이미 누군가 사용중이다
         *  null -----> 아무도 사용하지 않는 이메일이다
         *  NotAvailable ------> 탈퇴한 이메일이다.
         */
        String result =  userRepository.emailCheck(user_email);

        /** 해당 이메일을 사용하는 사람이 없다 = 사용할 수 있다.*/
        if(result==null)
        {
            result="null";
        }
        return result;
    }

    @Override
    public String googleCheck(String email)
    {

        String result = "Exist";


        if( userRepository.googleCheck(email) == null )
        {
            result = "NotExist";
        }
        else if(userRepository.googleCheck(email).equals("NotAvailable"))
        {
            result = "NotAvailable";
        }

        return result;
    }

    @Override
    public JSONObject googleAccount(String email)
    {

        JSONObject jsonObject = new JSONObject();
        userRepository.googleAutoLogin(1,email);
        Object result = userRepository.googleAccount(email);

        Object[] accountResult = (Object[]) result;

        jsonObject.put("login_user_idx",accountResult[0]);
        jsonObject.put("login_user_nick",accountResult[1]);
        jsonObject.put("login_auto_login",accountResult[2]);



        return jsonObject;
    }

    /** user id 로 user email 을 가져오는 Method */
    /** login 했을 때, 계정이 탈퇴 계정일 경우 id 를 사용해서 user Email 을 가져옴 */
    /** LoginFragment ------> notAvailable*/
    @Override
    public String notAvailable(String id)
    {
        return userRepository.notAvailable(id);
    }


}
