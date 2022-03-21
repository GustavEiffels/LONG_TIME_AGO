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

        unscribeService.updateModDate((Long)session.getAttribute("code"));
        session.invalidate();
        return "redirect:/checkplan/mainpage";
    }

    /**
     * 회원 정보 변경 page 에서 변경하는 method =============================================================================
     */
    @PostMapping("editing")
    public String editingUserInfo(HttpSession session ,
                                String nick,
                                String pw,
                                String answer,
                                Model model,
                                String pwCheck,
                                String gender,
                                String birthday,
                                String context)
    {
        /**
         *  paramValid : key---> isValid
         *  true --> error 존재 x
         *  false --> error 존재함
         */
        Map<String, Object> paramValid = editService.parameterValidCheck
                (
                session,
                nick,
                pw,
                answer,
                gender,
                birthday,
                context
                );

        // error 가 존재하지 않음 : 변수 전체는 유효한 값 -----------------------------------------------------------------
        if((boolean)paramValid.get("isValid"))
            {
             nick = (String)paramValid.get("nick");
             pw = (String)paramValid.get("pw");
            answer = (String)paramValid.get("answer");
            birthday = (String)paramValid.get("birthday");
            gender = (String)paramValid.get("gender");
            context = (String)paramValid.get("context");

            // session 에서 nick name 을 받아옴 : 바뀔 nick 과 비교하기 위해서 ----------------------------------------------
            String currentNick = (String)session.getAttribute("nick");

                /**
                 * @return --------------------------------------------------------------------
                 * key
                 * result ---> true : 값을 정상적으로 전달
                 * result ---> false : error 발생
                 *
                 * error
                 * key
                 * nickErrorMessage
                 * pwErrorMessage
                 */
            Map<String, Object> result = editService.changeUserInfo
                    (
                            session,
                            currentNick,
                            nick,
                            pw,
                            pwCheck,
                            gender,
                            birthday,
                            answer,
                            context
                    );
            // 정상적으로 회원정보가 변경 됨 -----------------------------------------------
            if ((boolean) result.get("result"))
                {
                session.setAttribute("nick", nick);
                }

            // error 가 발생했을 때 ------------------------------------------------------
            // 닉네임 중복, 비밀번호 비밀번호 체크 불일치
            else
                {

                    // 닉네임이 중복 되었을 경우 ----------------------------
                    if(result.containsKey("nickErrorMessage"))
                        {
                        model.addAttribute("nickErrorMessage",(String)result.get("nickErrorMessage"));
                        }

                    // 비밀번호와 비밀번호 확인 값이 일치하지 않을 경우 -----------------------------
                    if(result.containsKey("pwErrorMessage"))
                        {
                        model.addAttribute("pwErrorMessage",(String)result.get("pwErrorMessage"));
                        }

                    return "checkplan/forUser/editUser";
                }


        }
        // parameter 값이 유효한 값이 아닐 때  ------------------------------------------------------
        else if(!(boolean)paramValid.get("isValid"))
            {
                // 닉네임 유효성을 불만족 ------------------
                if(paramValid.containsKey("nickValidError"))
                    {
                    model.addAttribute("nickValidError",(String)paramValid.get("nickValidError"));
                    }


                // 비밀번호 유효성을 불만족----------
                if(paramValid.containsKey("pwValidError"))
                    {
                    model.addAttribute("pwValidError",(String)paramValid.get("pwValidError"));
                    }


                // 질문에 대한 답 불만족 ----------
                if(paramValid.containsKey("answerValidError"))
                    {
                    model.addAttribute("answerValidError",(String)paramValid.get("answerValidError"));
                    }
                    // 질문에 대한 답 불만족 ----------


                // 질문이 변경되었는데 답을 입력하지 않았을 때 -----------
                if(paramValid.containsKey("errorMessage"))
                    {
                    model.addAttribute("errorMessage", paramValid.get("errorMessage"));
                    }

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


    // ====================================================================== 회원 탈퇴 기능

    // ------------- 탈퇴 page 로 이동하는 method
    @GetMapping("retire/retire")
    public void retire(){
    }

    /** 회원 탈퇴 시 비밀번호를 입력 받아, 탈퇴 할 것인지 확인 받는 method -------------------------------------------
     */
    @PostMapping("retire/UnscribingChecking")
    public String unSubCheck(@Valid PasswordDTO dto, Errors errors,Model model, HttpSession session)
    {
        // ---- 유효성 검사 ----------
        if(errors.hasErrors())
        {
            model.addAttribute("dto", dto);
            Map<String, String> validatorResult = validateHandling.validateHandling(errors);
            for (String key : validatorResult.keySet())
            {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "checkplan/forUser/retire/retire";
        }

        /** 입력한 비밀번호와, user 의 비밀번호가 같은지 확인 ----------------------------
         */

        // ---  계정을 탈퇴 ( 휴면 ) 계정으로 전환 확인 page 로 이동  ----------
        if( editService.isPwEqual(dto.getPw(), (Long)session.getAttribute("code")) )
            {
                return  "checkplan/forUser/retire/UnscribingDoing";
            }
        // --- 비밀번호가 서로 맞지 않음으로 현재 page로 이동 ----------
        else
            {
                model.addAttribute("errorMessage","Wrong Password! , Please Check again");
                return  "checkplan/forUser/retire/retire";
            }
    }


    @Autowired
    private UnscribeService unscribeService;

    // ------------ 회원 탈퇴를 진행하는 method ------------------------------------------------------------
    @PostMapping("retire/UnscribingComplete")
    public String unSubComplete(HttpSession sessioin)
    {
        Long code = (Long)sessioin.getAttribute("code");

        // 정상적으로 만료가 된다면
        if ( unscribeService.unSubscribing("휴면",code))
            {
                unscribeService.updateModDate(code);
                sessioin.invalidate();
            }
        return "checkplan/forUser/retire/UnscribingComplete";
    }

    /** 회원 상태가 expiring 인 계정 state 를 "회원"으로 변경 -------------------------------------------------------
     *  < 회원 계정 복구 >
     */
    @PostMapping("unScribeCancle")
    public String unSubCancel(
            HttpSession session,
            @Valid PasswordDTO dto,
            Errors errors,
            Model model,
            String pwCheck)
    {

        if(errors.hasErrors())
        {
            model.addAttribute("dto", dto);
            Map<String, String> validatorResult = validateHandling.validateHandling(errors);
            for (String key : validatorResult.keySet())
                {
                    model.addAttribute(key, validatorResult.get(key));
                }
        return "checkplan/forUser/retire/UnscribingCancle";
        }

        // 비밀번호 값과 비밀번호 확인 값이 같다면 --------------------
        if( editService.unSubCancel(dto.getPw(), pwCheck, (Long) session.getAttribute("code")) > 0 )
            {
                return "redirect:/checkplan/mainpage";
            }
        else
            {
                model.addAttribute("ErrorMessage", "The passwords entered do not match each other.");
            return "checkplan/forUser/retire/UnscribingCancle";
            }
    }

}
