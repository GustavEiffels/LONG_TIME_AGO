package com.example.memoserver.service.Find;

import com.example.memoserver.repository.UserRepository;
import com.example.memoserver.service.Email.EmailSenderService;
import com.example.memoserver.service.Email.ForFindPw;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindServiceImpl implements FindService
{
    private final UserRepository userRepository;

    private final ForFindPw forFindPw;

    private final EmailSenderService emailSenderService;


    @Override
    public String findAccount(String email) throws MessagingException {

        String result = "exist";
     if(userRepository.emailCheck(email)==null)
     {
         result= "not";
     }
     else
     {
         String userId = userRepository.getUserIdByEmail(email);
         emailSenderService.sendMail("Hello this is Board4_3",email, String.format("Your Id is :{}", userId));
         log.info("userId = {} ", userId);
     }

     return  result;
    }

    @Override
    public String findPassword(String email, String id) throws MessagingException {
        String result = "exist";
        if(userRepository.emailCheck(email)==null ||  userRepository.duplicateId(id)==null)
        {
            result = "not";
            log.info("result", "notSame");
        }
        else if(userRepository.getUserIdxById(id)!=userRepository.getUserIdxByEmail(email))
        {
            result = "not";
            log.info("result", "notSame");
        }
        else
        {

            String newPw = forFindPw.excuteGenerate();
            userRepository.updatePwByEmail(newPw, email);
            emailSenderService.sendMail("Hello this is Board4_3",email, String.format("Your New Password is :{}", newPw));

            log.info("new Password = {}",newPw);
        }


        return  result;

    }
    /**
     *  git
     */
}
