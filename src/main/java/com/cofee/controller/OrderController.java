package com.cofee.controller;

import com.cofee.constant.OrderStatus;
import com.cofee.constant.Role;
import com.cofee.dto.OrderDto;
import com.cofee.dto.OrderProductDto;
import com.cofee.dto.OrderResponseDto;
import com.cofee.entitiy.Member;
import com.cofee.entitiy.Order;
import com.cofee.entitiy.OrderProduct;
import com.cofee.entitiy.Product;
import com.cofee.service.CartService;
import com.cofee.service.MemberService;
import com.cofee.service.OrderService;
import com.cofee.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Order")
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ProductService productService;
    private final CartService cartService;


    //리엑트의 카트목록/ 주문하기 버튼을 눌러서 주문을 시도
    @PostMapping("")
    public ResponseEntity<?> addCartToOrder(@RequestBody OrderDto dto) {
        //회원 객체 생성 Member
        Optional<Member> optionalMember = memberService.findMemberById(dto.getMemberId());
        if (!optionalMember.isPresent()) {
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }
        //마일리지 적립 시스템이 있으면, 적립 기능 여기에 구현
        Member member = optionalMember.get();
        //주문 객체 생성 OrderDto
        Order order = new Order();
        order.setMember(member);
        order.setOrderDate(LocalDate.now());
        order.setStatus(dto.getStatus());
        //주문 상품 OrderProduct with 확장 for
        List<OrderProduct> orderProductList = new ArrayList<>();

        for (OrderProductDto item : dto.getOrderItems()) {
            Optional<Product> optionalProduct = productService.findproductById(item.getProductId());
            if (!optionalProduct.isPresent()) {
                throw new RuntimeException(" 해당 상품이 존재하지 않습니다.");
            }
            Product product = optionalProduct.get();
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("재고 수량이 부족합니다.");
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(item.getQuantity());

            //리스트 컬렉션에 각각 주문 상품을 담는다.
            orderProductList.add(orderProduct);

            product.setStock(product.getStock() - item.getQuantity());

            Long cartProductId = item.getCartProductId();

            if (cartProductId != null) {

                cartService.deleteCartProductById(cartProductId);
            } else {
                System.out.println("sdsd");
            }


        }

        order.setOrderProducts(orderProductList);

        // 주문 객체를 저장
        orderService.save(order);
        String msg = "주문 성공";
        return ResponseEntity.ok(msg);
    }

    // 특정한 회원의 주문 정보를 최신 날짜 순으로 조회합니다.
    // http://localhost:8989/order/list?memberId=회원아이디
    @GetMapping("/list") //리액트의 OrderList.js 파일 내의 useEffect 참조
    public ResponseEntity<List<OrderResponseDto>> getOrderList(@RequestParam Long memberId, @RequestParam Role role) {

        List<Order> orderList = null;

        if (role == Role.ADMIN) {
            System.out.println("관리자");
            orderList = orderService.findAllByOrderByIdDesc(OrderStatus.PENDING);
        } else {
            System.out.println("일반인");
            orderList = orderService.findByMemberId(memberId);
        }

        List<OrderResponseDto> responseDtoList = new ArrayList<>();
        for (Order item : orderList) {
            OrderResponseDto dto = new OrderResponseDto();
            dto.setOrderId(item.getId());
            dto.setMemberId(item.getMember().getName());
            dto.setOrderDate(item.getOrderDate());
            dto.setStatus(item.getStatus().name());
            List<OrderResponseDto.OrderItem> orderItemsDto = new ArrayList<>();
            for (OrderProduct op : item.getOrderProducts()) {
                OrderResponseDto.OrderItem responseItem
                        = new OrderResponseDto.OrderItem(op.getProduct().getName(), op.getQuantity());
                orderItemsDto.add(responseItem);
            }
            dto.setOrderItems(orderItemsDto);
            responseDtoList.add(dto);
        }

        return ResponseEntity.ok(responseDtoList);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {

        if (!orderService.existesById(orderId)) {
            return ResponseEntity.notFound().build();
        }


        Optional<Order> orderOptional = orderService.findById(orderId);
        Order order = orderOptional.get();
        for (OrderProduct op : order.getOrderProducts()) {
            Product product = op.getProduct();
            int quantity = op.getQuantity();
            product.setStock(product.getStock() + quantity);
            productService.save(product);
        }

        this.orderService.deleteById(orderId);


        return ResponseEntity.ok("Success");
    }

    @PutMapping("/changeStatus/{orderId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        System.out.println("ID : " + orderId + "Status : " + status);

        int affected = -1; // database 에서 반영이 된 행 개수
        affected = orderService.updateOrderStatus(orderId, status);


        String msg = "ID : " + orderId + "Status : " + status;
        return ResponseEntity.ok(msg);
    }

}
