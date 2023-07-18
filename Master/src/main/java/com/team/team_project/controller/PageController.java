package com.team.team_project.controller;

import com.team.team_project.dto.AnswerDTO;
import com.team.team_project.dto.QuestionDTO;
import com.team.team_project.dto.UserDTO;
import com.team.team_project.dto.PasswordDTO.PasswordDTO;
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
            model.addAttribute("qdto",dto);

            Map<String, String> validatorResult = validateHandling.validateHandling(qerrors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            return new ModelAndView("checkplan/join");
        }
        if(aerrors.hasErrors()){
            model.addAttribute("adto", adto);

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
    private UnscribeService unscribeService;

    @Autowired
    private LoginService loginService;


    @PostMapping("login")
    public String login(String account,@Valid PasswordDTO dto, Errors errors, Model model, HttpSession session) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {

        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            Map<String, String> validatorResult = validateHandling.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "checkplan/mainpage";
        }

        String url = null;

        Map<String, Object> loginResult = loginService.forloginUpdate(account,dto);

        boolean complete = (boolean) loginResult.get("loginResult");


        if(complete==true){
            session.setAttribute("code", (Long)loginResult.get("code"));
            session.setAttribute("nick",(String)loginResult.get("nick"));
            if(loginResult.get("status").equals("7day")){
                url = "checkplan/forUser/retire/UnscribingCancle";
            }else if(loginResult.get("status").equals("회원")){
                url =  "checkplan/logincomplete";
            }
        }else if(complete==false) {
            if ((boolean) loginResult.get("result") == false) {
                model.addAttribute("accountError", (String) loginResult.get("validErrorMessage"));
                return "checkplan/mainpage";
            } else if ((boolean) loginResult.get("result") == true) {
                model.addAttribute("passwordError", (String) loginResult.get("passwordError"));
                return "checkplan/mainpage";
            }
        }
        return url;
    }

    @GetMapping("logincomplete")
    public void afterLoginmainPage(){

    }


}
