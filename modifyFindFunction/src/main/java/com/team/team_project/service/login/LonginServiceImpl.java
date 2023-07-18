package com.team.team_project.service.login;
import com.team.team_project.dto.passwordDTO.PasswordDTO;
import com.team.team_project.repository.UserRepository;
import com.team.team_project.service.validationHandling.ValidateHandling;
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
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class LonginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    private final ValidateHandling validateHandling;


    @Override
    public Map<String, Object> forloginUpdate(String account, PasswordDTO dto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Map<String,Object> result = validateHandling.accountValid(account);
        /*** idAndEmailValidCheck Key
         * // map 에 유효성 검사가 성공했는지 여부를 삽입
         *         validCheckResult.put("result",validFinalCheck);
         *
         *
         *         // 값들이 유효한 경우
         *         if(validFinalCheck==true) {
         *             validCheckResult.put("account",account);
         *             // 유효한 값이 email 일 경우\
         *             if (isItEmail == true) {
         *                 validCheckResult.put("validComplete_Value", "email");
         *                 validCheckResult.put("code",code);
         *                 validCheckResult.put("pw",password);
         *                 validCheckResult.put("nick",nick);
         *                 System.out.println(code);
         *                 System.out.println(password);
         *                 System.out.println(nick);
         *             }
         *             // 유효한 값이 id 일 경우
         *             else if (isItId == true) {
         *                 validCheckResult.put("validComplete_Value", "id");
         *                 validCheckResult.put("code",code);
         *                 validCheckResult.put("pw",password);
         *                 validCheckResult.put("nick",nick);
         *                 System.out.println(code);
         *                 System.out.println(password);
         *                 System.out.println(nick);
         *             }
         *         }
         * **/

        /**
         * --------------------------------------------------------------------------------------
         * method 시작
         * **/

        /**
         * complete = true ----> 아이디 혹은 이메일이 존재하는 값
         * complete = false ----> error
         * */
        boolean complete = (boolean)result.get("result");

        /**
         * dto 에서 pw 값을 가져온다
         * */
        String pw = dto.getPw();

        /***
         * 최종적으로 pw 까지 맞는지 확인하기 위한 boolean 값
         * */
        boolean loginComplete = false;

        /** pwError Message 를 받을 String
         * **/
        String pwErrorMessage = null;



        if(complete==true){
            /**
             * complete 이 true 이면 nick, pw, code 값이 존재함
             * validComplete_value = email ----> 해당 값이 email
             * validComplete_value = id -------> 해당 값이 id
             * **/

            String pwResult = (String)result.get("pw");

            if(BCrypt.checkpw(pw, pwResult)){
                loginComplete = true;
            }else{
                /***
                 *  else 는 비밀번호가 서로 다르다는 말
                 *  pwError 에 값을 부여
                 * */
                pwErrorMessage = "Wrong Password ! Pleas check it";
            }
        }
        result.put("loginResult", loginComplete);

        if(loginComplete==false){
            result.put("passwordError", pwErrorMessage);
        }

        return result;
    }


}
