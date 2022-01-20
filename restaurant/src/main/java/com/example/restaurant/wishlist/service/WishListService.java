package com.example.restaurant.wishlist.service;

import com.example.restaurant.naver.NaverClient;
import com.example.restaurant.naver.dto.SearchImageReq;
import com.example.restaurant.naver.dto.SearchLocalReq;
import com.example.restaurant.wishlist.dto.WishListDto;
import com.example.restaurant.wishlist.entity.WishListEntity;
import com.example.restaurant.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final NaverClient naverClient;
    private final WishListRepository wishListRepository;

    public WishListDto search(String query){  //해당 쿼리를 받아서 검색한 결과를 반환해주는 서비스


        // 지역검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        var searchLocalRes = naverClient.searchLocal(searchLocalReq); //해당 요청 query에 대한 지역검색 결과 받아오기
        if(searchLocalRes.getTotal() >0){ //지역검색한 결과가 있을 경우
            var localItem = searchLocalRes.getItems().stream().findFirst().get(); // 해당 검색결과의 첫번째 localItem 가져오기


            // image 검색 위한 쿼리 생성
            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>",""); //필요없는 문자열 없애는 처리

            //해당 쿼리로 이미지 검색
            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);

            var searchImageRes = naverClient.searchImage(searchImageReq);

            if(searchImageRes.getTotal()>0){

                var imageItem = searchImageRes.getItems().stream().findFirst().get();
                //해당 결과를 리턴
                var result = new WishListDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setImageLink(imageItem.getLink());
                result.setHomePageLink(localItem.getLink());


                return result;
            }
        }

        return new WishListDto();




    }


    public WishListDto add(WishListDto wishListDto) { //데이터베이스에 해당 객체를 저장한다.

        //dto 를 entity 로 바꾼후에 db에 저장한다.
        var entity = dtoToEntity(wishListDto);

        var saveEntity = wishListRepository.save(entity);

        return entityToDto(saveEntity);


    }

    //dto를 entity로 변경
    private WishListEntity dtoToEntity(WishListDto wishListDto){
        var entity = new WishListEntity();
        entity.setTitle(wishListDto.getTitle());
        entity.setCategory(wishListDto.getCategory());
        entity.setIndex(wishListDto.getIndex());
        entity.setAddress(wishListDto.getAddress());
        entity.setRoadAddress(wishListDto.getRoadAddress());
        entity.setHomePageLink(wishListDto.getHomePageLink());
        entity.setImageLink(wishListDto.getImageLink());
        entity.setVisit(wishListDto.isVisit());
        entity.setVisitCount(wishListDto.getVisitCount());
        entity.setLastVisitDate(wishListDto.getLastVisitDate());

        return entity;
    }

    //entity를 dto로 변경
   private WishListDto entityToDto(WishListEntity wishListEntity){
        var dto = new WishListDto();
       dto.setTitle(wishListEntity.getTitle());
       dto.setCategory(wishListEntity.getCategory());
       dto.setIndex(wishListEntity.getIndex());
       dto.setAddress(wishListEntity.getAddress());
       dto.setRoadAddress(wishListEntity.getRoadAddress());
       dto.setHomePageLink(wishListEntity.getHomePageLink());
       dto.setImageLink(wishListEntity.getImageLink());
       dto.setVisit(wishListEntity.isVisit());
       dto.setVisitCount(wishListEntity.getVisitCount());
       dto.setLastVisitDate(wishListEntity.getLastVisitDate());

       return dto;
    }

    public List<WishListDto> findAll() {
        //entity 를  dto로 변환해주어야 한다. listall 메서드는 entity의 리스트를 반환하고 우리는 dto의 리스트를 사용할 것이다.
        return wishListRepository.findAll()
                .stream()
                .map(it->entityToDto(it))
                .collect(Collectors.toList());
    }

    public void delete(int index) {

        wishListRepository.deleteById(index);

    }

    public void addVisit(int index){
        var wishItem = wishListRepository.findById(index);
        if (wishItem.isPresent()) {
            var item = wishItem.get();
            item.setVisit(true);
            item.setVisitCount(item.getVisitCount()+1);


        }

    }
}
