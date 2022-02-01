package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book,Long> {

    // entity 를 만들면 repository도 만들어야 한다.
    //entity : 객체 (핖드) 관련 설정 + listener 설정
    // repository :  entity 객체 저장 & 조회 위한 클래스

    @Modifying
    @Query(value  = "update book set category='none'",nativeQuery = true)
    void update();

}
