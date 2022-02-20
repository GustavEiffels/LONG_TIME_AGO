package com.team.team_project;


import com.team.team_project.dto.loginDTO.loginDTO;
import com.team.team_project.entity.*;
import com.team.team_project.repository.*;
import com.team.team_project.service.*;
import com.team.team_project.service.edit.EditService;
import com.team.team_project.service.email.EmailSenderService;
import com.team.team_project.service.email.serialNumberFactory.ForFindPw;
import com.team.team_project.service.email.serialNumberFactory.ForJoin;
import com.team.team_project.service.find.FindService;
import com.team.team_project.service.login.TestLoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
public class RepositoryTest {

    @Autowired
    private UserRepository userRepository;

//    @Test
//    private void findid(){
//        List<UserDTO> list = userRepository.findUserByIdAndEmail(3L);
//        for(UserDTO l:list){
//            System.out.println(toString());
//        }
//    }

//    @Test
    public void userTest(){
        IntStream.rangeClosed(1,100).forEach(i->{
            User user = User.builder()
                    .email("Test"+i+"@naver.com")
                    .id("test"+i)
                    .nick("테스트"+i)
                    .pw("QWER!@#$"+i*10)
                    .birthday(LocalDate.parse("2000-01-01"))
                    .gender("f")
                    .build();
            userRepository.save(user);
        });
    }

    @Autowired
    private UserService userService;
//
////    @Test
//    public void joinTestByDto() throws Exception {
//        UserDTO dto = UserDTO.builder()
//                .id("0123!_12")
//                .email("01232!_1t@test.com")
//                .nick("테스트맨20123_1")
//                .pw("QERWEq22q2R!@#")
//                .birthday("1999-12-12")
//                .gender("f")
//                .build();
//        userService.join(dto);
//    }
//
//    @Autowired
//    private PlanService planService;
//
//    @Test
//    public void makeaplan(){
//        PlanDTO dto =PlanDTO.builder()
//                .user_code(3L)
//                .pno(1L)
//                .priority(10L)
//                .title("전기뱀장어")
//                .description("이해하자")
//                .location("장충동")
//                .category("족발")
//                .share("공개")
//                .start(LocalDateTime.parse("2002-01-23T09:30:00"))
//                .end(LocalDateTime.parse("2002-01-23T09:30:00"))
//                .build();
//        System.out.println(planService.makeAPlan(dto));
//
//
//    }
//
////    @Test
//    public void hundredmakeplan(){
//        IntStream.rangeClosed(1,100).forEach(i->{
//            PlanDTO dto =PlanDTO.builder()
//                    .user_code((long)i)
//                    .title("테스트1"+i)
//                    .description("T2EST_1"+i)
//                    .location("TES2T"+i+"번지")
//                    .start(LocalDateTime.parse("2002-01-23T09:30:00"))
//                    .end(LocalDateTime.parse("2002-01-23T09:30:00"))
//                    .build();
//            System.out.println(planService.makeAPlan(dto));
//        });
//    }
//    @Autowired
//    private FriendRepository friendRepository;
//    @Test
//    public void friendTableTestWithoutDto(){
//        User user1 = User.builder()
//                .code(2L)
//                .build();
//        User user2 = User.builder()
//                .code(3L)
//                .build();
//        Friend friend = Friend.builder()
//                .response(user1)
//                .request(user2)
//                .build();
//        friendRepository.save(friend);
//
//    }
//
//    @Autowired
//    private FriendService friendService;
//
//    @Test
//    public void makeAFriendByDTO(){
//        FriendDTO friendDTO = FriendDTO.builder()
//                .request(4L)
//                .response(3L)
//                .build();
//        System.out.println(friendService.beAFriend(friendDTO));
//    }
//
//    @Autowired
//    private CheckListRepository checkListRepository;
//
//    @Test
//    public void checkListWithOutDTO(){
//        User user = User.builder()
//                .code(100L)
//                .build();
//        Plan plan = Plan.builder()
//                .pno(200L)
//                .build();
//        CheckList checkList = CheckList.builder()
//                .todo("기본적인 setting 은 완료 시키기")
//                .user(user)
//                .plan(plan)
//                .build();
//        checkListRepository.save(checkList);
//    }
//
//    @Autowired
//    private CheckListService checkListService;
//
//    @Test
//    public void confirmCheckListByDto(){
//        CheckListDTO dto = CheckListDTO.builder()
//                .code(1L)
//                .pno(2L)
//                .todo("Contents of thisq plan")
//                .done("Successqwwqwq")
//                .build();
//        System.out.println(checkListService.showCheckList(dto));
//    }
//
//    @Autowired
//    private QuestionService questionService;
//
//    @Test
//    public void makeAQuestionaryByDto(){
//        IntStream.rangeClosed(1,100).forEach(i->{
//            QuestionDTO dto = QuestionDTO.builder()
//                    .context("Questionary today  "+i)
//                    .build();
//            System.out.println(questionService.insertQuestionTableTest(dto));
//        });
//
//    }
//
//    @Autowired
//    private AnswerService answerService;
//
//    @Test
//    public void checkSelfCheck(){
//        AnswerDTO dto = AnswerDTO.builder()
//                .qno(5L)
//                .code(5L)
//                .answer("good")
//                .build();
//        System.out.println(answerService.selfcheckinggood(dto));
//    }
//
//    @Autowired
//    private ShareService shareService;
//
//    @Test
//    public void shareTest(){
//        ShareDTO dto = ShareDTO.builder()
//                .pno(2L)
//                .code(3L)
//                .build();
//        System.out.println(shareService.shareTest(dto));
//    }

//    @Test
//    public void decrut() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
//        String user = userService.dec(35L);
//        System.out.println(user);
//
//        User user1 = userRepository.findByCode(35L);
//        System.out.println(CryptoUtil.decryptAES256(user1.getId(), "Id"));
//    }



//    @Test
//    public  void EncodingTest(){
//        String password = "ssw0304";
//        String enPw = passwordEncoder.encode(password);
//        System.out.println("result : "+ enPw);
//    }

