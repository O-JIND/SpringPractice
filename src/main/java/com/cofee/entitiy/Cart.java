package com.cofee.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name="carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="cart_id")
    private Long id; //카트 아이디

    // 고객 한 명이 1개의 카트를 사용
    @OneToOne(fetch = FetchType.LAZY)//지연 로딩 1:1 연관 관계 매핑;
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CartProduct> cartProducts;

}
