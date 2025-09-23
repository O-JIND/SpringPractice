package com.cofee.controller;

import com.cofee.entitiy.Member;
import com.cofee.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController //웹에서 요청을 받아 처리하는 controller class
@RequiredArgsConstructor//final keyword 가 들어있는 식별자에 값을 외부에서 생성자를 통해서 injection 해줌
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody Member bean, BindingResult bR){
        //ResponseEntity : http 응답코드나 메세지를 표현하기 위한 클래스
        //Json JavaScript Object Notation
        //@RequestBody : json 형태의 문자열을 자바의 객체 타입으로 변환
        //@Valid : 입력 데이터에 대한 유효성 검사를 수행하는 annotation
        //bindingResult : 유효성 검사시 문제가 있으면 이 객체에 해당 예외 정보들이 포함됨
        System.out.println(bean);
        //input email 중복체크
        Member member = memberService.findByEmail(bean.getEmail());

        System.out.println("유효성 오류 개수");
        System.out.println(bR.getFieldErrorCount());
        //Field는 각각의 변수
        if(bR.hasErrors()) {
            //Map<column_name,error_message>
            Map<String, String> errors = new HashMap<>();
            for (FieldError err : bR.getFieldErrors()) {
                errors.put(err.getField(), err.getDefaultMessage());

            }for (String s : errors.values()) {
                System.out.println(s);
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if(member != null){//이미 존재하는 email
            return new ResponseEntity<>(Map.of("email","이미 존재하는 이메일 주소입니다."), HttpStatus.BAD_REQUEST);
        }else{
            memberService.insert(bean);
            return new ResponseEntity<>("회원 가입 성공",HttpStatus.OK);
        }


    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Member bean){

        System.out.println(bean);


        Member member = this.memberService.findByEmail(bean.getEmail());

        boolean isfound = false;

        if(member != null) {
            if (bean.getPassword().equals(bean.getPassword())) {
                isfound = true;
            }
        }
        //response : 클라이언트에 넘겨줄 값
        Map<String, Object> response = new HashMap<>();
        if(isfound){
            response.put("message","success");
            response.put("member",member);
            return ResponseEntity.ok(response);
        }else{
            response.put("message","Id/Password error");
            return ResponseEntity.status(401).body(response);

        }

    }

}
