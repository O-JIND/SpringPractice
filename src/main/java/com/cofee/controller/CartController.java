package com.cofee.controller;


import com.cofee.dto.CartProductDto;
import com.cofee.dto.CartProductResponseDto;
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

import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<String> addToCart(@RequestBody CartProductDto dto) {
        //member,product의 유효성 검사
        Optional<Member> memberOptional = memberService.findMemberById(dto.getMemberId());
        Optional<Product> productOptional = productService.findproductById(dto.getProductId());

        // 중복된 Product 확인 및 등록 불가 RespondEntity 기능

        //member,product의 객체 정보 가져오기
        if (memberOptional.isEmpty() || productOptional.isEmpty()) {//
            return ResponseEntity.badRequest().body("회원 또는 상품 정보가 올바르지 않습니다.");
        }
        Member member = memberOptional.get();
        Product product = productOptional.get();
        //stock 유효성 검사
        if (product.getStock() < dto.getQuantity()) {
            return ResponseEntity.badRequest().body("재고 수량이 부족합니다.");
        }

        //Cart 조회 및 신규 작성
        Cart cart = cartService.findByMember(member);
        if (cart == null) {
            Cart newcart = new Cart();
            newcart.setMember(member);//고객이 카트를 집어온 행위와 유사
            cart = cartService.saveCart(newcart);//database 에 저장
        }

        CartProduct existingCartProduct = null;
        if (cart.getCartProducts() != null) {
            for (CartProduct cp : cart.getCartProducts()) {
                // 주의) Long 타입은 참조 자료형이르로 == 대신 equals() 메소드를 사용해야 합니다.
                if (cp.getProduct().getId().equals(product.getId())) {
                    existingCartProduct = cp;
                    break;
                }
            }
        }
        if (existingCartProduct != null) { // 기존 상품이면 수량 누적
            existingCartProduct.setQuantity(existingCartProduct.getQuantity() + dto.getQuantity());
            cartproductService.saveCartProduct(existingCartProduct);

        } else { // 새로운 상품이면 새로 추가
            CartProduct cp = new CartProduct();
            cp.setCart(cart);
            cp.setProduct(product);
            cp.setQuantity(dto.getQuantity());
            cartproductService.saveCartProduct(cp);
        }
        //재고 수량은 아직 차감시키지 않는다. 차감은 결제 당시..
        return ResponseEntity.ok("요청하신 상품이 장바구니에 추가되었습니다.");
    }

    //    특정 사용자의 카트 상품을 조회한다.
    @GetMapping("/list/{member_id}")
    public ResponseEntity<List<CartProductResponseDto>> getCartProducts(@PathVariable Long member_id) {
        Optional<Member> memberOption = memberService.findMemberById(member_id);

        if (memberOption.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Member member = memberOption.get();
        Cart cart = cartService.findByMember(member);

        if (cart == null) {
            cart = new Cart();
        }
        //과거에 내가 장바구니에 추가한 내역을 의미하는 컬렉션
        List<CartProductResponseDto> cartProducts = new ArrayList<>();
        for (CartProduct cp : cart.getCartProducts()) {
            cartProducts.add(new CartProductResponseDto(cp));
        }

        System.out.println("카트 상품 개수" + cartProducts.size());
        return ResponseEntity.ok(cartProducts);
    }

    @PatchMapping("/edit/{cartProductId}")
    public ResponseEntity<String> quantitySet(
            @PathVariable Long cartProductId,
            @RequestParam(required = false) Integer quantity) {
        String message = null;
        if (quantity == null) {
            message = "Cart product is At least over one product";
            return ResponseEntity.badRequest().body(message);
        }
        Optional<CartProduct> cartProductOptional = cartService.findCartProductById(cartProductId);
        CartProduct cartProduct = cartProductOptional.get();
        cartProduct.setQuantity(quantity);//기존 내용 덮어쓰기

        cartproductService.saveCP(cartProduct);

        message = "Update Complete";
        return ResponseEntity.ok(message);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCartProduct(@PathVariable Long id) {
        System.out.println(id + "---------------------------------------------------------");
        try {
            boolean isDeleted = cartService.deleteCartProductById(id);
            if (isDeleted) {
                return ResponseEntity.ok().body("Delete Complete");
            } else {
                return ResponseEntity.badRequest().body("Delete Failed");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Error occurred : " + e.getMessage());
        }
    }

}
