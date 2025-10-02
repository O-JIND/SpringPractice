package com.cofee.repository;

import com.cofee.constant.OrderStatus;
import com.cofee.entitiy.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByMemberIdOrderByIdDesc(Long memberId);

    List<Order> findByStatusOrderByIdDesc(OrderStatus status);

    // 주의사항 테이블 이름 대신 @Query annotation 사용시
    //  1 테이블 이름 대신 Entity 이름을 명시
    //  2.대소문자 구분

    @Modifying // 이 쿼리는 select 구문이 아닌, data 변경을 위한 쿼리
    @Transactional
    @Query("update Order o set o.status = :status where o.id = :orderId")
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("status") OrderStatus status);
}
