package com.reboot.exerc.service.test.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reboot.exerc.dto.User;
import com.reboot.exerc.mapper.TestMapper;
import com.reboot.exerc.service.test.service.TestService;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class TestServiceImpl implements TestService {

    private final TestMapper testmapper;

    @Override
    public List<User> findAllUser() {
        return testmapper.findAllUser();

    }

}
