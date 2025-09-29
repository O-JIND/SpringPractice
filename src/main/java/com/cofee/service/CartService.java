package com.cofee.service;

import com.cofee.entitiy.Cart;
import com.cofee.entitiy.CartProduct;
import com.cofee.entitiy.Member;
import com.cofee.repository.CartProductRepository;
import com.cofee.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
private final CartRepository cartRepository ;
private final CartProductRepository cartProductRepository ;

    public Cart findByMember(Member member) {
        // orElse : 해당 Cart 가 있으면 그대로 리턴하고 없으면 null을 리턴한다.
        return cartRepository.findByMember(member).orElse(null);

    }

    public Cart saveCart(Cart newcart) {
        return cartRepository.save(newcart);
    }

    public void updateQuantity(Long cartProductId, int quantity) {

    }

    public Optional<CartProduct> findCartProductById(Long cartProductId) {
        return cartProductRepository.findById(cartProductId);
    }

    public Boolean deleteCartProductById(Long id) {
        if(cartProductRepository.existsById(id)){
            this.cartProductRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }
}
