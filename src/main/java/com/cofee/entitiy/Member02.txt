package com.cofee.entitiy;

import com.cofee.constant.Role;
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
@Getter @Setter @ToString
@Entity //해당 클래스를 Entity로 관리
@Table(name = "members")
public class Member {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="member_id")//컬럼 이름변경
    private long id;

    @NotBlank(message = "Name is necessary")
    private String name;

    @NotBlank(message = "Email is necessary")
    @Column(unique = true,nullable = false)
    @Email(message = "Not a Email form")
    private String email;

    //정규 표현식 [asdf] 中 1
    @NotBlank(message = "Password is necessary")
    @Size(min=8,message = "Password have to over 8 char")
    @Pattern(regexp = ".*[A-Z].*", message = "비밀 번호는 대문자 1개 이상을 포함해야 합니다.")
    @Pattern(regexp = ".*[!@#$%].*", message = "비밀 번호는 특수 문자 '!@#$%' 중 하나 이상을 포함해야 합니다.")
    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate regdate;



}
