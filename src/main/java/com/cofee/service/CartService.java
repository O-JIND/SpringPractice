package com.cofee.service;

import com.cofee.entitiy.Cart;
import com.cofee.entitiy.Member;
import com.cofee.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
private final CartRepository cartRepository ;

    public Cart findByMember(Member member) {
        // orElse : 해당 Cart 가 있으면 그대로 리턴하고 없으면 null을 리턴한다.
        return cartRepository.findByMember(member).orElse(null);

    }

    public Cart saveCart(Cart newcart) {
        return cartRepository.save(newcart);
    }
}
