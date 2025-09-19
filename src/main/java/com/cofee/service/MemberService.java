package com.cofee.service;

import com.cofee.constant.Role;
import com.cofee.entitiy.Member;
import com.cofee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor // final 키워드 또는 @NotNull 필드가 들어있는 식별자에 생성자를 통해 값을 외부에서 주입해준다.
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findByEmail(String email) {


        return memberRepository.findByEmail(email);
    }

    public void insert(Member bean) {
        //사용자 역할과 등록 역할은 in here
        bean.setRole(Role.USER);
        bean.setRegdate(LocalDate.now());
        memberRepository.save(bean);//주의> Repository에서 insert는 save;
    }
}
