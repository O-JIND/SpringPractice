package com.cofee.repository;

import com.cofee.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {
//상품 id를 역순으로 정렬하여 목록을 보여줌.

    List<Product> findProductByOrderByIdDesc();


    // image 칼럼에 특정 문자열이 포함된 데이터를 조회합니다.
    // 데이터 베이스의 like 키워드와 유사
    List<Product> findByImageContaining(String keyward);
}
