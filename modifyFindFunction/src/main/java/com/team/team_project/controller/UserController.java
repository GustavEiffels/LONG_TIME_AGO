package com.team.team_project.controller;
import com.team.team_project.dto.passwordDTO.PasswordDTO;
import com.team.team_project.service.edit.EditService;
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
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/checkplan/forUser/")
public class UserController {
    @GetMapping("editUserInfo")
    public void editUserInfo(){
    }
    @Autowired
    private EditService editService;

    @Autowired
    private ValidateHandling validateHandling;

    @PostMapping("Logout")
    public String logOutmethod(HttpSession session){
        String userNick = (String)session.getAttribute("nick");
        unscribeService.userModDateUpdate(userNick);
        session.invalidate();
        return "redirect:/checkplan/mainpage";
    }


    @PostMapping("editing")
    public String editingUserInfo(HttpSession session ,
                                String nick,
                                String pw,
                                String answer,
                                Model model,
                                String pwCheck,
                                String gender,
                                String birthday,
                                String context){



        /***
         * session 에서 현재 nick name 을 받아온다 .
         */

        String currentNick = (String)session.getAttribute("nick");


        /***
         * null 처리를 해야한다 .
         */
        System.out.println(birthday);

        Map<String, String> nullProcess = editService.parameterNullProcess(session, nick, pw, answer, gender, birthday, context);

        if(nullProcess.get("error").equals("notExists")) {
             nick = nullProcess.get("nick");
             pw = nullProcess.get("pw");
            answer = nullProcess.get("answer");
            birthday = nullProcess.get("birthday");
            gender = nullProcess.get("gender");
            context = nullProcess.get("context");

            Map<String, Object> result = editService.changeUserInfo(session, currentNick, nick, pw, pwCheck, gender, birthday, answer, context);

            if ((boolean) result.get("result")==true){
                session.setAttribute("nick", nick);
            }else{
                if((String)result.get("nickErrorMessage")!=null){
                    model.addAttribute("nickErrorMessage",(String)result.get("nickErrorMessage"));
                }
                if((String)result.get("pwErrorMessage")!=null){
                    model.addAttribute("pwErrorMessage",(String)result.get("pwErrorMessage"));
                }
                return "checkplan/forUser/editUser";
            }


        }else if(nullProcess.get("error").equals("exists")){
            if((String)nullProcess.get("nickValidError")!=null){
                model.addAttribute("nickValidError",(String)nullProcess.get("nickValidError"));
            }
            if((String)nullProcess.get("pwValidError")!=null){
                model.addAttribute("pwValidError",(String)nullProcess.get("pwValidError"));
            }
            if((String)nullProcess.get("answerValidError")!=null){
                model.addAttribute("answerValidError",(String)nullProcess.get("answerValidError"));
            }
            model.addAttribute("errorMessage",nullProcess.get("errorMessage"));

            return "checkplan/forUser/editUser";
        }
        return "checkplan/forUser/editUserInfo";
    }

    @GetMapping("editUser")
    public void editUser(HttpSession session) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        String nick = (String) session.getAttribute("nick");
        Map<String, Object> result = editService.bringUserInfo(nick);
        String userId = (String) result.get("userId");
        String userEmail = (String)result.get("userEmail");
        String userGender = null;
        userGender = (String)result.get("userGender");
        Long userCode = (Long)result.get("userCode");
        String userAnswer = editService.bringAnswerInfo(userCode);
        System.out.println(userAnswer);
        String userContext = editService.bringQuestionInfo(userCode);

        if(userGender.equals("m")==true){
            userGender="Male";
        }else if(userGender.equals("f")==true){
            userGender="Female";
        }
        LocalDate userBirthday = (LocalDate) result.get("userBirthday");


        session.setAttribute("userId",userId);
        session.setAttribute("userEmail",userEmail);
        session.setAttribute("userBirthday",userBirthday);
        session.setAttribute("userGender",userGender);
        session.setAttribute("userAnswer",userAnswer);
        session.setAttribute("userContext",userContext);


    }




    @GetMapping("retire/retire")
    public void retire(){
    }

    @PostMapping("retire/UnscribingChecking")
    public String unscribingChecking(@Valid PasswordDTO dto, Errors errors,Model model, HttpSession session){
        String url = null;
        String userNick = (String) session.getAttribute("nick");
        String pw = dto.getPw();

        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            Map<String, String> validatorResult = validateHandling.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "checkplan/forUser/retire/retire";
        }

        boolean userPwCollectOrNot = editService.bringPwForRetire(pw, userNick);
        if(userPwCollectOrNot==true){
            url = "checkplan/forUser/retire/UnscribingDoing";
        }else if(userPwCollectOrNot==false){

            model.addAttribute("errorMessage","Wrong Password! , Please Check again");
            return  "checkplan/forUser/retire/retire";
        }
        return url;
    }
    @Autowired
    private UnscribeService unscribeService;


    @GetMapping("retire/UnscribingComplete")
    public void unscribingComplete(HttpSession sessioin){
        String userNick = (String) sessioin.getAttribute("nick");
        String userStatus = "7day";
        boolean userUnscribeComplete = unscribeService.userUnscribecomplete(userStatus, userNick);
        if(userUnscribeComplete==true){
            unscribeService.userModDateUpdate(userNick);
            sessioin.invalidate();
        }
    }
    @PostMapping("unScribeCancle")
    public String unScribeCancle(HttpSession session, @Valid PasswordDTO dto,Errors errors, Model model, String pwCheck){
        Long code = (Long) session.getAttribute("code");
        String url = null;

        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            Map<String, String> validatorResult = validateHandling.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "checkplan/forUser/retire/UnscribingCancle";
        }
        /***
         * dto 에서 pw 를 받아온다.
         */
        String pw = dto.getPw();

        int unScribeCancleResult = editService.unSubScribeCancle(pw, pwCheck, code);
        if(unScribeCancleResult>0){
            url = "redirect:/checkplan/mainpage";
        }else{
            model.addAttribute("ErrorMessage", "The passwords entered do not match each other.");
            return "checkplan/forUser/retire/UnscribingCancle";
        }
        return url;
    }

}
