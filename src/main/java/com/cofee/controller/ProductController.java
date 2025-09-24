package com.cofee.controller;

import com.cofee.entitiy.Product;
import com.cofee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> product(){
        List<Product> products =this.productService.getProductList();


        return products;
    }

    @DeleteMapping("/delete/{id}")//{id} 는 경로 변수; 가변 매개 변수 | 특정 id 삭제 요청
    public ResponseEntity<String> delete(@PathVariable Long id){//{id} --> Long id, @PathVariable는 가변 매개변수를 메소드에 전달
        try{
            boolean idDeleted=this.productService.deleteProduct(id);
            if(idDeleted){
                return ResponseEntity.ok("Successfully Deleted");
            }else{
                return ResponseEntity.badRequest().body("Delete Failure "+id+"is not found");
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Error occurred : " + e.getMessage());
        }
    }

    @RequestMapping("/register")
    public ResponseEntity<String> Addone(){
        return null;
    }
}
