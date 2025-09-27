package com.cofee.repository;

import com.cofee.entitiy.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {


}
