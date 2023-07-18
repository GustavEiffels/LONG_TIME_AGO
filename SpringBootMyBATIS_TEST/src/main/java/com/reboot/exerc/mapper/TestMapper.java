package com.reboot.exerc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.reboot.exerc.dto.User;

@Mapper
public interface TestMapper {
    List<User> findAllUser();
}
