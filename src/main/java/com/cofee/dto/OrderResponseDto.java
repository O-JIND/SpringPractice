package com.cofee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private String memberId;
    private String status;
    private LocalDate orderDate;
    private List<OrderItem> orderItems;//주문의 상품 목록 ; 하단의 OrderItem에 대한 컬렉션

    //주문 상품 1개
    @Data
    @AllArgsConstructor
    public static class OrderItem{
        //내부 정적 클래스
        private String productName;
        private int quantity;
    }


}
