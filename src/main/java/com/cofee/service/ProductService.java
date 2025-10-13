package com.cofee.service;

import com.cofee.dto.SearchDto;
import com.cofee.entitiy.Product;
import com.cofee.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
        if (productsRepository.existsById(id)) {//in CrudRepository
            this.productsRepository.deleteById(id);
            return true;
        } else {
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

    public Optional<Product> findproductById(Long productId) {
        return productsRepository.findById(productId);
    }


    public List<Product> getProductsByFilter(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return productsRepository.findByImageContaining(filter);
        }
        return null;
    }

    public Page<Product> listProducts(Pageable pageable) {
        return this.productsRepository.findAll(pageable);
    }

    //필드 검색 조건과 페이징 기본 정보를 사용하여 상품 목록을 조회하는 로직을 작성
    public Page<Product> listProducts(SearchDto searchDto, int pageNumber, int pageSize) {
        //specification 은 엔티티 객체에 대한 쿼리 조건을 정의할 수 있는 조건자로 사용
        Specification<Product> spec = Specification.where(null);

        // 기간
        if (searchDto.getSearchDateType() != null) {
            spec = spec.and(ProductSpecification.hasDateRange(searchDto.getSearchDateType()));
        }
        // 카테고리
        if (searchDto.getCategory() != null) {
            spec = spec.and(ProductSpecification.hasCategory(searchDto.getCategory()));
        }
        // Mode
        String searchMode = searchDto.getSearchMode();
        String searchKeyword = searchDto.getSearchByKeyword();
        if (searchMode != null && searchKeyword != null) {
            if ("name".equals(searchMode)) {
                spec = spec.and(ProductSpecification.hasNameLike(searchKeyword));
            } else if ("description".equals(searchMode)) {
                spec = spec.and(ProductSpecification.hasDesLike(searchKeyword));
            }

        }
        Sort sort = Sort.by(Sort.Order.desc("id"));
        //pageNumber 페이지 0base를 보여주되, sort 방식으로 정렬하여 pageSize 개씩 보여준다.
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);


        return this.productsRepository.findAll(spec, pageable);
    }
}
