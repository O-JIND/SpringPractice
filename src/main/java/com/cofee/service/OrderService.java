package com.cofee.service;

import com.cofee.constant.OrderStatus;
import com.cofee.entitiy.Order;
import com.cofee.repository.OrderProductRepository;
import com.cofee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;


    public Optional<Order> findProductById(Long productId) {
        return orderRepository.findById(productId);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }


    public List<Order> findByMemberId(Long memberId) {
        return orderRepository.findByMemberIdOrderByIdDesc(memberId);
    }

    ;

    public List<Order> findAllByOrderByIdDesc(OrderStatus status) {
        return orderRepository.findByStatusOrderByIdDesc(status);
    }

    public void deleteById(Long orderId) {


        orderProductRepository.deleteById(orderId);
        orderRepository.deleteById(orderId);
    }

    public int updateOrderStatus(Long orderId, OrderStatus status) {
        return orderRepository.updateOrderStatus(orderId, status);

    }

    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public boolean existesById(Long orderId) {
        return orderRepository.existsById(orderId);
    }
}
