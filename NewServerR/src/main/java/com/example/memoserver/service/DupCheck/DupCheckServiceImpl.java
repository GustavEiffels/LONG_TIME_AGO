package com.example.memoserver.service.DupCheck;

import com.example.memoserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class DupCheckServiceImpl implements DupCheckService
{
    private final UserRepository userRepository;

    /** Id 중복확인 method
     *  IdAndPwFragment : idDuplicate -----> */
    @Override
    public boolean idDuplicateCheck(String userId)
    {
        boolean result = true;

        // Id 를 조회해봤는데 아이디가 존재
        if( userRepository.duplicateId(userId) !=null )
        {
            result = false;
        }

        return result;
    }


    /** Nick 중복확인 method
     *  JoinFragment */
    @Override
    public boolean nickDuplicateCheck(String userNick)
    {
        boolean result = true;

        if( userRepository.duplicateNick(userNick)  != null)
        {
            result = false;
        }
        return result;
    }


    /** Email 중복 확인 ---- return : String --- user_status */
    /** EmailFragment : emailCheck */
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

}
