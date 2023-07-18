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


        if(loginArr==null || loginArr[2].equals("NotAvailable") )
        {
            result.put("errorMessage","Not Exist Account");
        }
        else
        {
            if(  ( loginArr[0] ).equals(userPw) )
            {
                result.put("userIdx",loginArr[1]);
            }
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

    @Override
    public String getUserPassword(Long user_idx)
    {
        return userRepository.getUserPassword(user_idx);
    }



    @Override
    public int changePw(int userAuto, String newPw, Long userIdx)
    {
        return userRepository.updatePw(0, newPw, userIdx);
    }

    /** --- true ---> email 사용 불가능
     *  --- false ---> email 사용 가능
     */
    @Override
    public String emailCheck(String user_email)
    {
        String result =  userRepository.emailCheck(user_email);


        if(result==null)
        {
            result="null";
        }
        return result;
    }

    @Override
    public String googleCheck(String email)
    {

        String result = "Y";

        // 이미 계정이 존재하는 경우
        if( userRepository.googleCheck(email) != null )
        {
            result = "N";
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

    @Override
    public int resignUser(Long idx)
    {
        return userRepository.resignUser("NotAvailable",idx);
    }
}
