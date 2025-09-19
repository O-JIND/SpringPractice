package com.cofee.controller;

import com.cofee.entitiy.Member;
import com.cofee.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController //웹에서 요청을 받아 처리하는 controller class
@RequiredArgsConstructor//final keyword 가 들어있는 식별자에 값을 외부에서 생성자를 통해서 injection 해줌
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member/signup")
    public ResponseEntity<?> signup(@RequestBody Member bean){
        //ResponseEntity : http 응답코드나 메세지를 표현하기 위한 클래스
        //Json JavaScript Object Notation
        //@RequestBody : json 형태의 문자열을 자바의 객체 타입으로 변환
        System.out.println(bean);
        //input email 중복체크
        Member member = memberService.findByEmail(bean.getEmail());

        if(member != null){//이미 존재하는 email
            return new ResponseEntity<>(Map.of("email","이미 존재하는 이메일 주소입니다."), HttpStatus.BAD_REQUEST);
        }else{
            memberService.insert(bean);
            return new ResponseEntity<>("회원 가입 성공",HttpStatus.OK);

        }


    }

}
