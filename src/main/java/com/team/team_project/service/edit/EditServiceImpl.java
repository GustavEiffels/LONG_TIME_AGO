package com.team.team_project.service.edit;

import com.team.team_project.entity.Question;
import com.team.team_project.entity.User;
import com.team.team_project.repository.AnswerRepository;
import com.team.team_project.repository.QuestionRepository;
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
        String userAnswer = answerRepository.getAnswerForEditInfo(userCode);
        return userAnswer;
    }
//
    @Override
    public String bringQuestionInfo(Long code) {
        User userCode  = User.builder()
                .code(code)
                .build();
        Question result = answerRepository.getQnoForEditInfo(userCode);
        Long userQno = result.getQno();
        Question qno = Question.builder()
                .qno(userQno)
                .build();
        String userContext = questionRepository.getContextForUserInfo(qno.getQno());
        return userContext;
    }

    @Override
    public boolean bringPwForRetire(String pw,String nick) {
        boolean userConfirm = false;
        String userPw = userRepository.brinUserPw(nick);
        if(BCrypt.checkpw(pw, userPw)){
            userConfirm = true;
        }else{
            throw new IllegalArgumentException("Wrong Password");
        }

        return userConfirm;
    }

    @Override
    public Map<String, Object> changeUserInfo(String currentNick,
                                  String nick,
                                  String pw,
                                  String pwCheck,
                                  String gender,
                                  String birthday,
                                  String answer,
                                  String context) {

        // 비밀번호랑 비밀번호 확인 값이 같으면 비밀번호를 암호화 해서 넣는다
        String pwChanged =null;





        // user , answer , question  table 에 데이터가 알맞게 들어가면 true 로 변경
        boolean userUpdateSuccess = false;
        boolean answerUpdateSuccess  = false;
        boolean questionUpdateSuccess = false;


        //nick name 중복 체크 완료 여부
        boolean nickIsNotDuplicate = true;


        // pw 와 pwCheck이 맞는지 확인
        boolean SamePwEachOther = false;


        if(pw.equals(pwCheck)) {
            // 비밀번호랑 비밀번호 확인 값이 같으면 비밀번호를 암호화 해서 넣는다
             pwChanged = BCrypt.hashpw(pw, BCrypt.gensalt());
            SamePwEachOther = true;
        }else if(!pw.equals(pwCheck)){
            // 서로 일치하지 않으면 Exception 발생
            throw new IllegalArgumentException("password and The verification password does not match.");
        }

        // 닉네임이 일치하는지 확인
        List<String> nickDuplicateCheck = userRepository.nickNameDuplicateCheck();

        // 닉네임이 일치하는 것이 존재하면
        // 존재한다면 false 로  바꿈
        for(String nickCheck:nickDuplicateCheck){
            if(nickCheck.equals(nick)){
                nickIsNotDuplicate = false;
                throw new IllegalArgumentException("somebody use that nickname ");
            }
        }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdayChanged = LocalDate.parse(birthday, formatter);

        int resultOfChangeUserInfo =0;
        if(nickIsNotDuplicate==true&&SamePwEachOther==true) {
            // session 에 저장되어 있는 user 의 nick name 을 입력받아서 수정
            resultOfChangeUserInfo = userRepository.changeUserInfo(currentNick, nick, pwChanged, gender, birthdayChanged);
        }
        // User code 를 사용해서 나머지 정보들을 불러올수 있음으로 UserCode 를 구하는 method 실행
        Long code = userRepository.getUserCodeByNick(nick);

        User userCode = User.builder()
                .code(code)
                .build();

        // ano를 구하기 위한 method
        Long ano = answerRepository.anoByCode(userCode);
        System.out.println(ano);



        // context 를 수정하기 위해서 AnswerRepository 를 사용해서 qno 를 return
        Question questionQno = answerRepository.getQnoForEditInfo(userCode);
        Long qno = questionQno.getQno();
        System.out.println(qno);


        //answer 를 수정하기 위한 method
        int resultAnswer = answerRepository.updateUserAnswer(ano, answer);




        // context 를 수정하기 위해서 qno 와  context  를 입력받는 method
        int resultQuestion = questionRepository.updateUserContext(qno, context);

        if(resultOfChangeUserInfo>0){
            userUpdateSuccess=true;
        }

        if(resultAnswer>0){
            answerUpdateSuccess=true;
        }

        if(resultQuestion>0){
            questionUpdateSuccess=true;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("user",userUpdateSuccess);
        result.put("answer",answerUpdateSuccess);
        result.put("question", questionUpdateSuccess);
        return result;
    }

    @Override
    public int unSubScribeCancle(String pw,String pwCheck, Long code) {
        int result = 0;
        String pwresult = null;
        String status = "회원";
        if(pw.equals(pwCheck)){
            pwresult = BCrypt.hashpw(pw,BCrypt.gensalt());
        }else{
            throw new IllegalArgumentException("password ans password checking is not same each other");
        }
        LocalDateTime modDate = LocalDateTime.now();

        result = userRepository.unScribeCancle(status, pwresult, modDate, code);

        return result;
    }
}
