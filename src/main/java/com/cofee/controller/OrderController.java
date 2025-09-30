package com.cofee.controller;

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
    public ResponseEntity<?> addCartToOrder(@RequestBody OrderDto dto){
        //회원 객체 생성 Member
        Optional<Member> optionalMember = memberService.findMemberById(dto.getMemberId());
        if(!optionalMember.isPresent()){
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

        for(OrderProductDto item :dto.getOrderItems()){
            Optional<Product> optionalProduct = productService.findproductById(item.getProductId());
            if(!optionalProduct.isPresent()){
                throw new RuntimeException(" 해당 상품이 존재하지 않습니다.");
            }
            Product product = optionalProduct.get();
            if(product.getStock()<item.getQuantity()){
                throw new RuntimeException("재고 수량이 부족합니다.");
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(item.getQuantity());

            //리스트 컬렉션에 각각 주문 상품을 담는다.
            orderProductList.add(orderProduct);

            product.setStock(product.getStock()- item.getQuantity());

            Long cartProductId = item.getCartProductId();

            if(cartProductId !=null){

                cartService.deleteCartProductById(cartProductId);
            }else{
                System.out.println("sdsd");
            }


        }

        order.setOrderProducts(orderProductList);

        // 주문 객체를 저장
        orderService.save(order);
        String msg = "주문 성공";
        return ResponseEntity.ok(msg);
    }
    @GetMapping("/list/{memberId}")
    public List<OrderResponseDto>orderList(@PathVariable Long memberId){
        List<Order> orderList= orderService.findProductByMember(memberId);
        List<OrderResponseDto> responseDto=new ArrayList<>();

        for(Order item : orderList){
            OrderResponseDto dto = new OrderResponseDto();
            dto.setOrderId(item.getId());
            dto.setMemberId(memberId);
            dto.setStatus(item.getStatus());
            dto.setOrderDate(item.getOrderDate());

            responseDto.add(dto);
        }

        return responseDto;

    }

}
