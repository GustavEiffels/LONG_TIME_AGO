package com.team.team_project.service.unscribe;

import com.team.team_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UnscribeServiceImpl implements UnscribeService{
    private final UserRepository userRepository;
    @Override
    public boolean userUnscribecomplete(String status, String nick) {
        boolean successOrNot =false;
        int unscribeResult = userRepository.unSubscribe(status, nick);
        if(unscribeResult>0){
            successOrNot = true;
        }
        return successOrNot;
    }
}
