package com.cofee.dto;


import com.cofee.entitiy.CartProduct;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//리엑트의 Cart.js 파일에서 fetchCartProducts() 함수 참조
//카트 상품 목록 페이지에서 데이터 1개를 의미하는 자바 DTO 클래스
@Getter
@Setter
@ToString
public class CartProductResponseDto {
    private Long cartProductId; //수량의 변경이나 삭제시 반드시 사용된다.
    private Long productId;
    private String name;
    private String image;
    private int price;
    private int quantity;
    private boolean checked = false;

    public CartProductResponseDto(CartProduct cartProduct) {
        this.cartProductId = cartProduct.getId();
        this.productId = cartProduct.getProduct().getId();
        this.name=cartProduct.getProduct().getName();
        this.image=cartProduct.getProduct().getImage();
        this.price=cartProduct.getProduct().getPrice();
        this.quantity=cartProduct.getQuantity();
    }


}
