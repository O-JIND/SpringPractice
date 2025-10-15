package com.cofee.controller;

import com.cofee.constant.Category;
import com.cofee.dto.SearchDto;
import com.cofee.entitiy.Product;
import com.cofee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Value("${productImageLocation}")
    private String productImageLocation;

    @Autowired
    private ProductService productService;

//    /*
//    @GetMapping("/list")
//    public List<Product> product() {
//        return this.productService.getProductList();
//    }
//
//    */

    //    @GetMapping("/list")
//    public ResponseEntity<Page<Product>> listPaging(
//            @RequestParam(defaultValue = "0") int pageNumber,
//            @RequestParam(defaultValue = "8") int pageSize
//    ) {//use Paging to list up
//        System.out.println("pageNumber : " + pageNumber + ", pageSize : " + pageSize);
//
//        //현재 페이지 : pageNumber , 페이지 당 보여줄 갯수 : pageSize
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
//        Page<Product> productPage = productService.listProducts(pageable);
//
//        return ResponseEntity.ok(productPage);
//    }
    //필드 검색 조건과 페이징 관련 파라미터를 사용하여 상품 목록을 조회한다.
    @GetMapping("/list")
    public ResponseEntity<Page<Product>> listPaging(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "8") int pageSize,
            @RequestParam(defaultValue = "all") String searchDateType,
            @RequestParam(defaultValue = "") Category category,
            @RequestParam(defaultValue = "") String searchMode,
            @RequestParam(defaultValue = "") String searchByKeyword
    ) {//use Paging to list up
        SearchDto searchDto = new SearchDto(searchDateType, category, searchMode, searchByKeyword);


        Page<Product> products = productService.listProducts(searchDto, pageNumber, pageSize);
        System.out.println("searchMode : " + searchDto);
        System.out.println("element count : " + products.getTotalElements());
        System.out.println("total page num : " + products.getTotalPages());
        System.out.println("page num : " + products.getNumber());
        // Http 응답 코드 200과 함께 상품 정보를 json 형태로 반환해 준다.
        return ResponseEntity.ok(products);
    }

    @GetMapping("/Update/{id}")
    public ResponseEntity<Product> productById(@PathVariable Long id) {

        Product product = this.productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(product);
        }

    }


    @DeleteMapping("/delete/{id}")//{id} 는 경로 변수; 가변 매개 변수 | 특정 id 삭제 요청
    public ResponseEntity<String> delete(@PathVariable Long id) {
        //{id} --> Long id, @PathVariable는 가변 매개변수를 메소드에 전달
        try {
            boolean idDeleted = this.productService.deleteProduct(id);
            if (idDeleted) {
                return ResponseEntity.ok("Successfully Deleted");
            } else {
                return ResponseEntity.badRequest().body("Delete Failure " + id + "is not found");
            }
        } catch (DataIntegrityViolationException e) {
            String msg = "this product is already contain in Cart or OrderList  ";
            return ResponseEntity.badRequest().body(msg);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error occurred : " + ex.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> getUpdate(@RequestBody Product product) throws FileNotFoundException {
        //Image's name that saved database and image path
        String imageFileName = "product_" + System.currentTimeMillis() + ".jpg";

        //폴더 구분자가 제대로 설정 되어있으면 그대로 사용한다.; 아니면 폴더 구분자 추가
        String pathName = productImageLocation.endsWith("\\") || productImageLocation.endsWith("/")
                ? productImageLocation
                : productImageLocation + File.separator;

        File total_File = new File(pathName + imageFileName);
        //base 64 encoding String
        String imageData = product.getImage();
        FileOutputStream fos = new FileOutputStream(total_File);
        try {
            // file inform 을 byte 단위로 transform 하여 image를 복사
            byte[] decodedImage = Base64.getDecoder().decode(imageData.split(",")[1]);
            fos.write(decodedImage); // byte 파일을 해당 image 경로에 copy

            product.setImage(imageFileName);
            product.setInput_date(LocalDate.now());

            this.productService.save(product);
            return ResponseEntity.ok(Map.of("message", "Product insert successfully", "image", imageFileName));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", e.getMessage(), "error", "error File uploading"));
        }

    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<?> putUpdate(@PathVariable Long id, @RequestBody Product Newproduct) {

        Optional<Product> findProduct = productService.findById(id);
        if (findProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Product saveProduct = findProduct.get();
            try {
                saveProduct.setName(Newproduct.getName());
                saveProduct.setPrice(Newproduct.getPrice());
                saveProduct.setCategory(Newproduct.getCategory());
                saveProduct.setStock(Newproduct.getStock());
                saveProduct.setDescription(Newproduct.getDescription());
                //saveProduct.setInput_date(LocalDate.now());
                if (Newproduct.getImage() != null && Newproduct.getImage().startsWith("data:image")) {
                    String imageFileName = saveProductImage(Newproduct.getImage());
                    saveProduct.setImage(imageFileName);
                }
                this.productService.save(saveProduct);
                return ResponseEntity.ok(Map.of("message", "Update Complete"));

            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of(
                                "message", e.getMessage(),
                                "error", "error File uploading"
                        ));
            }
        }

    }

    private String saveProductImage(String base64image) {
        String imageFileName = "product_" + System.currentTimeMillis() + ".jpg";
        String pathName = productImageLocation.endsWith("\\") || productImageLocation.endsWith("/")
                ? productImageLocation
                : productImageLocation + File.separator;
        File total_File = new File(pathName + imageFileName);
        byte[] decodedImage = Base64.getDecoder().decode(base64image.split(",")[1]);

        try {
            FileOutputStream fos = new FileOutputStream(total_File);
            fos.write(decodedImage);
            return imageFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return base64image;
        }
    }

    @GetMapping("/specific/{id}")
    public ResponseEntity<Product> specificMatch(@PathVariable Long id) {
        Product product = this.productService.getProductById(id);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(product);
        }
    }


    @GetMapping("")
    public List<Product> getBigsizeProducts(@RequestParam(required = false) String filter) {
        return productService.getProductsByFilter(filter);
    }


}

