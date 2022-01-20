package com.example.restaurant.wishlist.repository;

import com.example.restaurant.db.MemoryDbRepositoryAbstract;
import com.example.restaurant.wishlist.entity.WishListEntity;
import org.springframework.stereotype.Repository;

@Repository //db를 저장하는 곳이다라는 의미의 어노테이션
public class WishListRepository extends MemoryDbRepositoryAbstract<WishListEntity> {



}
