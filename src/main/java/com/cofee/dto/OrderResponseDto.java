package com.cofee.dto;

import com.cofee.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Getter
@Setter
@ToString
public class OrderResponseDto {
    private Long orderId;
    private Long memberId;
    private OrderStatus status;
    private LocalDate orderDate;




}
