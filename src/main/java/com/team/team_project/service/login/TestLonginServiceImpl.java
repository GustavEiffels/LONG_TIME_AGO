package com.team.team_project.service.login;

import com.team.team_project.dto.PasswordDTO.PasswordDTO;
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
import java.util.regex.Pattern;

@Service
@Log4j2
@RequiredArgsConstructor
public class TestLonginServiceImpl implements TestLoginService {
    private final UserRepository userRepository;

    @Override
    public Map<String, Object> forlogin(String account, PasswordDTO dto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {

        // 비밀번호 까지 맞는지 확인  true 일때 모든 정보가 유효함
        boolean isItPw = false;


        // user 의 아이디 혹은 email 이 있는지 여부를 확인하기 위한 boolean
        boolean idOrEmailAvailable = false;


        // 넘겨 줄  변수들  -----------------------

        // user 의 user code 를 받아올 변수
        Long userCode = null;

        // user 닉네임을 받을  변수
        String userNick = null;

        // user 회원 정보 상태를 받음
        String userStatus = null;


        // 에러 message 들

        // 비밀번호를 틀렸을 때 message 를 담을 변수
        String wrongPassword = null;


        // 아이디 혹은 email 을 틀렸을 때 message 를 담을 변수
        String wrongAccount = null;





        // 입력받은 계정을 소문자화 및 공백제거
        account = account.toLowerCase().replace(" ","");




        if(account.contains("@")){
            if(!Pattern.matches( "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",account)){

                wrongAccount =  "not in email format";
            }else if(Pattern.matches( "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",account)){
                String email = account;
                String pw = dto.getPw();
                List<Object[]> list = userRepository.getalldata();

                for (Object[] i : list) {
                    String reemail = (String) i[0];
                    userStatus = (String)i[3];
                    String repw = (String) i[1];
                    if(CryptoUtil.decryptAES256(reemail, "Email").equals(email)){
                        idOrEmailAvailable = true;
                        if (userStatus.equals("회원")||userStatus.equals("7day")) {
                            if (BCrypt.checkpw(pw, repw)) {
                                // 아이디와 비밀번호가 맞다면 비밀번호가 일치 여부 boolean 을 true 로 변경
                                isItPw = true;

                                // 1. nick 에 entity 에서 가져온 nick
                                userNick = (String)i[2];


                                // 3. code 에 entity 에서 가져온 code 로 지정
                                userCode = (Long)i[4];


                            }else{
                                wrongPassword = "Wrong password";
                            }
                        }
                    }
                }


            }else if(idOrEmailAvailable==false){
                wrongAccount = "Email is not valid";
            }


        }else if(!account.contains("@")){

            if(!Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$", account)){
                wrongAccount =  "not in id format";
            }else if(Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$", account)){

            String id = account;
            String pw = dto.getPw();



            Object user = userRepository.findById(account);
            if(user!=null){
                Object[] result = (Object[]) user;
                String fid = (String) result[0];
                String fpw = (String) result[1];
                // userStatus 변수에 entity의 userStatus 값을 지정
                userStatus = (String) result[3];

                if (fid != null) {
                    idOrEmailAvailable = true;
                    if (userStatus.equals("회원")||userStatus.equals("7day")) {
                        if (BCrypt.checkpw(pw, fpw)) {
                            isItPw = true;

                            // nick name 에서 아이디와 비밀번호가 맞을 경우

                            // nick 에다가 entity 의 nick 값을
                            userNick = (String) result[2];


                            // code 변수에 entity 의 code 값을 지정 한다 .
                            userCode = (Long)result[4];
                        }else{
                            wrongPassword = "Wrong password";
                        }
                    }
                }
            }else if(idOrEmailAvailable==false){
                wrongAccount = "ID is not valid";
            }
        }
        }


        Map<String , Object> loginResult = new HashMap<>();
        if(isItPw==true){
            loginResult.put("success",isItPw);
            loginResult.put("userCode",userCode);
            loginResult.put("userNick", userNick);
            loginResult.put("userStatus", userStatus);
            loginResult.put("Account", idOrEmailAvailable);
            loginResult.put("wrongPassword", wrongPassword);
            loginResult.put("wrongAccount",wrongAccount);
        }else if(wrongAccount!=null){
            loginResult.put("success",isItPw);
            loginResult.put("account", idOrEmailAvailable);
            loginResult.put("wrongAccount",wrongAccount);
            loginResult.put("password",isItPw);
        }else if(wrongPassword!=null){
            loginResult.put("success",isItPw);
            loginResult.put("account", idOrEmailAvailable);
            loginResult.put("password",isItPw);
            loginResult.put("wrongPassword", wrongPassword);
        }
        return loginResult;
    }



}
