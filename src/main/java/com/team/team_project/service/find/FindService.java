package com.team.team_project.service.find;


import com.team.team_project.dto.findDTO.ByNickDTO;
import com.team.team_project.dto.findDTO.ByQandAandBirthdayDTO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public interface FindService {

    // nick name 으로 아이디 찾기
    public Map<String, Object> findByNick(ByNickDTO dto) throws Exception;

    // question 으로 아이디 찾기 - 1.
    public Map<String, Object> findUserIdAndEmailByQuestionAndAnswer(ByQandAandBirthdayDTO dto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;

    /**
     *  비밀번호찾기 --> 비밀번호 변경시 사용하는 method
     *
     *  user 정보를 입력해서 유효한지 확인하는 method
     *
     * */
    Map<String, Object> checkAccountAvailable(String userInfo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;

    /**
     * 입력한 id 혹은 email 과 입력한 user 정보가 맞다면 비밀번호를 변경 하고 email 로 변경된 password 를 전송
     * **/
    Map<String , Object> resultOfPwfind(String userInfo, ByQandAandBirthdayDTO dto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, MessagingException;
    // 모든 조건이 충족 되었다면 설정된 변경할 비밀번호로 암호화 하여 변경하고 비밀번호를 해당 email 로 전송
    void PwChangeResult(String userInfo, String answer, String context, String birthday) throws InvalidAlgorithmParameterException, MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;

    void PwChangeResult(String userInfo, ByQandAandBirthdayDTO dto) throws InvalidAlgorithmParameterException, MessagingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;
}
