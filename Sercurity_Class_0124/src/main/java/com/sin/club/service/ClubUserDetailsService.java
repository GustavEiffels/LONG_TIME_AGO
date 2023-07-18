package com.sin.club.service;

import com.sin.club.dto.ClubAuthMember;
import com.sin.club.entity.ClubMember;
import com.sin.club.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
        log.info("loadByUserName : "+username);
        // 데이터 베이스에서 username 에 해당하는 데이터 찾아오기
        ClubMember clubMember
                    =clubMemberRepository.findByEmail(
                            username, false).get();

        // 인증을 위한 class 의 instance를 생성
        ClubAuthMember clubAuthMember = new ClubAuthMember(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.getRoleSet().stream()
                        .map(role->new SimpleGrantedAuthority(
                                "ROLE_"+role.name()))
                        .collect(Collectors.toSet())
        );
        clubAuthMember.setName(clubAuthMember.getName());
        clubAuthMember.setFromSocial(clubAuthMember.isFromSocial());
        log.info(clubMember);
        log.info(clubAuthMember);
        return clubAuthMember;
    }

}
