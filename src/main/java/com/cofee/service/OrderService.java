package com.cofee.service;

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

    public List<Order> findAllByOrderByIdDesc() {
        return orderRepository.findAllByOrderByIdDesc();
    }

    public void deleteById(Long orderId) {
        orderProductRepository.deleteById(orderId);
        orderRepository.deleteById(orderId);
    }
}
