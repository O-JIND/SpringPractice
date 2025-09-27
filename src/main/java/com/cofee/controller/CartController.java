package com.cofee.controller;


import com.cofee.dto.CartProductDto;
import com.cofee.entitiy.Cart;
import com.cofee.entitiy.CartProduct;
import com.cofee.entitiy.Member;
import com.cofee.entitiy.Product;
import com.cofee.service.CartProductService;
import com.cofee.service.CartService;
import com.cofee.service.MemberService;
import com.cofee.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
/*
                            test 시나리오
                사용자가 상품을 장바구니에 담았을 때 체크
        1. 로그인한 회원Id , 상품 entity의 Id 확인
        2. Cart Table의 회원 아이디가 로그인한 사람인가?
        3. Cart entity 의 카트아이디와 CartProduct entity의 회원아이디가 동일한가?
        4. CartProduct Entity의 상품 Id 와 Product entity의 상품아이디가 동일한가?


                장바구니에 상품이 존재할 경우
        1. Cart entity에 변동사항은 없습니다.
        2. CartProduct 엔티티에 신규 상품 정보만 추가
        3.
 */

@RestController //웹에서 요청을 받아 처리하는 controller class
@RequiredArgsConstructor//final keyword 가 들어있는 식별자에 값을 외부에서 생성자를 통해서 injection 해줌
@RequestMapping("/cart")
public class CartController {
    private final MemberService memberService;
    private final ProductService productService;
    private final CartService cartService;
    private final CartProductService cartproductService;

    @PostMapping("/insert")
    public ResponseEntity<String> addToCart(@RequestBody CartProductDto dto){
        //member,product의 유효성 검사
        Optional<Member> memberOptional = memberService.findMemberById(dto.getMemberId());
        Optional<Product> productOptional = productService.findproductById(dto.getProductId());

        //member,product의 객체 정보 가져오기
        if(memberOptional.isEmpty()||productOptional.isEmpty()){//
            return ResponseEntity.badRequest().body("회원 또는 상품 정보가 올바르지 않습니다.");
        }
        Member member = memberOptional.get();
        Product product = productOptional.get();
        //stock 유효성 검사
        if(product.getStock()<dto.getQuantity()){
            return ResponseEntity.badRequest().body("재고 수량이 부족합니다.");
        }


        //Cart 조회 및 신규 작성
        Cart cart = cartService.findByMember(member);
        if(cart == null){
            Cart newcart = new Cart();
            newcart.setMember(member);//고객이 카트를 집어온 행위와 유사
            cart =cartService.saveCart(newcart);//database 에 저장
        }
        //chosen product 를 Cart에 카트 상품 담기
        CartProduct cp = new CartProduct();
        cp.setCart(cart);
        cp.setProduct(product);
        cp.setQuantity(dto.getQuantity());
        cartproductService.saveCP(cp);
        //재고 수량은 아직 차감시키지 않는다. 차감은 결제 당시..
        return ResponseEntity.ok("요청하신 상품이 장바구니에 추가되었습니다.");
    }


}
