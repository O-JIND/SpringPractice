package com.cofee.repository;

import com.cofee.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;

//회원 정보들을 이용하여 데이터베이스와 교신하는 인터페이스
//JpaRepository<entity_name,Base_type>
public interface MemberRepository extends JpaRepository<Member,Long> {

//email 정보를 이용하여 해당 회원 존재유무 확인
    //query Method // email로 회원을 체크
    Member findByEmail(String email);


}
