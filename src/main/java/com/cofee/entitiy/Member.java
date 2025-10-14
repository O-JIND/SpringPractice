package com.cofee.entitiy;

import com.cofee.constant.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

//회원 한명에 대한 정보를 저장하고 있는 자바 클래스
@Getter
@Setter
@ToString
@Entity //해당 클래스를 Entity로 관리
@Table(name = "members")
public class Member {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")//컬럼 이름변경
    private long id;

    @NotBlank(message = "Name is necessary")
    private String name;

    @NotBlank(message = "Email is necessary")
    @Column(unique = true, nullable = false)
    @Email(message = "Not a Email form")
    private String email;

    //정규 표현식 [asdf] 中 1
    @NotBlank(message = "Password is necessary")
    @Size(min = 8, message = "Password have to over 8 char")
    @Size(max = 255, message = "Password is Maximum 255 char")
    @Pattern(regexp = ".*[A-Z].*", message = "Password contain at least one capital char .")
    @Pattern(regexp = ".*[!@#$%].*", message = "Password contain at least one of '!@#$%' ")
    private String password;

    @NotBlank(message = "address is necessary")
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 자바의 객체를 json 타입으로 변경할때 , LocalDate, LocalDateTime 클래스들이 변환이 잘 이루어 지지 않는다.
    // jackson library를 사용하여 변환이 수월하게 되도록 바꾼다.
    @JsonFormat(pattern = "yyyy-MM-dd")//변환시 날짜 형식을 개발자가 지정
    private LocalDate regdate;


}
