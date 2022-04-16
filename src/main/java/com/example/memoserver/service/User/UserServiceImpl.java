package com.example.memoserver.service.User;

import com.example.memoserver.dto.UserDto;
import com.example.memoserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{


    private final UserRepository userRepository;

    @Override
    public void saveUser(String id, String pw, String nick, String email)
    {
        UserDto dto = UserDto.builder()
                .user_id(id)
                .user_pw(pw)
                .user_nick_name(nick)
                .user_auto_login(1)
                .user_email(email)
                .build();

        userRepository.save(dtoToEntity(dto));

        log.info("save","Saving user Info success");
    }

}
