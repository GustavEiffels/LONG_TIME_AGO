package com.example.memoserver.service;

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


}
