package com.cofee.entitiy;

import com.cofee.constant.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter @Setter @ToString
@Table(name = "products")
public class Product { //entity coding 시 database 제약조건도 고려
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private  Long id;

    @Column(nullable = false)
    private  String name;

    @Column(nullable = false)
    @Min(value = 1000,message = "price is at leat over 1000￦")
    private  int price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    @Min(value = 5,message = "stock is at leat over 5")
    @Max(value = 4000,message = "stock is at leat under 50")
    private  int stock;

    @Column(nullable = false)
    @NotBlank(message = "image is necessary")
    private  String image;

    @Column(nullable = false,length = 1000)
    @NotBlank(message = "description is necessary")
    @Size(max = 1000,message = "description is maximum 1000")
    private  String description;


    private LocalDate input_date;

}
