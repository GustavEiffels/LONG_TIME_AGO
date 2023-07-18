package com.sin.club;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SpringPasswordTest {
    @Autowired
    private PasswordEncoder passwordEncoder;





    @Test
    public void testEncoding(){
        String password = "pickchu";
        //암호화
        String enPw=passwordEncoder.encode(password);

        // 출력
        System.out.println("Encoding 된 피카츄 : "+enPw);

        // 평문과 비교
        System.out.println("비교 : "+ passwordEncoder.matches(password,enPw));
    }


}
