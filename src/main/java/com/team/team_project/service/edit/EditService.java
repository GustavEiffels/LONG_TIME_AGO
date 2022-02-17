package com.team.team_project.service.edit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.Map;

public interface EditService {
        Map<String, Object> bringUserInfo(String nick) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;

        String bringAnswerInfo(Long code);
//
        String bringQuestionInfo(Long code);

        boolean bringPwForRetire(String pw, String nick);

        // 실제로 값을 바꾸는 method
        Map<String, Object> changeUserInfo(String currentNick,
                               String nickCh,
                               String pw,
                               String pwCheck,
                               String gender,
                               String birthday,
                               String answer,
                               String context);
}
