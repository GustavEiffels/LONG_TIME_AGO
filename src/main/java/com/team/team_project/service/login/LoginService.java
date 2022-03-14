package com.team.team_project.service.login;

import com.team.team_project.dto.passwordDTO.PasswordDTO;
import java.util.Map;

public interface LoginService {
    /**
     *  로그인을 위한 method
     */
    Map<String, Object> login(String account, PasswordDTO dto) throws Exception;
}