package com.cofee.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="cart_products",uniqueConstraints = @UniqueConstraint( name = "uq_cart_product",
        columnNames = {"cart_id", "product_id"})
)
public class CartProduct {
    //카트 1개에 여러개 카트 상품을 담을 수 있다.
    //JoinColumn cart_id는 Foreign key
    //mappedBy 구문이 없는 곳이 연관관계의 주인이 되면 외래키를 관리해주는 주체이다.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

     //동일 품목의 상품은 여러개의 카트상품에 별도로 담겨질 수 있다.
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="product_id",nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

}
