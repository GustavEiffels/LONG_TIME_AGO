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


    /** 자신의 계정을 찾는 Method */
    /** FindAccountFragment */
    @Override
    public String findAccount(String email) throws MessagingException
    {
        String result = "exist";

        log.info("findAccount = {}", userRepository.emailCheck(email));

     if(userRepository.emailCheck(email)==null)
     {

         result= "not";
     }
     else if( userRepository.emailCheck(email).equals("NotAvailable") )
     {
         result = "NotAvailable";
     }
     else
     {
         String userId = userRepository.getUserIdByEmail(email);
         emailSenderService.sendMail("Hello this is Board4_3",email, String.format("Your Id is :{}", userId));
         log.info("userId = {} ", userId);
     }

     return  result;
    }


    /** 비밀번호를 재설정하는 Method */
    /** FindAccountFragment */
    @Override
    public String findPassword(String email, String id) throws MessagingException {
        String result = "Exist";

        // user id , 혹은 email 둘중 하나가 null 일 때
        if(userRepository.emailCheck(email)==null ||  userRepository.duplicateId(id)==null)
        {
            result = "NotExist";
        }
        // user id 와 user email 이 서로 맞지 않을 때
        else if(userRepository.getUserIdxById(id)!=userRepository.getUserIdxByEmail(email))
        {
            result = "NotExist";
        }
        else if(userRepository.emailCheck(email).equals("NotAvailable"))
        {
            result = "NotAvailable";
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
