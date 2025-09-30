package com.cofee.dto;

import com.cofee.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

//사용자가 주문할 때 필요한 변수들을 정의해 놓은 클래스
@Getter
@Setter
@ToString
public class OrderDto {
    private Long memberId;
    private OrderStatus status;
    private List<OrderProductDto> orderItems ;//주문 상품들 목록

}
