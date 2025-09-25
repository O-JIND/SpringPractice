package com.cofee.controller;

import com.cofee.entitiy.Product;
import com.cofee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @GetMapping("/list")
    public List<Product> product(){
        return this.productService.getProductList();
    }

   @GetMapping("/Update/{id}")
       public ResponseEntity<Product> productById(@PathVariable  Long id){

        Product product = this.productService.getProductById(id);
        if(product==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else{
            return ResponseEntity.ok(product);
        }

   }


    @DeleteMapping("/delete/{id}")//{id} 는 경로 변수; 가변 매개 변수 | 특정 id 삭제 요청
    public ResponseEntity<String> delete(@PathVariable Long id){
        //{id} --> Long id, @PathVariable는 가변 매개변수를 메소드에 전달
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

    @PostMapping("/register")
    public ResponseEntity<?> getUpdate( @RequestBody Product product) throws FileNotFoundException {
    //Image's name that saved database and image path
    String imageFileName = "product_"+System.currentTimeMillis()+".jpg";

    //폴더 구분자가 제대로 설정 되어있으면 그대로 사용한다.; 아니면 폴더 구분자 추가
    String pathName = productImageLocation.endsWith("\\") || productImageLocation.endsWith("/")
            ? productImageLocation
            : productImageLocation + File.separator;

    File total_File = new File(pathName+imageFileName);
    //base 64 encoding String
    String imageData = product.getImage();
    FileOutputStream fos = new FileOutputStream(total_File);
    try{
        // file inform 을 byte 단위로 transform 하여 image를 복사
        byte[] decodedImage = Base64.getDecoder().decode(imageData.split(",")[1]);
        fos.write(decodedImage); // byte 파일을 해당 image 경로에 copy

        product.setImage(imageFileName);
        product.setInput_date(LocalDate.now());

        this.productService.save(product);
        return ResponseEntity.ok(Map.of("message","Product insert successfully","image",imageFileName));

    } catch (Exception e) {
        return ResponseEntity.status(500).body(Map.of("message",e.getMessage(),"error","error File uploading"));
    }

    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<?> putUpdate(@PathVariable Long id, @RequestBody Product Newproduct) {

        Optional<Product> findProduct = productService.findById(id);
        if(findProduct.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            Product saveProduct = findProduct.get();
            try{
                saveProduct.setName(Newproduct.getName());
                saveProduct.setPrice(Newproduct.getPrice());
                saveProduct.setCategory(Newproduct.getCategory());
                saveProduct.setStock(Newproduct.getStock());
                saveProduct.setDescription(Newproduct.getDescription());
                //saveProduct.setInput_date(LocalDate.now());
                if(Newproduct.getImage() !=null&& Newproduct.getImage().startsWith("data:image")) {
                    String imageFileName = saveProductImage(Newproduct.getImage());
                    saveProduct.setImage(imageFileName);
                }
                this.productService.save(saveProduct);
                return ResponseEntity.ok(Map.of("message","Update Complete"));

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

    private String saveProductImage (String base64image) {
        String imageFileName= "product_"+System.currentTimeMillis() + ".jpg";
        String pathName = productImageLocation.endsWith("\\") || productImageLocation.endsWith("/")
                ? productImageLocation
                : productImageLocation + File.separator;
        File total_File = new File(pathName+imageFileName);
        byte[] decodedImage = Base64.getDecoder().decode(base64image.split(",")[1]);

        try{
            FileOutputStream fos = new FileOutputStream(total_File);
            fos.write(decodedImage);
            return imageFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return base64image;
        }
    }
}

