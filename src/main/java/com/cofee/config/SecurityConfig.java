package com.cofee.config;

import com.cofee.handler.CustomLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration // 설정용 파일로 만들어주며 , 명시된 내용들을 객체 형태로 만들어준다.
@EnableWebSecurity // 웹 페이지 관련된 시큐리티 설정을 활성화 해주는 어노테이션
public class SecurityConfig {
    @Bean // SecurityFilterChain는 http 요청이 들어올 때 보안과 관련된 필터들이 해야할 일을 명시 해주는 필터 체인.
    //Ex, 로그인 ,로그아웃,cors 활성화 ,csrf 설정 등등
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 명시된 항목들은 로그인이 필요 없이 무조건 접속 허용
        String[] permitAllowed = {
                "/", "/member/signup", "/member/login", "/products/list", "/cart/**", "/Orders/**", "/fruit/**",
                "/element/**", "/images/", "/products", "/eleList/**"
        };
        String[] needAuthenticated = {"/products/specific/"};

        //HttpSecurity : 개발자가 코드를 직접 작성하여 보안 정책을 설정 할 수 있도록 도와주는 객체
        http
                .csrf(csrf -> csrf.disable())//CSRF 비활성화
                .cors(Customizer.withDefaults())//Cors 활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(permitAllowed).permitAll()
                        .requestMatchers(needAuthenticated).authenticated()//그 외 경로는 로그인이 필요
                );

        // successHandler는 이 문서 하단에 정의
        http.formLogin(form -> form
                .loginProcessingUrl("/member/login") // React 에서 로그인 시 요청 할 Url
                .usernameParameter("email") // 로그인 시 id 역할을 할 column명
                .passwordParameter("password") // 비밀번호 호 사용할 column
                .permitAll() // 누구든지 접근 허용
                .successHandler(handler()) // 로그인 성공시 수행할 동작을 여기에 명시
        );

        //참고: logoutSuccessHandler()
        http.logout(logout -> logout
                .logoutUrl("/member/logout")
                .permitAll()

        );

        return http.build();
    }

    @Bean//CorsConfigurationSource는 다른 도메인 에서 지원 요청시 브라우저가 허용여부를 검사하는 보안 정책
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "OPTIONS", "PATCH", "PUT"));
        //허용할 메소드 목록
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // 클라이언트가 서버에 요청시 모든 요청 정보를 허용한다.
        configuration.setAllowCredentials(true); // 쿠키, 세션 인증 정보 허용


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean //PasswordEncoder : 비밀 번호 암호화를 해주는 인터페이스
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomLoginSuccessHandler handler() {
        return new CustomLoginSuccessHandler();
    }
}
