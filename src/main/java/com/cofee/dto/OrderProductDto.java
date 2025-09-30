package com.cofee.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderProductDto {
    //변수 cartProductId는 카트목록보기 (CartList.js) 메뉴에서만 사용
    private Long cartProductId;
    private Long productId;
    private int quantity;
}
