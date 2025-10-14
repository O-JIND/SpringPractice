package com.cofee.service;

import com.cofee.entitiy.Member;
import com.cofee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
    UserDetailsService 인터페이스
    로그인시 입력한 email 정보를 기반으로 DB 에서 사용자 정보를 조회하고
    인증에 필요한 UserDetails 객체를 반환한다.

    UserDetails 인터페이스
    로그인시 사용할 Id ,Password , 계정 만료 여부 , 비밀번호 만료 여부 등등..

 */
// 해당 클래스는 로그인시 입력한 사용자 정보를 토대로 데이터 베이스에서 읽은 다음 인증 객체로 반환하는 역할을 한다.
// 개발자가 직접 호출할 필요는 없고, Spring Security가 암시적으로 호출.
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            String message = "Id/Pw Error";
            throw new UsernameNotFoundException(message);
        }
        // Spring Security 는 UserDetails 의 구현체인 User를 사용하여 사용자 인증과 권한을 수행한다.
        return User.builder()
                .username(member.getEmail()) // 로그인 시 사용헀던 email
                .password(member.getPassword())
                .roles(member.getRole().name()) // 요구 형태가 String 이라서 name() 붙임
                .build();
    }
}
