package com.team.team_project.service.pwCheck_And_DuplicateCheck;

import com.team.team_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PwAndDupCheckImpl implements PwAndDupCheck{

    /** pw 와 pwCheck 가 일치하는지 판단하는 method
     */
    @Override
    public boolean isPwEqual(String pw, String pwCheck)
    {
        boolean isValid = false;
        if(pw.equals(pwCheck))
            {
                isValid = true;
            }
        return isValid;
    }


    private  final UserRepository userRepository;


    /**
     * true ---> 중복된 값이 없다
     * false ---> 중복된 값이 존재한다
     */
    @Override
    public boolean nickDuplicateCheck(String nick, String currentNick) {
        boolean nickNotDuplicate = true;

        /**
         * 모든 nick name 을 들고 온다
         */
        List<String> nickDuplicateCheck = userRepository.getAllNick();

        for(String nickCheck:nickDuplicateCheck)
            {

            // nick 이 중복할 경우 ---------------------------------------
            if(nickCheck.equals(nick))
                {

                // 그 중복된 값이 현재 사용하고 있는 nick 인 경우 통과 -------------------------
                if(nickCheck.equals(currentNick))
                    {
                    continue;

                    /** 그렇지 않다는 것은 다른누군가가 사용하고 있다는 말이기 때문에
                     * nickDuplicate 를 true 로 변경
                     * */
                    }
                // 그 중복된 값이 현재 사용하고 있는 nick 이 아닌 경우 ----------------------------------
                // 다른 사용자가 있다는 의미
                else
                    {
                    nickNotDuplicate = false;
                    }

                }
            }
        return nickNotDuplicate;
    }
}