    @Test
    public void idCheckTest(){
        String testid = "socket2";
        String testpw = "Qwer!23";
        Object user = userRepository.findById(testid);
        boolean idex = false;
        boolean pwex = false;
        Object [] result = (Object[]) user;
        String id = (String) result[0];
        String pw = (String) result[1];
        if(id!=null){
            idex =true;
            if(BCrypt.checkpw(testpw,pw)){
                pwex = true;
            }
        }
        System.out.println(id);
        System.out.println(idex);
        System.out.println(pwex);
        System.out.println((String)result[2]);
    }

//    @Test
    public void allemailre() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        List<Object []> list = userRepository.getalldata();
        String inemail = "socket2@naver.com";
        String inpw = "Qwer!34";
        boolean exists = false ;
        boolean pwcollect = false;
        String nick = null ;
        for(Object [] i:list){
            String em = (String) i[0];
            String pw = (String) i[1];
            nick = (String) i[2];
            if(CryptoUtil.decryptAES256(em,"Email").equals(inemail)){
                System.out.println(CryptoUtil.decryptAES256(em,"Email"));
                exists =true;
                if(BCrypt.checkpw(inpw,pw)) {
                    pwcollect = true;
                }
            }
        }
        System.out.println(nick);
        System.out.println(exists);
        System.out.println(pwcollect);
    }
//    @Test
    public void findemailwithmasking() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        String testNick = "socket22";
        // 자료형이 다양하니 Object 로 받는다.
        Object getIdAndEmail = userRepository.findByNick(testNick);

        // id ,  값과  email 값을 받을 것이기 때문에 배열로 저장
        Object[] result = (Object[]) getIdAndEmail;

        String email = (String) result[1];

        // Email 은 암호화가 되어있음으로 복호화를 실행
        email = CryptoUtil.decryptAES256(email, "Email");

        // 복호화를 확인
        System.out.println("마스킹 되기 전 Email : "+email);

        // @를 중심으로 나누어 배열로 저장
        String[] emailFront = email.split("@");
        int len_f = emailFront[0].length();

        String maskingComplete = null;


        if(len_f>3){
            String noMask = emailFront[0].substring(0,3);
            // 값을 확인
//            System.out.println(noMask);
            String Masking = "*".repeat(len_f-3);

            // 값을 확인
//            System.out.println(Masking);
            maskingComplete = noMask+Masking;
        }

        int len_b = emailFront[1].indexOf(".");
        String masking_b = "*".repeat(len_b);
        String other = emailFront[1].substring(len_b);

        maskingComplete = maskingComplete+"@"+masking_b+other;
        System.out.println("마스킹 되고 난 후 Email : "+maskingComplete);
    }

    @Autowired
    private FindService findService;

