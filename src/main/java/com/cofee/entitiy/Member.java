package com.cofee.entitiy;

import com.cofee.constant.Role;
import jakarta.persistence.*;
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

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate regdate;



}
