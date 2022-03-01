package com.team.team_project.service.validationHandling;

import com.team.team_project.repository.UserRepository;
import com.team.team_project.service.CryptoUtil;
import com.team.team_project.service.pwCheck_And_DuplicateCheck.PwAndDupCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ValidateHandling {

    private  final UserRepository userRepository;

    public  Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    // 이메일, 아이디 동시 로그인 시 유효성 판별하기 위한 method
    /***
     return  Map 으로 하고
     id , email 이 유효한지 아닌지 판단을 boolean 으로 판단
     ***/

    public Map<String, Object> idAndEmailValidCheck(String account) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        // email
        boolean isItEmail = false;

        // id
        boolean isItId = false;

        // message
        String  errorMessage = null;

        // 유효성에 만족하고 존재하는 값인지 확인
        boolean validFinalCheck = false;

        // 비밀번호를 받을 변수
        String password = null;

        // userCode 를 받을 변수
        Long code = 0L;

        // nickName 을 받을 변수
        String nick = null;

        // status를 받을 함수
        String status = null;

        // birthday 를 받을 변수
        LocalDate birthday = null;





        // Map 을 리턴하기 때문에 미리 만들어두자
        Map<String, Object> validCheckResult = new HashMap<>();


        /****
         * account 모든 문자를 소문자로 만들고 , 공백을 제거
         */
        account = account.toLowerCase().replace(" ","");

        /***
         account 에 @ 가  포함되어 있으면 Email
         그렇지 않으면 id 로 간주
         * ***/

        // account 가 email 일 때,
        if(account.contains("@")){
            // account 가 email 이기 때문에 id 체크할 필요가 없다
            isItEmail = true;

            // email 이 정규식에 포함된다면
            if(Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",account)) {


                /***
                 *  Email 이 존재하는지 확인
                 */

                List<Object[]> list = userRepository.getalldata();

                for (Object[] i : list) {
                    /***
                     * getEmail 은 list Object 배열안에 있는 id
                     * getStatus 는 list Object 배열안에 있는 status
                     * */
                    String getEmail = (String) i[0];
                    status = (String)i[3];

                    /***
                     * getEmail 이 account 와 같을 때
                     * getStatus 값이 7day 나 회원일때
                     * list 에서 password 값을 갖는다
                     * */
                    if(CryptoUtil.decryptAES256(getEmail, "Email").equals(account)){
                        if(status.equals("7day")||status.equals("회원")){
                            validFinalCheck = true ;
                            /**
                             * 여기서 입력받는 password 는 암호화된 password
                             * **/
                            password = (String)i[1];
                            nick = (String)i[2];
                            code = (Long)i[4];
                            birthday = (LocalDate) i[5];
                            break;
                        }else{
                            /**
                             * status 가 만족하지 않을때 출력할 message를 설정
                             * */
                            errorMessage = "expired Email";
                        }

                    }

                    }
                /**
                 * 반복문 실행하고나서도 validFinalCheck 라면
                 * 존재하지 않는 Email 이라는 것
                 * **/
                if(validFinalCheck==false){
                    errorMessage = "Not Valid Email, Please Check Again";
                }
            }
            // account 가 email 이 정규식에 포함 되지 않는다면
            else{
                /***
                 * 여기에 해당한다는 얘기는 유효성에 맞지 않다는 얘기임으로 유효성이 맞지 않다는 Error message 를 띄워야함
                 */
                errorMessage = "It doesn't fit the email format. Please rewrite the email format";

            }

       // account 가 id 일 때
        }else{
            // account 가 id 임으로 true 로 변경
            isItId = true ;
            if(Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{6,11}$",account)){

                /**
                 *  account가 유효한지 확인
                 *
                 *  findById
                 *  @Query(value = "select u.id, u.pw, u.nick, u.status, u.code from User u where u.id =:id")
                 *     Object findById(@Param("id") String id);
                 * **/
                Object user = userRepository.findById(account);

                /**
                 * user 가 null 일때 --> 일치하는 id 가 없다
                 * user 가 null 이 아닐 때 일치하는 id 가 존재
                 *
                 *
                 * **/
                if(user!=null){
                    /** id가 존재하는데 그 아이디 값이 유효한 status 값인지 확인 해야함
                     * */
                    Object[] result = (Object[]) user;
                    status = (String)result[3];

                    if(status.equals("회원")||status.equals("7day")){
                        // 유효한 id 임으로
                        validFinalCheck = true;

                        /** 아이디가 유효하기 때문에 nick, code , password 를 부여
                         * */
                        password = (String)result[1];
                        nick = (String)result[2];
                        code = (Long)result[4];
                        birthday = (LocalDate) result[5];
                    }else{
                        /**
                         * 유효한 status 가 아님으로 만료된 계정이다.
                         * */
                        errorMessage = "Expired Account";
                    }

                }/***
                 일치하는 Id 가 없기 때문에
                 */
                else{
                    errorMessage = "No matching IDs, check again";
                }

            }
            // id 가 정규식에 포함되지 않는다면  ----> 즉 맞지 않는 다면
            else{
                /***
                 * 여기에 해당한다는 얘기는 유효성에 맞지 않다는 얘기임으로 유효성이 맞지 않다는 Error message 를 띄워야함
                 */
                errorMessage = "The ID format does not match. Please check again";
            }
        }
        // map 에 유효성 검사가 성공했는지 여부를 삽입
        validCheckResult.put("result",validFinalCheck);


        // 값들이 유효한 경우
        if(validFinalCheck==true) {
            validCheckResult.put("account",account);
            validCheckResult.put("code",code);
            validCheckResult.put("pw",password);
            validCheckResult.put("nick",nick);
            validCheckResult.put("status",status);
            validCheckResult.put("birthday",birthday);
            // 유효한 값이 email 일 경우\
            if (isItEmail == true) {
                validCheckResult.put("validComplete_Value", "email");
                System.out.println(code);
                System.out.println(password);
                System.out.println(nick);
            }
            // 유효한 값이 id 일 경우
            else if (isItId == true) {
                validCheckResult.put("validComplete_Value", "id");
                System.out.println(code);
                System.out.println(password);
                System.out.println(nick);
            }
        }

        // 값들이 유효성에 만족하지 못한 경우
        else{
            validCheckResult.put("validErrorMessage",errorMessage);
            System.out.println(errorMessage);
        }

        return validCheckResult;
    }

    public boolean nickValid(String nick){
        boolean result = false;
        if(Pattern.matches("^[가-힣a-zA-Z0-9]{2,20}$",nick)){
            result = true;
        }
        return result;
    }

    public boolean pwValid(String pw) {
        boolean result = false;
        if (Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", pw)) {
            result = true;
        }
        return result;
    }

    public boolean answerValid(String answer){
        boolean result = false;
        if(Pattern.matches("^[가-힣a-zA-Z0-9]{2,20}$",answer)){
            result = true;
        }
        return result;
    }

    boolean emailDupCheck(String email) throws Exception{
        /**
         *  email 이 중복된다면 false
         *
         */
        boolean result = false;
        List<String> list = userRepository.getAllEmail();;
        for(String i:list){
           String getEmail = CryptoUtil.decryptAES256(i, "Email");
           if(getEmail.equals(email)){
               result = true;
               break;
           }
        }
        return result;
    }
    boolean nickDupCheck(String nick){
        boolean result = false;
        List<String> list = userRepository.getAllNick();
        for(String i:list){
            if(i.equals(nick)){
                result = true;
            }
        }
        return result;
    }
    boolean idDupCheck(String id){
        boolean result = false;
        List<String> list = userRepository.getALLId();
        for(String i: list){
            if(i.equals(id)){
                result = true;
            }
        }
        return result;
    }

    /***
     *  id, email , nick  중복 검사 method + pw 확인  method
     * */

    private final PwAndDupCheck pwAndDupCheck;

    public Map<String, String> joinValidation(String email, String id, String nick, String pw, String pwCheck) throws Exception {


        /**
         * 최종적으로 유효한 값인지 확인
         * true 이면 유효
         * fasle 이면 유효하지 않음
         */
        boolean valid = true;

        boolean emailResult = emailDupCheck(email);
        boolean idResult = idDupCheck(id);
        boolean nickResult = nickDupCheck(nick);

        /**
         * 비밀번호 체크는
         * true 일 때 서로 일치
         * false 일 때 서로 일치하지 않음
         */
        boolean pwResult = pwAndDupCheck.pwAndPwCheck(pw, pwCheck);

        /**
         *   map 생성
         *
         */
        Map<String, String> result = new HashMap<>();

        if(emailResult==true){
            result.put("emailMessage","Someone use it already");
            valid = false;
        }
        if(idResult==true){
            result.put("idMessage","Someone use it already");
            valid = false;
        }
        if(nickResult==true){
            result.put("nickMessage","Someone use it already");
            valid = false;
        }
        if(pwResult==false){
            result.put("pwMessage","Passwords do not match");
            valid = false;
        }

        if(valid==true){
            result.put("result","okay");
        }else{
            result.put("result","no");
        }


        return result;
    }

    }
