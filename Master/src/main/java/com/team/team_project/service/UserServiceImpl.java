package com.team.team_project.service;

import com.team.team_project.dto.UserDTO;
import com.team.team_project.entity.User;
import com.team.team_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User join(UserDTO dto) throws Exception {


        User entity = dtoToEntity(dto);
        userRepository.save(entity);
        dto.setCode(entity.getCode());
        return entity;
    }

    @SneakyThrows
    @Override
    public String dec(Long code)
    {
        User user = userRepository.findByCode(code);
        String decryptComplete =  CryptoUtil.decryptAES256(user.getId(),"Id");
        return decryptComplete;
    }

    @Override
    public Boolean checkid(String id) {
        return null;
    }



    @Override
    public Boolean checkEmail(String email) {
        return null;
    }



}
