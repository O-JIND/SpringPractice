package com.cofee.service;

import com.cofee.entitiy.Product;
import com.cofee.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductsRepository productsRepository;
    public List<Product> getProductList() {
        return this.productsRepository.findProductByOrderByIdDesc();
    }


    public boolean deleteProduct(Long id) {
        if(productsRepository.existsById(id)){//in CrudRepository
            this.productsRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public void save(Product product) {
        this.productsRepository.save(product);
    }


    public Product getProductById(Long id) {
        Optional<Product> product = this.productsRepository.findById(id);
        return product.orElse(null);
    }

    public Optional<Product> findById(Long id) {
        return productsRepository.findById(id);
    }

    public Optional<Product> findproductById(Long productId)  {
        return productsRepository.findById(productId);
    }
}
