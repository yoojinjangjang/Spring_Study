package com.example.restaurant.controller;

import com.example.restaurant.wishlist.dto.WishListDto;
import com.example.restaurant.wishlist.entity.WishListEntity;
import com.example.restaurant.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class ApiController {


    private final WishListService wishListService;


    @GetMapping("/search")  //검색한 쿼리 결과 반환 controller
    public WishListDto search(@RequestParam String query) {
        return wishListService.search(query);

    }


    //위시리스트 추가
    @PostMapping("")
    public WishListDto add(@RequestBody WishListDto wishListDto){
        log.info("{}",wishListDto);

        return wishListService.add(wishListDto); //해당 객체를 위시리스트에 추가하는 메서드


    }
    //전체리스트를 가져오는 controller

    @GetMapping("/all")
    public List<WishListDto> findAll(){

        return wishListService.findAll(); // 전체 리스틀를 가져오는 메서드
    }

    //방문등록
    @PostMapping("/{index}")
    public void addVisit(@PathVariable int index){
         wishListService.addVisit(index);
    }


    //삭제
    @DeleteMapping("/{index}")
    public void delete(@PathVariable int index) {
        wishListService.delete(index);
    }



}
