package com.cofee.service;

import com.cofee.entitiy.CartProduct;
import com.cofee.repository.CartProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CartProductService {
private final CartProductRepository cartProductRepository;

    public CartProduct saveCP(CartProduct cp) {
        return  cartProductRepository.save(cp);
    }
}
