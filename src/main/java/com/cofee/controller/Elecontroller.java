package com.cofee.controller;

import com.cofee.entitiy.Ele;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Elecontroller {



    @GetMapping("/eleList/randm")
    public Ele newlst(){
        List<Ele> newEle = new ArrayList<>(newlsts());
        int a = (int)(Math.random()*newlsts().size());
        Ele bean=newEle.get(a);
        return bean;
    }


    @GetMapping("/eleList")
    public List<Ele> newlsts(){
        List<Ele> EleList= new ArrayList<>();
        EleList.add(new Ele( 1,  "Ragu pasta2",  24000,  "food",  111,  "진하게 끓인 고기 소스와 파스타를 함께 버무린 요리입니다. 뭉근하게 끓여 부드럽고 깊은 맛이 나며, 면과 소스가 잘 어우러져 풍미가 좋습니다." ));
        EleList.add(new Ele( 2,  "MacNCheese2",  16000,  "food",  222, "마카로니와 치즈 소스를 섞어 만든 미국식 요리입니다. 고소하고 짭짤한 치즈 맛이 일품이며, 따뜻하고 부드러운 식감으로 남녀노소 누구나 즐기기 좋습니다." ));
        EleList.add(new Ele(   3,  "Tequila SunRise",   16000,  "drink",  333,  "데킬라에 오렌지 주스와 그레나딘 시럽을 섞어 만듭니다. 붉은색 그라데이션이 마치 일출처럼 아름다워 보는 즐거움이 있습니다." ));
        EleList.add(new Ele(   4,  "Black Russian",   12000,  "drink",  444,  "보드카와 커피 리큐어를 섞어 만든 칵테일입니다. 커피의 진하고 쌉쌀한 맛과 보드카의 깔끔함이 어우러져 깊고 부드러운 맛이 납니다."));
        EleList.add(new Ele(  5,  "Strawberry siru",   52000,  "cake",  99 ,  "성심당의 겨울 시즌 케이크입니다. 원래 이름은 '스트로베리 쇼콜라 케이크'였으나 '딸기 시루'로 바꾼 후 폭발적인 인기를 얻었습니다. 검은 브라우니 시트 위에 신선한 딸기와 생크림을 겹겹이 쌓아 올려 만듭니다." ));
        EleList.add(new Ele(  6,  "Mango siru",   43000,  "cake",  999,  "성심당의 여름 한정 케이크입니다. 부드러운 생크림과 달콤한 망고가 듬뿍 들어간 것이 특징이며, 망고 콩포트(망고 잼)가 들어있어 더욱 풍부한 맛을 냅니다." ));
        EleList.add(new Ele(  7,  "Vanilla Macaron",   2500,  "macaron",  120,  "부드럽고 달콤한 바닐라 크림이 들어 있는 프랑스식 디저트입니다. 겉은 바삭하고 속은 촉촉한 식감이 특징입니다." ));
        EleList.add(new Ele(  8,  "Strawberry Macaron",   2800,  "macaron",  90,  "상큼한 딸기 크림이 가득 들어 있는 마카롱으로, 달콤하면서도 상큼한 맛을 즐길 수 있습니다." ));
        return EleList;
    }






}
