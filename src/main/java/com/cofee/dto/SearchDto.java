package com.cofee.dto;

//필드 검색시 사용하는 Dto

import com.cofee.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor//인스턴스 변수 모두를 매개 변수로 입력 받는 생성자를 만들어 줌
public class SearchDto {
    //조회할 날짜 검색 범위를 선정하기 위한 변수, 현재 시간과 상품 입고일을 비교하여 처리;
    //All() , 1d , 1w ,1m , 6m

    private String searchDateType;//기간 검색 콤보 박스

    private Category category;// 검색하고자 하는 특정 카테고리 콤보 박스

    private String searchMode;// 상품 검색모드 콤보 박스 이름/ 설명

    private String searchByKeyword;//검색 키워드 입력상자

}
