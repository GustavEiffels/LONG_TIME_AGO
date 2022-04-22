package com.example.memoserver.service.User;

import com.example.memoserver.dto.UserDto;
import com.example.memoserver.entity.User;

public interface UserService
{

    // DTO에서 Entity 로 데이터 변경
    default User dtoToEntity(UserDto userDto)
    {
        User user = User.builder()
                .user_id(userDto.getUser_id())
                .user_pw(userDto.getUser_pw())
                .user_auto_login(userDto.getUser_auto_login())
                .user_nick_name(userDto.getUser_nick_name())
                .user_Email(userDto.getUser_email())
                .user_status(userDto.getUser_status())
                .build();

        return user;
    }

    // Entity 에서 DTO 로 데이터 전달
    default UserDto EntityToDto(User user)
    {
        UserDto userDto = UserDto.builder()
                .user_id(user.getUser_id())
                .user_pw(user.getUser_pw())
                .user_auto_login(user.getUser_auto_login())
                .user_nick_name(user.getUser_nick_name())
                .user_email(user.getUser_Email())
                .user_status(user.getUser_status())
                .build();

        return userDto;
    }


    /** User 의 정보 저장 ------------
     */
    void saveUser(String id, String pw, String nick, String email);



}
