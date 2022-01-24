package com.sin.club;

import com.sin.club.entity.ClubMember;
import com.sin.club.entity.ClubMemberRole;
import com.sin.club.repository.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTest {
    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummitData(){
        for(int i=1; i<=100; i++){
            ClubMember clubMember = ClubMember.builder()
                    .email("user"+ i +"@gmail.com")
                    .name("사용자" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1234")).build();
            clubMember.addMemberRole(ClubMemberRole.USER);
            if(i>80){
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }
            if(i>90){
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }
            clubMemberRepository.save(clubMember);
        }
    }
    @Test
    public void testEmail(){
        Optional<ClubMember> result = clubMemberRepository.findByEmail("user88@gmail.com",false);
        System.out.println(result.get());
    }
}