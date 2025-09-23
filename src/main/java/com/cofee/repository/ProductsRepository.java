package com.cofee.repository;

import com.cofee.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {
//상품 id를 역순으로 정렬하여 목록을 보여줌.

    List<Product> findProductByOrderByIdDesc();
}
