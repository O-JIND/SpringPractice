package com.cofee.service;

import com.cofee.constant.Category;
import com.cofee.entitiy.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Criteria ; Specification JPA 의  Criteria API를 사용하여 where 절을 객체 지향적으로 만들어 준다.
//JPA 를 사용하여 동적 쿼리를 만들어 주는  interface
public class ProductSpecification {
    /*
        사용자가 지정한 날짜 범위의 상품 목록만 필터링해주는 Specification,
        searchDateType : 검색할 날짜 범위
        반환 값 : 검색된 범위의 상품 목록을 조회하기 위한 Specification 객체 정보
    */
    public static Specification<Product> hasDateRange(String searchDateType) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                LocalDate now = LocalDate.now();
                LocalDate startDate = null;
                switch (searchDateType) {
                    case "1d":
                        startDate = now.minus(1, ChronoUnit.DAYS);
                        break;
                    case "1w":
                        startDate = now.minus(1, ChronoUnit.WEEKS);
                        break;
                    case "1m":
                        startDate = now.minus(1, ChronoUnit.MONTHS);
                        break;
                    case "6m":
                        startDate = now.minus(6, ChronoUnit.MONTHS);
                        break;
                    case "all":
                    default:
                        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));

                }
                //상품 입고 일자 (input_date)가 startDate 이후의 상품들만 검색합니다.

                return criteriaBuilder.greaterThanOrEqualTo(root.get("input_date"), startDate);
            }
        };

    }

    /*
        사용자가 지정한 특정 카테고리의 상품 목록만 필터링해주는 Specification,
        category : 검색할 카테고리 문자열
        반환 값 : 해당 카테고리의 상품 목록을 조회하기 위한 Specification 객체 정보
    */
    public static Specification<Product> hasCategory(Category category) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (category == Category.ALL) {
                    return criteriaBuilder.conjunction();
                }
                return criteriaBuilder.equal(root.get("category"), category);
            }
        };
    }

    public static Specification<Product> hasNameLike(String keyword) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
            }
        };
    }

    public static Specification<Product> hasDesLike(String keyword) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("description"), "%" + keyword + "%");
            }
        };
    }

}
