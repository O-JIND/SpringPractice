package com.cofee.handler;

import com.cofee.entitiy.Member;
import com.cofee.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
    AuthenticationSuccessHandler interface
      스프링 시큐리티에서 로그인에 성공했을 때 실행하고자 하는 동작을
      개발자가 직접 정의 할 수 있도록 해주는 인터페이스
      로그인 성공시 클라이언트에 Json 형식으로 회원 정보를 반환
 */
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    private MemberService memberService;

    @Autowired //setter 메서드를 이용한 객체 주입 injection--> 생성자, setter 가 존재
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override   //Callback method
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        //authentication는 인증 객체라고 하며, 로그인 성공시의 정보가 포함되어 있다.
        System.out.println("로그인 성공 실행 -------------------------");
        //클라이언트에 대한 응답을 Json 타입으로 지정
        response.setContentType("application/json;charset=UTF-8");
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();
        Member member = memberService.findByEmail(email);

        Map<String, Object> data = new HashMap<>();
        data.put("message", "success");
        data.put("member", member);

        System.out.println("회원 객체 정보 :  ");
        System.out.println(member);

        // 자바 객체를 Json 으로 변환
        ObjectMapper mapper = new ObjectMapper();
        // Java 날짜, 시간 처리 모듈을 등록한다.
        mapper.registerModule(new JavaTimeModule());
        // 시간의 TimeStamp 타입 대신 문자열로 변환한다.
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Json 문자열을 http의 응답 객체로 전송한다.
        response.getWriter().write(mapper.writeValueAsString(data));
    }
}
