package com.team.team_project.service.edit;

import com.team.team_project.dto.PasswordDTO.PasswordDTO;
import com.team.team_project.dto.editInfoDTO.EditInfoDTO;
import com.team.team_project.entity.Question;
import com.team.team_project.entity.User;
import com.team.team_project.repository.AnswerRepository;
import com.team.team_project.repository.QuestionRepository;
import com.team.team_project.repository.UserRepository;
import com.team.team_project.service.CryptoUtil;
import com.team.team_project.service.pwCheck_And_DuplicateCheck.PwAndDupCheck;
import com.team.team_project.service.validationHandling.ValidateHandling;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class EditServiceImpl implements EditService{
    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;


    private final PwAndDupCheck pwAndDupCheck;

    private  final ValidateHandling validateHandling;

//    @Query(value = "select id, email, gender, birthday from User where id = :nick ")
    @Override
    public Map<String, Object> bringUserInfo(String nick) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Object result = userRepository.bringUserData(nick);
        Object[] userInfo = (Object[]) result;


        String userId = (String)userInfo[0];
        String userEmail = (String)userInfo[1];
        userEmail = CryptoUtil.decryptAES256(userEmail,"Email");
        String userGender = (String)userInfo[2];
        LocalDate userBirthday = (LocalDate) userInfo[3];
        Long userCode = (Long)userInfo[4];



        Map<String, Object> users = new HashMap<>();
        users.put("userId", userId);
        users.put("userEmail", userEmail);
        users.put("userGender", userGender);
        users.put("userBirthday", userBirthday);
        users.put("userCode", userCode);
        return users;
    }

    @Override
    public String bringAnswerInfo(Long code) {
        User userCode  = User.builder()
                .code(code)
                .build();
        String userAnswer = answerRepository.getAnswerByCode(userCode);
        return userAnswer;
    }
