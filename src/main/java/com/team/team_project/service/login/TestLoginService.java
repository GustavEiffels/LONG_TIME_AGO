package com.team.team_project.service.login;

import com.team.team_project.dto.loginDTO.loginDTO;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public interface TestLoginService {
    Map<String, Object> forlogin(loginDTO dto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;

    public default Map<String, String> loginAccountValidateHandling(Errors errors){
        Map<String, String> loginAccountValidatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            loginAccountValidatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return loginAccountValidatorResult;
    }

    public default Map<String, String> loginPasswordValidateHandling(Errors errors){
        Map<String, String> loginPasswordValidateResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            loginPasswordValidateResult.put(validKeyName, error.getDefaultMessage());
        }
        return loginPasswordValidateResult;
    }
}
