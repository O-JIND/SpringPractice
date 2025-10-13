package com.cofee.repository;

import com.cofee.entitiy.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {
//상품 id를 역순으로 정렬하여 목록을 보여줌.

    List<Product> findProductByOrderByIdDesc();


    // image 칼럼에 특정 문자열이 포함된 데이터를 조회합니다.
    // 데이터 베이스의 like 키워드와 유사
    List<Product> findByImageContaining(String keyward);

    // 검색 조건인 spec와 페이징 객체 pageable을 사용하여 데이터를 검색합니다.
    // 정렬방식은 pageable에 포합되어 있음.
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}