//
    @Override
    public String bringQuestionInfo(Long code) {
        User userCode  = User.builder()
                .code(code)
                .build();
        Question result = answerRepository.getQnoByCode(userCode);
        Long userQno = result.getQno();
        Question qno = Question.builder()
                .qno(userQno)
                .build();
        String userContext = questionRepository.getContextByQno(qno.getQno());
        return userContext;
    }

    @Override
    public boolean bringPwForRetire(String pw,String nick) {
        boolean userConfirm = false;
        String userPw = userRepository.brinUserPw(nick);
        if(BCrypt.checkpw(pw, userPw)){
            userConfirm = true;
        }
        /**
         * nick name 으로 user 의 pw 를 가져오는 것이라서
         * pw 가 없을 수가 없다 .
         */


        return userConfirm;
    }
    /***
     *  user info 변경시
     *
     *  parameter null 값을 처리해주는 method
     */




    @Override
    public Map<String, Object> changeUserInfo(HttpSession session, String currentNick,
                                  String nick,
                                  String pw,
                                  String pwCheck,
                                  String gender,
                                  String birthday,
                                  String answer,
                                  String context) {


        /****
         *  요소들 중 하나라도 안넣으면 Error 가 발생하기 때문에
         *
         *  정보를 변경하려면 전체를 변경해야 한다 .. 비효율적이다 ..
         *
         *  그래서 변경하지 않을 정보를 null 로 두면
         *
         *  model 에서 지금 현재 정보를 가져와서 적용 시킨다 .
         *
         */

        /***
         *  user 정보를 수정하는 method
         *
         *  result : true ---> 정보 변경이 잘됨
         *  result : false ---> 정보 변경중 오류 발생
         *          --> nickErrorMessage = nickName 설정시 Error 발생 message
         *          --> pwErrorMessage = pw 변경시 Error 발생 message
         */
        Map<String, Object> userInfoUpdate = userInfoUpdate(currentNick,nick,pw,pwCheck,gender,birthday);


        // User code 를 사용해서 나머지 정보들을 불러올수 있음으로 UserCode 를 구하는 method 실행
        Long code = (Long) session.getAttribute("code");


        User userCode = User.builder()
                .code(code)
                .build();

        // ano를 구하기 위한 method
        Long ano = answerRepository.anoByCode(userCode);
        System.out.println(ano);



        // context 를 수정하기 위해서 AnswerRepository 를 사용해서 qno 를 return
        Question questionQno = answerRepository.getQnoByCode(userCode);
        Long qno = questionQno.getQno();
        System.out.println(qno);


        //answer 를 수정하기 위한 method
        int resultAnswer = answerRepository.updateUserAnswer(ano, answer);


        // context 를 수정하기 위해서 qno 와  context  를 입력받는 method
        int resultQuestion = questionRepository.updateUserContext(qno, context);



        return userInfoUpdate;
    }

    public Map<String, Object> userInfoUpdate(String currentNick, String nick, String pw, String pwCheck, String gender, String birthday){
        /** 닉네임이 유효한지 아닌지에 대해서 return
         * 중복 된다면 false
         * 중복 되지 않는 다면 true
         * */
        boolean nickDuplicate = pwAndDupCheck.nickDuplicateCheck(nick, currentNick);


        /***
         * pw 와 pwcheck 가 일치하는지 확인
         */
        boolean pwCheckResult = false;

        /***
         * pw 를 바꾹 싶지 않다는 것을 확인
         */
        boolean wantPwChange =true;


        if(pw.equals("noChange")){
            /***
             *  즉 비밀번호에 값을 아무것도 넣지 않았을 경우를 말함
             *  pw 에 아무것도 없는데 pwcheck 에 값이 있는 것은 이상함으로
             * */
            if(pwCheck.equals("")){
                pwCheckResult=true;
                wantPwChange = false;
            }
        }else{
            pwCheckResult = pwAndDupCheck.pwAndPwCheck(pw,pwCheck);
            if(pwCheckResult == true){
                pw = BCrypt.hashpw(pw,BCrypt.gensalt());
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdayChanged = LocalDate.parse(birthday, formatter);

        Map<String, Object> result = new HashMap<>();

        if(nickDuplicate==true&&pwCheckResult==true){
            result.put("result",true);
            if(wantPwChange==true) {
                userRepository.changeUserInfo(currentNick, nick, pw, gender, birthdayChanged);
            }else{
                userRepository.byNickUpdateUserInfo(currentNick,nick,gender,birthdayChanged);
            }

        }else{
            result.put("result",false);
            if(nickDuplicate==false) {
                result.put("nickErrorMessage", "someone Using that nick name, please setting another one");
            }
            if(pwCheckResult==false){
                result.put("pwErrorMessage","The passwords do not match each other.");
            }
        }

        return result;
    }



    /****
     *  parameter 값이 null 일때를 처리하기 위한 MEthod
     */

    @Override
    public Map<String, String> parameterNullProcess(HttpSession session, String nick, String pw, String answer, String gender, String birthday, String context) {

        Map<String, String> result = new HashMap<>();

        /** parameter 들의 값들이 null 일 경우
         *
         * model 에서 현재 값들을 가져와서 비어있는 값들에 적용 시킨다.
         * */
        String nickValidError = null;
        String pwValidError = null;
        String answerValidError = null;

        // ErrorOrNot
        boolean errorEmerge = false;

        if(nick==""){
            nick = (String)session.getAttribute("nick");
        }else if(nick!=null){
            if(!validateHandling.nickValid(nick)){
                nickValidError= "Korean, English, numbers excluding special characters, 2~20 digits";
                result.put("nickValidError",nickValidError);
                errorEmerge=true;

            }
        }
        if(pw == ""){
            /**
             * pw 는 복호화할 수 없기 때문에 pw 가 null 일 때 다른 method 수행 시켜야함
             * */
            pw = "noChange";
        }else if(pw!=null){
            if(!validateHandling.pwValid(pw)){
                pwValidError = "Password must be 8 to 16 characters in uppercase and lowercase letters, numbers, and special characters.";
                result.put("pwValidError",pwValidError);
                errorEmerge=true;
            }
        }

        if(gender==null){
            gender=(String)session.getAttribute("userGender");
            if(gender.equals("Male")){
                gender = "m";
            }else if(gender.equals("Female")){
                gender = "f";
            }
        }
        System.out.println(birthday);
        if(birthday == ""){
            LocalDate getBirthday= (LocalDate) session.getAttribute("userBirthday");
            birthday = String.valueOf(getBirthday);
        }

        if(answer == "" ) {
            answer = (String) session.getAttribute("userAnswer");
        }else if(answer!=""){
            if(!validateHandling.answerValid(answer)){
                answerValidError = "answer type is Korean, numeric, English 2~20 characters";
                result.put("answerValidError",answerValidError);
                errorEmerge=true;
            }
        }

        if(context == ""){
            context = (String)session.getAttribute("userContext");

            /** 질문 변경이 없다 ---> 현재 질문에 유지
             *
             * 그에 대한 답도 없다 ----> 질문에 다한 답 또 한 유지
             * */
        }else if(context!="") {
            /**
             * 질문이 null 이 아니고 기존의 질문과 일치하지 않다면 ----> 즉 달라진다면
             * answer 도 무조건 달라져야하는데.
             * Answer 의 값이 null 이라면 error
             *
             * */
            if((!context.equals((String)session.getAttribute("userContext")))&&answer==null){
                result.put("errorMessage", "If you want to change a question, please enter an answer to the question");
                errorEmerge=true;
            }else if((context.equals((String)session.getAttribute("userContext")))&&answer==null){
                answer = (String) session.getAttribute("userAnswer");
            }
            }


        if(errorEmerge==false) {
            result.put("error","notExists");
            result.put("nick", nick);
            result.put("pw", pw);
            result.put("answer", answer);
            result.put("gender", gender);
            result.put("birthday", birthday);
            result.put("context", context);
        }else{
            result.put("error","exists");

        }

     return result;
    }


    @Override
    public int unSubScribeCancle(String pw,  String pwCheck, Long code) {
        int result = -1;
        String pwresult = null;
        String status = "회원";

        /*** 새로운 pw 가 pwcheck 와 맞는지 확인 ----> 이거 method 따로 빼서 만들자  많이 사용됨
         * */
        if(pwAndDupCheck.pwAndPwCheck(pw,pwCheck)){
            pwresult = BCrypt.hashpw(pw,BCrypt.gensalt());
            LocalDateTime modDate = LocalDateTime.now();
            result = userRepository.unScribeCancle(status, pwresult, modDate, code);
        }



        return result;
    }
}
