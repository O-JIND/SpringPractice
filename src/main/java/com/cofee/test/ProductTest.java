package com.cofee.test;

import com.cofee.common.GenerateData;
import com.cofee.entitiy.Product;
import com.cofee.repository.ProductsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductTest {
    @Autowired
    private ProductsRepository productRepository;

    @Test
    @DisplayName("이미지 이용 데이터 추가")
    public void insertProductList(){
        //image label 추가
        GenerateData gen = new GenerateData();
        List<String> imageList = gen.getImageFileNames();
        System.out.println("image : " + imageList.size());

        //반복문을 사용하여 데이터 베이스에 추가
        for(int i=0; i< imageList.size();i++){
            Product bean = gen.createProduct(i,imageList.get(i));
            this.productRepository.save(bean);
        }
    }

}