//    @Test
//    public void findEmailService() throws Exception {
//        String testNick = "socket22";
//        String result = findService.findByNick(testNick);
//        System.out.println(result);
//
//    }
//    @Test
    public void SerialMaker(){
        ForJoin forJoin = new ForJoin();
        String result = forJoin.excuteGenerate();
        System.out.println(result);
    }
    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ForJoin forJoin;

//    @Test
    public void SendSerialByEmail() throws MessagingException {
        String serialKey = forJoin.excuteGenerate();
        String result = "Your certification Code is : "+serialKey+"\n Plaese Insert BlanK For Join Us";
        emailSenderService.sendMail("Here is A make a Plan!","@naver.com",result);
    }

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void contexttest(){
        String context = "What is your favorite food";
        List<Long> getQnoListFromQuestion = questionRepository.findByContext(context);
        for(Long i:getQnoListFromQuestion){
            System.out.println(i);
        }
    }

//    @Test
    public void findIdByanswer() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        String context = "What is your favorite food";
        String answer = "사과";
//
        List<Long> getQnoListFromQuestion = questionRepository.findByContext(context);
        Question codeResult = answerRepository.getQnoByUseContext(answer);
        Long resultQnoByAnswer = codeResult.getQno();
        System.out.println(resultQnoByAnswer);

        String id = null;
        String email = null;
        for(Long qnoResult:getQnoListFromQuestion) {
            if(qnoResult==resultQnoByAnswer){
                Question question = Question.builder()
                        .qno(resultQnoByAnswer)
                        .build();
                User userInfoByUsingQno = answerRepository.getCodeByUseQno(question);
                Long userCode = userInfoByUsingQno.getCode();

                Object userIdAndEmail = userRepository.findUserIdAndEmailByUserCode(userCode);
                Object[] resultUserIdAndEmail = (Object[]) userIdAndEmail;
                id = (String)resultUserIdAndEmail[0];
                email = (String)resultUserIdAndEmail[1];
                email = CryptoUtil.decryptAES256(email,"Email");

            }
        }
        System.out.println(id);
        System.out.println(email);
    }
//    @Test
    public void returnQnoUsingAnwser(){
        String answer = "사과";
        Question codeResult = answerRepository.getQnoByUseContext(answer);
        Long resultQnoByAnswer = codeResult.getQno();
        System.out.println(resultQnoByAnswer);
        Question question = Question.builder()
                .qno(resultQnoByAnswer)
                .build();
        User b = answerRepository.getCodeByUseQno(question);
        System.out.println(b);
    }
    @Test
    public void returnTestUser(){
        Long a = 57L;
        Question question = Question.builder()
                .qno(a)
                .build();
        User b = answerRepository.getCodeByUseQno(question);
        System.out.println(b.getCode());
    }

    @Test
    public void idAndEmailReturnCodeTest() throws Exception {
        String userInfo = "singsiuk";
        Map<String, Object> result = findService.pwFindResult(userInfo);
        System.out.println(result.get("userInfo"));
    }

//    @Test
    public void createrandomcode(){
        ForFindPw code = new ForFindPw();
        for(int i = 0 ; i< 10 ; i++) {
            String result = code.excuteGenerate();
            System.out.println(result);
        }
    }
