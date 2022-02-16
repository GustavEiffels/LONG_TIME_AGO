package com.team.team_project.controller;

import com.team.team_project.dto.AnswerDTO;
import com.team.team_project.dto.QuestionDTO;
import com.team.team_project.dto.UserDTO;
import com.team.team_project.repository.UserRepository;
import com.team.team_project.service.CryptoUtil;
import com.team.team_project.service.QuestionService;
import com.team.team_project.service.AnswerService;
import com.team.team_project.service.UserService;
import com.team.team_project.service.email.login.loginService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
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
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/checkplan")
public class PageController {

    private String authkey ;



    @GetMapping("mainpage")
    public void mainpage(){
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

    @PostMapping("joining")
    public ModelAndView joining(@Valid UserDTO dto,
                                Errors errors,
                                QuestionDTO qdto ,
                                Errors qerrors,
                                AnswerDTO adto ,
                                Errors aerrors,
                                Model model
    ) throws Exception {
        // 유효성 통과 못한 필드와 메시지를 핸들링
        if(errors.hasErrors()){
            model.addAttribute("dto", dto);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return new ModelAndView("checkplan/join");
    }
        if(qerrors.hasErrors()){
            model.addAttribute("qdto",dto);

            Map<String, String> qvalidatorResult = questionService.qvalidateHandling(qerrors);
            for(String key : qvalidatorResult.keySet()){
                model.addAttribute(key, qvalidatorResult.get(key));
            }
            return new ModelAndView("checkplan/join");
        }
        if(aerrors.hasErrors()){
            model.addAttribute("adto", adto);

            Map<String, String> avalidatorResult = answerService.avalidateHandling(aerrors);
            for(String key : avalidatorResult.keySet()){
                model.addAttribute(key, avalidatorResult.get(key));
            }
            return new ModelAndView("checkplan/join");
        }

        userService.join(dto);
        questionService.insertQuestionTableTest(qdto);
        AnswerDTO answerDTO = AnswerDTO.builder()
                .code(dto.getCode())
                .qno(qdto.getQno())
                .answer(adto.getAnswer())
                .build();
        answerService.selfcheckinggood(answerDTO);
        return new ModelAndView ("redirect:/checkplan/mainpage");
    }




    @Autowired
    private loginService loginService;


    @PostMapping("login")
    public String login(String email, String pw, HttpSession session) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Map<String, Object> fronLogin = loginService.forlogin(email, pw);
        boolean exists = (Boolean) fronLogin.get("exists");
        boolean pwcollect = (Boolean) fronLogin.get("pwcollect");
        String nick = (String) fronLogin.get("nick");
        String url = url = "checkplan/mainpage";
        if(exists==true){
            if(pwcollect==true){
                url = "checkplan/logincomplete";
                session.setAttribute("nick",nick);
            }
        }
        if(exists==false){
            if(pwcollect=false){
                throw new IllegalArgumentException("Invalid Password");
            }
            throw new IllegalArgumentException("Invalid Email or Id");
        }

        return url;

    }
    @GetMapping("logincomplete")
    public void afterLoginmainPage(){

    }


}
