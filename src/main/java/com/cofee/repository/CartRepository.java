package com.cofee.repository;

import com.cofee.entitiy.Cart;
import com.cofee.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByMember(Member member);
    //특정 회원이 Cart를 가지고 있는지 확인한다.

}
