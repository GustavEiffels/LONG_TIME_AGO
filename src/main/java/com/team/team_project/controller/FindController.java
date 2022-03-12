package com.team.team_project.controller;


import com.team.team_project.dto.findDTO.NickDTO;
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
import javax.validation.Valid;
import java.util.Map;

@Controller
@Log4j2
public class FindController {
    @Autowired
    private FindService findService;

    @Autowired
    private ValidateHandling validateHandling;

    @GetMapping("/find/emailPage")
    public void emailFindPage(){
    }


    @GetMapping("/find/byNickName")
    public void byNick(){
    }



    @PostMapping("/find/result/byNick")
    public String findingByNick(@Valid NickDTO dto, Errors errors, Model model) throws Exception {

        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            Map<String, String> validatorResult = validateHandling.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "find/byNickName";
        }
        Map<String, Object> result = findService.findByNick(dto);

        String exceptionEmerge = (String) result.get("exceptionMessage");
        if(exceptionEmerge==null){
            model.addAttribute("email",(String) result.get("email"));
            model.addAttribute("id",(String) result.get("id"));
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





    @PostMapping("/find/result/byEmailOrId")
    public String changePassword__SendingEmail(String userInfo,@Valid ByQandAandBirthdayDTO dto, Errors errors, Model model) throws Exception {

        /**
         * dto 에 유효성 검사 method ----------------------------------------------------------------------
         */
        if(errors.hasErrors()){
            model.addAttribute("dto", dto);
            Map<String, String> validatorResult = validateHandling.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "/find/pwByInfo";
        }
        /**
         * dto 에 유효성 검사 method ----------------------------------------------------------------------
         */



        Map<String ,Object> validResult = findService.resultAccountValid(userInfo,dto);

        // map 에 email 값이 들어있는 경우는 모든 값이 유효할 때임으로
        if(validResult.get("email")!=null) {
            findService.sendNewPassword(userInfo, dto);
        }else{
            if(validResult.get("infoErrorMessage")!=null){
                model.addAttribute("infoErrorMessage",(String) validResult.get("infoErrorMessage"));
                System.out.println((String) validResult.get("infoErrorMessage"));
                return "/find/pwByInfo";
            }else{
                model.addAttribute("validErrorMessage",(String) validResult.get("errorMessage"));
                System.out.println((String) validResult.get("ErrorMessage"));
                return "/find/pwByInfo";
            }
        }

        return "/find/result/byEmailOrId";
    }
}
