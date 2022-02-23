package com.team.team_project.service.login;

import com.team.team_project.dto.PasswordDTO.PasswordDTO;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public interface TestLoginService {
    Map<String, Object> forlogin(String account, PasswordDTO dto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;

    public default Map<String, String> validateHandling(Errors errors){
        Map<String, String> loginAccountValidatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            loginAccountValidatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return loginAccountValidatorResult;
    }


}
