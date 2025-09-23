package com.cofee.common;

import com.cofee.constant.Category;
import com.cofee.entitiy.Product;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenerateData {
    //일반 Test 모드 에서는 @Value 사용 불가
    private final String imageFolder = "C:\\Users\\ict\\Desktop\\Image\\public_images";

    //image 를 List로 반환
    public List<String> getImageFileNames() {
        File folder = new File(imageFolder);
        List<String> imageFiles = new ArrayList<>();
        if(!folder.exists()||!folder.isDirectory()){
            //exist 존재하면 true
            //isDirectory 폴더이면 true
            System.out.println(imageFolder+"is not detected");
            return imageFiles;
        }
        String[] imageExtensions = {".jpg", ".jpeg", ".png"};
        File[] fileList = folder.listFiles();
        //모든 파일의 이름을 소문자로 변경 후 확장자와 비교후 조건에 부합하면 컬렉션에 추가
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile() && Arrays.stream(imageExtensions)
                        .anyMatch(ext -> file.getName().toLowerCase().endsWith(ext))) {
                            imageFiles.add(file.getName());
                }
            }
        }

        return imageFiles;
    }

    public Product createProduct(int index, String imageName) {
        Product product = new Product();

        switch (index % 3) {
            case 0:
                product.setCategory(Category.BREAD);
                break;
            case 1:
                product.setCategory(Category.BEVERAGE);
                break;
            case 2:
                product.setCategory(Category.CAKE);
                break;
        }

        String productName = getProductName();
        product.setName(productName);
        String description = getDescriptionData(productName);
        product.setDescription(description);
        product.setImage(imageName);
        product.setPrice(1000 * getRandomDataRange(1, 10));
        product.setStock(111*getRandomDataRange(1,9));
        LocalDate sysdate = LocalDate.now();
        product.setInput_date(sysdate.minusDays(index));
        return product;
    }

    private int getRandomDataRange(int start, int end) {
        // start <= somedata <= end
        return new Random().nextInt(end) + start;
    }

    private String getDescriptionData(String name) {
        String[] description = {"엄청 달아요.", "맛있어요.", "맛없어요.", "떫어요.", "엄청 떫어요.", "아주 떫어요.", "새콤해요.", "아주 상큼해요.", "아주 달아요."};
        return name + "는(은) " + description[new Random().nextInt(description.length)];
    }

    private String getProductName() {
        String[] fruits = {"아메리카노", "바닐라라떼", "우유", "에스프레소", "크로아상", "치아바타", "당근 케이크"};
        return fruits[new Random().nextInt(fruits.length)];
    }
}


