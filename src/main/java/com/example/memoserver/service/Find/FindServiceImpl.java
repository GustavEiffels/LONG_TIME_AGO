package com.example.memoserver.service.Find;

import com.example.memoserver.repository.UserRepository;
import com.example.memoserver.service.Email.ForFindPw;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindServiceImpl implements FindService
{
    private final UserRepository userRepository;

    private final ForFindPw forFindPw;


    @Override
    public String findAccount(String email)
    {

        String result = "exist";
     if(userRepository.emailCheck(email)==null)
     {
         result= "not";
     }
     else
     {
         String userId = userRepository.getUserIdByEmail(email);
         log.info("userId = {} ", userId);
     }

     return  result;
    }

    @Override
    public String findPassword(String email, String id)
    {
        String result = "exist";
        if(userRepository.emailCheck(email)==null ||  userRepository.duplicateId(id)==null)
        {
            result = "not";
        }
        else
        {

            String newPw = forFindPw.excuteGenerate();
            userRepository.updatePwByEmail(newPw, email);
            log.info("new Password = {}",newPw);
        }


        return  result;

    }
    /**
     *  git
     */
}
