package com.team.team_project.controller;


import com.team.team_project.dto.findDTO.ByNickDTO;
import com.team.team_project.dto.findDTO.ByQandAandBirthdayDTO;
import com.team.team_project.service.email.EmailSenderService;
import com.team.team_project.service.find.FindService;
import com.team.team_project.service.validationHandling.ValidateHandling;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@Controller
@Log4j2
public class FindController {
    @Autowired
    private FindService findService;

    @GetMapping("/find/emailPage")
    public void emailFindPage(){
    }


    @GetMapping("/find/byNickName")
    public void byNick(){
    }

    @Autowired
    private ValidateHandling validateHandling;

    @PostMapping("/find/result/byNick")
    public String findingByNick(@Valid ByNickDTO dto, Errors errors, Model model) throws Exception {
        Map<String, Object> result = findService.findByNick(dto);

        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            Map<String, String> validatorResult = validateHandling.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "find/byNickName";
        }

        String exceptionEmerge = (String) result.get("exceptionEmerge");
        if(exceptionEmerge==null){
            model.addAttribute("email",(String) result.get("emailMaskingResult"));
            model.addAttribute("id",(String) result.get("idMaskingResult"));
        }else{
            model.addAttribute("exceptionMessage",exceptionEmerge);
            return "/find/byNickName";
        }

        return "/find/result/byNick";
    }


    @GetMapping("/find/byQuestion")
    public void byQuestion(){
    }
    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/find/result/byQuestion")
    public String temp(@Valid ByQandAandBirthdayDTO dto, Errors errors, Model model) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, MessagingException {
        Map<String, Object> result = findService.findUserIdAndEmailByQuestionAndAnswer(dto);
        String message = "Your Id is : "+result.get("id")+"\n" +
                "Your Email is : " +result.get("email")+
                "\n Plaese Insert BlanK For Join Us";
        String userEmail = (String) result.get("email");
        System.out.println(userEmail);
        emailSenderService.sendMail("Your Id From Making a Plan", userEmail,message);
        return "/find/result/byQuestion";
    }

    @GetMapping("/find/pwByInfo")
    public void findPwByInfo(){
    }
    @PostMapping("/find/result/byEmailOrId")
    public String changePassword__SendingEmail(String userInfo,@Valid ByQandAandBirthdayDTO dto, Errors errors, Model model) throws InvalidAlgorithmParameterException, MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            Map<String, String> validatorResult = validateHandling.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "/find/pwByInfo";
        }

        Map<String ,Object> checkingComplete = findService.resultOfPwfind(userInfo,dto);
        boolean allResult = (boolean)checkingComplete.get("allResult");

        if(allResult==true) {
            findService.PwChangeResult(userInfo, dto);
        }else{
            if((boolean)checkingComplete.get("result")){
                model.addAttribute("infoErrorMessage",(String) checkingComplete.get("infoErrorMessage"));
                return "/find/pwByInfo";
            }else{
                model.addAttribute("validErrorMessage",(String) checkingComplete.get("validErrorMessage"));
                return "/find/pwByInfo";
            }
        }

        return "/find/result/byEmailOrId";
    }
}