//    @Test
    public void middleCheck() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, MessagingException {
        String userInfo = "singsiuk";
        String context = "What is your favorite food";
        String answer = "헤헿맛있는피자";
        String birthday = "2022-02-11";

        Map<String, Object> result = new HashMap<>();

        result = findService.resultOfPwfind(userInfo,answer,context,birthday);
        System.out.println(result.get("newPassword"));
        System.out.println(result.get("UserEmail"));
        System.out.println(result.get("UserId"));
        String pwResult = (String) result.get("newPassword");
        String userEmail = (String) result.get("UserEmail");
        BCrypt.hashpw(pwResult,BCrypt.gensalt());

        String message = "Your new Password  : "+result.get("newPassword")+"\n" +
                "\n Plaese Insert BlanK For Join Us";
        userRepository.changingPw(BCrypt.hashpw(pwResult,BCrypt.gensalt()), (String) result.get("UserId"));
        emailSenderService.sendMail("Here is New Password From Make Your Plan", userEmail, message);
    }
//    @Test
    public void finalTestOfFindingpw() throws InvalidAlgorithmParameterException, MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        String userInfo = "singsiuk";
        String context = "What is your favorite food";
        String answer = "헤헿맛있는피자";
        String birthday = "2022-02-11";

        findService.resultOfPwfind(userInfo, answer,context, birthday);
    }
    @Autowired
    private EditService editService;

    @Test
    public void codetest(){
        Long code = 1L;
        User userCode = User.builder()
                .code(code)
                .build();
        String userAnswer = answerRepository.getAnswerForEditInfo(userCode);
        System.out.println(userAnswer);
    }

    @Test
    public void codeteest2() {
        Long a = 57L;
        Question question = Question.builder()
                .qno(a)
                .build();
        User b = answerRepository.getCodeByUseQno(question);
        System.out.println(b.getCode());

    }

    @Test
    public void codetest3(){
        Long a = 59L;
        String userContext = editService.bringQuestionInfo(a);
        System.out.println(userContext);

    }
//    @Test
//    public void getpwtest(){
//        String nick = "ssw0304";
//        String pw = "wrongPasswordTest";
//        boolean result = editService.bringPwForRetire(pw,nick);
//        System.out.println(result);
//    }

//    @Test
//    public void ChangeUserInfoTest(){
//        String nick = "싱시";
//        String nickCh = "테스트1맨";
//        String pw = "!234Qwe1r";
//        String gender = "m";
//        String birthday = "1099-09-09";
//        boolean result = editService.changeUserInfo(nick, nickCh, pw, gender, birthday);
//        System.out.println(result);
//
//        User result1 = userRepository.bringAllData();
//        System.out.println(result1.getCode());
//        System.out.println(result1.getBirthday());
//        System.out.println(result1.getGender());
//        System.out.println(result1.getNick());
//        System.out.println(result1.getBirthday());
//
//        LocalDateTime atime = LocalDateTime.now();
//        int a = userRepository.unScribeTime(atime, nick);
//        System.out.println(atime);
//    }

//    @Test
//    public void updatetest(){
//        Long userCode = 4L;
//        String answer = "비타민씨";
//        User code = User.builder()
//                .code(userCode)
//                .build();
//        String context ="QQ";
//        // context 를 수정하기 위해서 AnswerRepository 를 사용해서 qno 를 return
//        Question questionQno = answerRepository.getQnoForEditInfo(code);
//        Long qno = questionQno.getQno();
//
//
//        //answer 를 수정하기 위한 method
//        int resultAnswer = answerRepository.updateUserAnswer(code, answer);
//
//        System.out.println(resultAnswer);
//
//
//
//        // context 를 수정하기 위해서 qno 와  context  를 입력 받는 method
//        int resultQuestion = questionRepository.updateUserContext(qno, context);
//        System.out.println(resultQuestion);
//    }

    @Autowired
    private TestLoginService testLoginService;

    @Test
    public void validTest() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        loginDTO dto = loginDTO.builder()
                .email("singsiuk")
                .pw("Qwer!234")
                .build();
        Map<String, Object> loginResult = testLoginService.forlogin(dto);
        System.out.println(loginResult.get("accountValid"));
        System.out.println(loginResult.get("userCode"));
        System.out.println(loginResult.get("userNick"));
        System.out.println(loginResult.get("userStatus"));
        System.out.println(loginResult.get("validCheck"));

    }



}
