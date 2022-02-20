package com.team.team_project.service.login;

import com.team.team_project.dto.loginDTO.loginDTO;
import com.team.team_project.repository.UserRepository;
import com.team.team_project.service.CryptoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class TestLonginServiceImpl implements TestLoginService {
    private final UserRepository userRepository;

    @Override
    public Map<String, Object> forlogin(loginDTO dto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        boolean isItEmail = false;
        boolean isItPw = false;
        Long userCode = null;
        String userNick = null;
        String userStatus = null;
        boolean validCheck = false;

        String emialOrId = dto.getEmail();

        if(emialOrId.contains("@")){
            isItEmail = true;
        }

        if(isItEmail==true){
            dto = loginDTO.builder()
                    .email(dto.getEmail())
                    .pw(dto.getPw())
                    .build();

            String email = dto.getEmail();
            String pw = dto.getPw();



            List<Object[]> list = userRepository.getalldata();

            for (Object[] i : list) {
                String reemail = (String) i[0];
                userStatus = (String)i[3];
                String repw = (String) i[1];
                if(userStatus.equals("회원")||userStatus.equals("7day")){

                    if (CryptoUtil.decryptAES256(reemail, "Email").equals(email)) {




                        if (BCrypt.checkpw(pw, repw)) {
                            // 아이디와 비밀번호가 맞다면
                            isItPw = true;

                            // 1. nick 에 entity 에서 가져온 nick
                            userNick = (String)i[2];


                            // 3. code 에 entity 에서 가져온 code 로 지정
                            userCode = (Long)i[4];

                            validCheck = true;
                        }
                    }
                }
            }
        }else if(isItEmail==false){

            dto = loginDTO.builder()
                    .id(dto.getEmail())
                    .pw(dto.getPw())
                    .build();

            String email = dto.getId();
            String pw = dto.getPw();



            Object user = userRepository.findById(email);
            Object[] result = (Object[]) user;
            String fid = (String) result[0];
            String fpw = (String) result[1];
            // userStatus 변수에 entity의 userStatus 값을 지정
            userStatus = (String) result[3];
            if (userStatus.equals("회원")||userStatus.equals("7day")) {
                if (fid != null) {
                    if (BCrypt.checkpw(pw, fpw)) {
                        isItPw = true;

                        // nick name 에서 아이디와 비밀번호가 맞을 경우

                        // nick 에다가 entity 의 nick 값을
                        userNick = (String) result[2];


                        // code 변수에 entity 의 code 값을 지정 한다 .
                        userCode = (Long)result[4];
                    }
                }
            }
        }
        Map<String , Object> loginResult = new HashMap<>();
        if(isItPw==true){
            loginResult.put("accountValid",isItPw);
            loginResult.put("userCode",userCode);
            loginResult.put("userNick", userNick);
            loginResult.put("userStatus", userStatus);
            loginResult.put("validCheck", validCheck);
        }
        return loginResult;
    }

    @Override
    public Map<String, String> loginAccountValidateHandling(Errors errors) {
        return TestLoginService.super.loginAccountValidateHandling(errors);
    }

    @Override
    public Map<String, String> loginPasswordValidateHandling(Errors errors) {
        return TestLoginService.super.loginPasswordValidateHandling(errors);
    }
}
