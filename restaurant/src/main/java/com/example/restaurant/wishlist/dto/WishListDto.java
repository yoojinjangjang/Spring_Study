package com.example.restaurant.wishlist.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class WishListDto{ //분리시켜서 사용한다.
    private Integer index;
    private String title;   //음식명
    private String category; //카테고리
    private String address; //주소
    private String roadAddress; //도로명
    private String homePageLink;    //홈페이지주소
    private String imageLink;   //음식 ,가게 이미지 주소
    private boolean isVisit;    //방문여부
    private int visitCount;     //방문 횟수
    private LocalDateTime lastVisitDate;     //마지막 방문일


}
