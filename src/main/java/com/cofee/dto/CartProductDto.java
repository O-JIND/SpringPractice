package com.cofee.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//DTO : Data Transfer Object 메소드에 데이터 매개 변수를 받아서 실행
@Getter
@Setter
@ToString
public class CartProductDto {
    private Long memberId;
    private Long productId;
    private int quantity;

}
