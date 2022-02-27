package com.team.team_project.service.find;


import com.team.team_project.dto.findDTO.ByNickDTO;
import com.team.team_project.dto.findDTO.ByQandAandBirthdayDTO;
import com.team.team_project.entity.Question;
import com.team.team_project.entity.User;
import com.team.team_project.repository.AnswerRepository;
import com.team.team_project.repository.QuestionRepository;
import com.team.team_project.repository.UserRepository;
import com.team.team_project.service.CryptoUtil;
import com.team.team_project.service.email.EmailSenderService;
import com.team.team_project.service.email.serialNumberFactory.ForFindPw;
import com.team.team_project.service.validationHandling.ValidateHandling;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class FindServiceImpl implements FindService{
    private final UserRepository userRepository;

    private final ValidateHandling validateHandling;

    private final EmailSenderService emailSenderService;
    @Override
    public Map<String, Object> findByNick(ByNickDTO dto) throws Exception {

        // dto 에서 nick name 출력
        String nick = dto.getNick();

        Object getIdAndEmail = userRepository.findByNick(nick);

        // 이상 발생시 Message 를 받을 변수
        String exceptionMessage = null;

        // masking 된 id를 받을 변수
        String idMaskingResult = null;

        // masking 된 email 값을 받을 변수
        String emailMaskingResult = null;




        /****
         * user status 가 회원, 7day가 아니면 exceptionMessage 를 가져야함
         */

        if(getIdAndEmail==null){
            exceptionMessage = "Invalid NickName!";
        }else {

            // id ,  값과  email 값을 받을 것이기 때문에 배열로 저장
            Object[] result = (Object[]) getIdAndEmail;

            String status = (String) result[2];

            if (status.equals("회원") || status.equals("7day")) {

                String email = (String) result[1];

                // Email 은 암호화가 되어있음으로 복호화를 실행
                email = CryptoUtil.decryptAES256(email, "Email");

                // 복호화를 확인
                System.out.println("마스킹 되기 전 Email : " + email);

                // @를 중심으로 나누어 배열로 저장
                String[] emailFront = email.split("@");
                int len_f = emailFront[0].length();


                if (len_f > 3) {
                    String noMask = emailFront[0].substring(0, 3);
                    // 값을 확인
//            System.out.println(noMask);
                    String Masking = "*".repeat(len_f - 3);

                    // 값을 확인
//            System.out.println(Masking);
                    emailMaskingResult = noMask + Masking;
                }

                int len_b = emailFront[1].indexOf(".");
                String masking_b = "*".repeat(len_b);
                String other = emailFront[1].substring(len_b);

                emailMaskingResult = emailMaskingResult + "@" + masking_b + other;

                // ------------------------------ id를 마스킹 하는 작업
                String id = (String) result[0];
                int idlen = id.length();

                String nomasking_id = id.substring(0, 3);
                String masking_id = "*".repeat(idlen - 3);

                idMaskingResult = nomasking_id + masking_id;

            }else{
                exceptionMessage = "Not Valid Account";
            }
        }

        Map<String, Object> resultByNick = new HashMap<>();
        // 이상 발생시 message 가 없다 == 유효성에 문제가 없다
        if(exceptionMessage==null){
                    resultByNick.put("exceptionEmerge",exceptionMessage);
                    resultByNick.put("emailMaskingResult", emailMaskingResult);
                    resultByNick.put("idMaskingResult",idMaskingResult);
        }else{
            resultByNick.put("exceptionEmerge",exceptionMessage);
        }

        return resultByNick;
    }


    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public Map<String, Object> findUserIdAndEmailByQuestionAndAnswer(ByQandAandBirthdayDTO dto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        // id 값을 받을 변수
        String id = null;

        // email 값을 받을 변수
        String email = null;

        // exception 이 발생했을 때 error message 를 받을 변수 생성
        String errorMessage = null;


        String context = dto.getContext();
        String answer = dto.getAnswer();
        String birthday = dto.getBirthday();

        // 해당 질문과 일치하는 qno를 list 형태로 return
        // context 는 우리가 제공하기 때문에 오류날 수가 없음
        // 하지만 혹시 모르니 설정하는 것이 좋다
        List<Long> qnoListByQuestion_Context = questionRepository.findByContext(context);

        // 해당 질문의 답을 가지고 있는  qno들을  question 형태로 return
        // 질문을 받았을 때  질문이 없을 경우 Error 를 발생
        List<Question> qnoListByUseAnswer = answerRepository.getQnoByUseContext(answer);

        // list 의 크기 --> 비교할 때 사용
        int listSize = qnoListByUseAnswer.size();





        // 질문을 통해서 얻은 qno 의 list 를 빠른 순환 시킴 ---> 여기에 대한 변수이름은 qnoResult
        if(qnoListByQuestion_Context!=null) {
            if (qnoListByUseAnswer != null) {
                for (Long qnoResult : qnoListByQuestion_Context) {
                    for (int i = 0; i < listSize; i++) {
                        if (qnoResult == (Long) ((Question) qnoListByUseAnswer.get(i)).getQno()) {
                            Question question = Question.builder()
                                    .qno(qnoResult)
                                    .build();

                            User userInfoByUsingQno = answerRepository.getCodeByUseQno(question);
                            Long userCode = userInfoByUsingQno.getCode();

                            LocalDate userBirthday = userInfoByUsingQno.getBirthday();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                            LocalDate date = LocalDate.parse(birthday, formatter);
                            if (userBirthday.equals(date)) {
                                Object userIdAndEmail = userRepository.getIdAndEmailByCode(userCode);
                                Object[] resultUserIdAndEmail = (Object[]) userIdAndEmail;
                                id = (String) resultUserIdAndEmail[0];
                                email = (String) resultUserIdAndEmail[1];
                                email = CryptoUtil.decryptAES256(email, "Email");
                            } else if (!userBirthday.equals(date)) {
                                throw new IllegalArgumentException("birth day is wrong");
                            }
                        }
                    }
                }
            }
        }


        Map<String, Object > result = new HashMap<>();
        result.put("id",id);
        result.put("email",email);
        return result;
    }

    @Override
    public Map<String, Object> checkAccountAvailable(String account) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        /**
         * 사용할 변수들 생성
         * */

        boolean userCodeExists = false;

        // 유효성 검사를 통과 못하거나 유효한 값이 아닐 경우 출력할 message
        String errorMessage = null;

        String accountIs = null;

        /**
         * 유효성 검사 진행
         * */

        Map<String, Object> accountValid = validateHandling.idAndEmailValidCheck(account);
        boolean accountValidResult = (boolean)accountValid.get("result");

        /***
         * account  다시 설정 , 소문자화 및 공백 제거를 적용
         * */
        account = (String)accountValid.get("account");


        /***
         * 유효성 검사 결과를 추출
         */
        // true 라는 말은 유효성검사를 통과했다는 말이다.
        if(accountValidResult==true){
            accountIs = (String) accountValid.get("validComplete_Value");

            /**
             *  유효성 검사를 통과했을 때 method
             * */

            // account 가 email 이라면
            if(accountIs.equals("email")) {
                // 모든 email 을 list 로 가져오는 Method 를 실행
                List<Object[]> emailAndCode = userRepository.return_Email_And_Status_List();


                // list 이기 때문에 전체 순환
                for (Object[] i : emailAndCode) {
                    String userEmail = (String) i[0];
                    String userStatus = (String) i[1];

                    // user 상태가 조회 가능한 상태일 경우
                    if (userStatus.equals("회원") || userStatus.equals("7day")) {

                        // email 전체에서 조회
                        if (CryptoUtil.decryptAES256(userEmail, "Email").equals(account)) {

                            // email 중에서 존재하는 것이 있다면 userCode 가 존재하는 것임으로 true
                            userCodeExists = true;
                        }
                    }
                }

                // 전체 순환이 끝났는데 userCode가 존재하지 않는다면 해당 id 는 존재하지 않는 값이다.
                if (userCodeExists == false) {
                    errorMessage = "This email does not exist";
                }


                // account 의 정체가 id 라면
            }else if(accountIs.equals("id")){
                // 해당 id 값이 존재하는지 확인

                // 해당 id 가 있는지 확인하는 Method 실행
                Object  userIdAndStatus  = userRepository.input_Id_Return_Id_And_Status(account);

                // userIdAndStatus 가 없을 때를 가정해야함
                if(userIdAndStatus!=null) {
                    Object[] info = (Object[]) userIdAndStatus;

                    String userId = (String) info[0];
                    String userStatus = (String) info[1];

                    // user 의 상태가 유효한 경우
                    if (userStatus.equals("회원") || userStatus.equals("7day")) {
                        // userId가 null 이 아니다 , 즉 존재한다면

                        userCodeExists = true;

                    }
                }
                else {
                    // 존재하지 않음으로 Error 발생
                    errorMessage = "The id does not exist. check back";
                }
            }
        }else{
            errorMessage = (String) accountValid.get("validErrorMessage");
        }





        /**
         * 해당 method 를 처음 작성할 때 userStatus 에 대해서 생각을 한적이 없다..
         * status 에 대해서 고려해야한다 .
         * */

        accountValid.put("isItExistsInfo",userCodeExists);
        if(userCodeExists==true){
            accountValid.put("accountIs",accountIs);
        }else{
            accountValid.put("errorMessage",errorMessage);
        }




        return accountValid;
    }

    /****
     *  context , answer , birthday , id or email 을 parameter 로 받는다
     *
     *  정보가 일치하면
     *
     *  pw 를 바꾸고 변경된 pw 를 해당 email 로 송신한다..
     *
     *  1. Entity
     *  Question ---- context  // column 중 qno 가지고 있다
     *
     *  Answer ------ answer // colum 으로 qno, code, 가지고 있다.
     *
     *  User -------- birthday // column 으로 code 를 가진다
     *
     *
     *  ---- 전에쓰던 method 를 지움 .
     *
     *  전에 쓰던거를 사용하기에는 유효성 적용해주기 힘듬
     *
     *  method 따로 설정해서 사용
     *
     *  ------- > id 혹은 email 을 받으니까 id 값이 일치하는 애들 찾아오자
     *
     *  순서
     *
     *  1. id or email 이 유효한 값이면 code 값을 받음
     *
     *  2. Answer table 에서 code 값을  입력받으면 qno ,  answer 를 출력받고 입력 받은 answer 가 맞는지 확인
     *
     *  3. 해당 qno 의 context 값이 입력한 context 갑인지 확인
     *
     *  4. user table 에서 user birthday 가 있는지 확인
     *
     *  5 . 위 조건을 만족 시키면 새로운 비밀번호 생성
     *
     *  6. 비밀번호 변경과 변경된 pw 를 email 로 송신하는 것 동시에 실행
     */
    @Override
    public Map<String, Object> resultOfPwfind(String userInfo, ByQandAandBirthdayDTO dto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, MessagingException {
        Map<String , Object > validResult = validateHandling.idAndEmailValidCheck(userInfo);

        // user code
        Long code = 0L;

        // user의 birthday
        LocalDate userBirthday = null;

        // user의 email
        String email = null;

        // user의 새로운 비밀번호
        String newPw = null;


        // 입력한 정보가 올바르지 않을때 message
        String infoErrorMessage = null;

        // 유효성과 입력한 정보가 모두 올바를 때 boolean
        boolean allValid = false;

        /***  birthday각 항목들이 일치하는 지여부를 확인하는 boolean 값을 설정
         * **/
        boolean birthdayCheck = false;

        boolean contextCheck = false;

        boolean answerCheck = false;

        /*** dto 에서 각 값을 받아옴
         * */
        String birthday = dto.getBirthday();

        String context = dto.getContext();

        String answer = dto.getAnswer();



        /***  account 가 유효한지 판단
         *  true ---> 유효함
         *  false ---> 유효하지 않음 / errorMessage 를 담고 잇음 :  key = validErrorMessage
         * */
        boolean result = (boolean) validResult.get("result");

        if(result==true){
            code = (Long)validResult.get("code");
            userBirthday = (LocalDate)validResult.get("birthday");

            /**
             * 입력한 정보들이 맞는지 확인
             * */
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthdayChanged = LocalDate.parse(birthday, formatter);

            birthdayCheck = birthdayChanged.equals(userBirthday);

            answerCheck = answerCheckMethod(code, answer);

            contextCheck = contextCheckMethod(code, context);
        }

        if(birthdayCheck==true&&answerCheck==true&&contextCheck==true){
            /**
             * 일일이  무엇이 틀렸다고 말해줄 수도 있지만 그러면 보안상 취약할 것 같다는 생각을 함
             *
             * 모든 확인 값이 일치하면 Email 과 변경할 pw 를 map 에 지정
             * **/
            allValid = true;
            email = getEmail(code);
            ForFindPw makePw = new ForFindPw();
            newPw = makePw.excuteGenerate();
        }else{
            infoErrorMessage = "The information you entered is incorrect";
        }

        validResult.put("allResult",allValid);

        if(allValid==true){
            validResult.put("email",email);
            validResult.put("newPw",newPw);
        }else {
            validResult.put("infoErrorMessage",infoErrorMessage);
        }



        return validResult;
    }

    /** code 와 answer 를 받고 해당 답이 일치하는지 확인하는 method
     * */
    public boolean answerCheckMethod(Long code, String answer){
        User user = User.builder()
                .code(code)
                .build();

        String getAnswer = answerRepository.getAnswerByCode(user);

        boolean answerValid = getAnswer.equals(answer);

        return answerValid;
    }


    /**
    code 와 context를 넣고 질문이 유효하는지 확인하는 method
    **/
    public boolean contextCheckMethod(Long code, String context){
        User user = User.builder()
                .code(code)
                .build();

        Question qno = answerRepository.getQnoByCode(user);

        String userContext = questionRepository.getContextByQno(qno.getQno());

        boolean contextValid = userContext.equals(context);

        return contextValid;
    }
    /** code 를 받으면
     * email 값을 받아오는 method 생성
     * */

    public String getEmail(Long code) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Object accountArray = userRepository.getIdAndEmailByCode(code);
        Object[] objectArray = (Object[]) accountArray;
        String email = (String) objectArray[1];
            email = CryptoUtil.decryptAES256(email,"Email");
            System.out.println(email);

        return email;
    }

    @Override
    public void PwChangeResult(String userInfo, String answer, String context, String birthday) throws InvalidAlgorithmParameterException, MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {

    }



    @Override
    public void PwChangeResult(String userInfo, ByQandAandBirthdayDTO dto) throws InvalidAlgorithmParameterException, MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Map<String, Object> result = resultOfPwfind(userInfo, dto);


        System.out.println(result.get("newPw"));
        System.out.println(result.get("email"));
        System.out.println(result.get("code"));


        String newPw = (String) result.get("newPw");
        String email = (String) result.get("email");

        String message = "Your new Password  : "+result.get("newPw")+"\n" +
                "\n Plaese Insert BlanK For Join Us";
        userRepository.changingPw(BCrypt.hashpw(newPw,BCrypt.gensalt()), (Long) result.get("code"));
        emailSenderService.sendMail("Here is New Password From Make Your Plan", email, message);
    }
}
