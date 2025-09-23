package com.cofee.service;

import com.cofee.entitiy.Product;
import com.cofee.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductsRepository productsRepository;
    public List<Product> getProductList() {
        return this.productsRepository.findProductByOrderByIdDesc();
    }
}
