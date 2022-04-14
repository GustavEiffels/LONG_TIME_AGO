package com.example.memoserver.service;

import com.example.memoserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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





        if(loginArr==null)
        {
            result.put("errorMessage","Not Exist Account");
        }
        else
        {
            log.info("loginArr[0].toString()",(String)loginArr[0]);
            if(  ( (String)loginArr[0] ).equals(userPw) )
            {
                result.put("userIdx",(Long)loginArr[1]);
                log.info("loginArr[0].toString()",(String)loginArr[0]);
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
}
