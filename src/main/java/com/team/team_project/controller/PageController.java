package com.team.team_project.controller;

import com.team.team_project.dto.AnswerDTO;
import com.team.team_project.dto.QuestionDTO;
import com.team.team_project.dto.UserDTO;
import com.team.team_project.dto.passwordDTO.PasswordDTO;
import com.team.team_project.service.QuestionService;
import com.team.team_project.service.AnswerService;
import com.team.team_project.service.UserService;
import com.team.team_project.service.login.LoginService;
import com.team.team_project.service.unscribe.UnscribeService;
import com.team.team_project.service.validationHandling.ValidateHandling;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/checkplan")
public class PageController {

    private String authkey ;



    @GetMapping("mainpage")
    public void mainpage(PasswordDTO dto){
    }

    @GetMapping("join")
    public String disJoin(UserDTO dto,
                          AnswerDTO adto,
                          QuestionDTO qdto){
        return "checkplan/join";

    }

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private ValidateHandling validateHandling;

    @PostMapping("joining")
    public ModelAndView joining(@Valid UserDTO dto,
                                Errors errors,
                                @Valid QuestionDTO qdto ,
                                Errors qerrors,
                                @Valid AnswerDTO adto ,
                                Errors aerrors,
                                Model model,
                                String pwCheck
    ) throws Exception {
        // 유효성 통과 못한 필드와 메시지를 핸들링
        if(errors.hasErrors()){
            model.addAttribute("dto", dto);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = validateHandling.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return new ModelAndView("checkplan/join");
    }
        if(qerrors.hasErrors()){
            model.addAttribute("dto",qdto);

            Map<String, String> validatorResult = validateHandling.validateHandling(qerrors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            return new ModelAndView("checkplan/join");
        }
        if(aerrors.hasErrors()){
            model.addAttribute("dto", adto);

            Map<String, String> validatorResult = validateHandling.validateHandling(aerrors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            return new ModelAndView("checkplan/join");
        }

        String email = dto.getEmail();
        String id = dto.getId();
        String pw = dto.getPw();
        String nick = dto.getNick();

        Map<String, String> result = validateHandling.joinValidation(email, id, nick, pw, pwCheck);

        String validCheck = result.get("result");

        if(validCheck.equals("okay")) {

            userService.join(dto);
            questionService.insertQuestionTableTest(qdto);
            AnswerDTO answerDTO = AnswerDTO.builder()
                    .code(dto.getCode())
                    .qno(qdto.getQno())
                    .answer(adto.getAnswer())
                    .build();
            answerService.selfcheckinggood(answerDTO);
        }else{
            if(result.get("emailMessage")!=null){
                model.addAttribute("emailMessage",result.get("emailMessage"));
            }
            if(result.get("idMessage")!=null){
                model.addAttribute("idMessage",result.get("idMessage"));
            }
            if(result.get("nickMessage")!=null){
                model.addAttribute("nickMessage",result.get("nickMessage"));
            }
            if(result.get("pwMessage")!=null){
                model.addAttribute("pwMessage",result.get("pwMessage"));
            }
            return new ModelAndView("checkplan/join");
        }
            return new ModelAndView("redirect:/checkplan/mainpage");
    }





    @Autowired
    private LoginService loginService;

    /**
     * Login 을 하기 위한 method ===================================================================
     */
    @PostMapping("login")
    public String login(String account,
                        @Valid PasswordDTO dto,
                        Errors errors,
                        Model model,
                        HttpSession session)
            throws Exception


    {
        /**
         *  Password dto가 유효한지 확인하는 method -----------------------------------------------
         */
        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            Map<String, String> validatorResult = validateHandling.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "checkplan/mainpage";
        }
        /**
         *  Password dto가 유효한지 확인하는 method -----------------------------------------------
         */

        String url = null;

        Map<String, Object> loginResult = loginService.login(account,dto);

        /**
         * login
         *  공통 값
         *  key = loginResult
         *
         *  true ----------
         *  result.put("code",(Long)info[0]);
         *  result.put("pw",(String)info[2]);
         *  result.put("nick",(String)info[3]);
         *  result.put("status",(String)info[4]);
         *  result.put("birthday",(LocalDate)info[5]);
         *
         *  false ------------
         *  key = errorMessage
         *  key = pwError
         */

        boolean isLogin = (boolean) loginResult.get("loginResult");

        // isLogin ---> ture : 로그인 값이 유효 하다면
        if(isLogin)
        {
            session.setAttribute("code", (Long)loginResult.get("code"));
            session.setAttribute("nick",(String)loginResult.get("nick"));
            if(loginResult.get("status").equals("7day"))
            {
                url = "checkplan/forUser/retire/UnscribingCancle";
            }
            else if(loginResult.get("status").equals("회원"))
            {
                url =  "checkplan/logincomplete";
            }
        }
        else
        {
            String pwError = (String)loginResult.get("pwError");
            /**
             *  pwError 가 존재 ---> pw 가 틀렸음
             *  pwError 가 존재 하지 않음  ---> 입력한 id 혹은 email 이 틀렸을 때
             */
            if(pwError==null)
            {
                model.addAttribute("accountError", (String) loginResult.get("errorMessage"));
                return "checkplan/mainpage";
            }
            else
            {
                model.addAttribute("passwordError", (String) loginResult.get("pwError"));
                return "checkplan/mainpage";
            }
        }
        return url;
    }

    @GetMapping("logincomplete")
    public void afterLoginmainPage(){

    }


}
