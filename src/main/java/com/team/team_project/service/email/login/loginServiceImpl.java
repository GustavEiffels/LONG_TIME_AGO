package com.team.team_project.service.email.login;

import com.team.team_project.repository.UserRepository;
import com.team.team_project.service.CryptoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
public class loginServiceImpl implements loginService{
    private final UserRepository userRepository;

    @Override
    public Map<String, Object> forlogin(String email, String pw) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        boolean exists = false;
        boolean pwcollect = false;
        if(email==null){
            if(pw==null){
                throw new IllegalArgumentException("Please Insert your Information for Login");
            }
            throw new IllegalArgumentException("Please Insert Id or Email");
        }
        else if(pw==null){
            throw new IllegalArgumentException("Please Insert pw ");
        }

        String nick = null;

        // user code 를 받을 variable 생성
        Long code = null;

        String status = null;

        if(email.contains("@")) {
            List<Object[]> list = userRepository.getalldata();
            for (Object[] i : list) {
                String reemail = (String) i[0];
                String repw = (String) i[1];
                status = (String)i[3];
                if(status.equals("회원")||status.equals("7day")){
                if (CryptoUtil.decryptAES256(reemail, "Email").equals(email)) {
                    System.out.println(CryptoUtil.decryptAES256(reemail, "Email"));
                    exists = true;
                    if (BCrypt.checkpw(pw, repw)) {
                        pwcollect = true;

                        // 아이디와 비밀번호가 맞다면

                        // 1. nick 에 entity 에서 가져온 nick
                        nick = (String)i[2];


                        // 3. code 에 entity 에서 가져온 code 로 지정
                        code = (Long)i[4];
                    }
                }
            }
            }

        }else {
            Object user = userRepository.findById(email);
            Object[] result = (Object[]) user;
            String fid = (String) result[0];
            String fpw = (String) result[1];
            // status 변수에 entity의 status 값을 지정
            status = (String) result[3];
            if (status.equals("회원")||status.equals("7day")) {
                if (fid != null) {
                    exists = true;
                    if (BCrypt.checkpw(pw, fpw)) {
                        pwcollect = true;

                        // nick name 에서 아이디와 비밀번호가 맞을 경우

                        // nick 에다가 entity 의 nick 값을
                        nick = (String) result[2];


                        // code 변수에 entity 의 code 값을 지정 한다 .
                        code = (Long)result[4];
                    }
                }
            }
        }
        System.out.println(status);
        Map<String, Object> map = new HashMap<>();
        if(status.equals("회원")||status.equals("7day")){
            map.put("exists",exists);
            map.put("pwcollect",pwcollect);
            map.put("nick",nick);

            // status 와 code 는 login 성공시에 사용할 거라서 map 에 key를 주고 저장
            map.put("status", status);
            map.put("code", code);
        }else{
            throw new IllegalArgumentException("It is not valid UserAccount");
        }
        return map;
    }
}

