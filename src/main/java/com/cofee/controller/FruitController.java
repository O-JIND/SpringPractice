package com.cofee.controller;

import com.cofee.entitiy.Fruit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FruitController {
    @GetMapping("/fruit")
    public Fruit test(){
        Fruit bean = new Fruit();
        bean.setPrice(1000);
        bean.setId("Banana");
        bean.setName("바나나");
        return bean;
    }
    @GetMapping("/fruit/list")
    public List<Fruit> Fruits(){
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Fruit("Apple","사과",8000));
        fruitList.add(new Fruit("Pineapple","파인애플",9000));
        fruitList.add(new Fruit("Green Apple","아오리 사과",5000));
        fruitList.add(new Fruit("Grape","포도",12000));
        return fruitList;
    }
}
