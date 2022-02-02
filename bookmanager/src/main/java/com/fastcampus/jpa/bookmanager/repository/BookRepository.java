package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.repository.dto.BookNameAndCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookRepository extends JpaRepository<Book,Long> {

    // entity 를 만들면 repository도 만들어야 한다.
    //entity : 객체 (핖드) 관련 설정 + listener 설정
    // repository :  entity 객체 저장 & 조회 위한 클래스

    @Modifying
    @Query(value  = "update book set category='none'",nativeQuery = true)
    void update();


    List<Book> findByCategoryIsNull();

    List<Book> findALlByDeletedFalse();

    List<Book> findByCategoryIsNullAndNameEqualsAndCreateAtGreaterThanEqualAndUpdateAtGreaterThanEqual(String name, LocalDateTime createdAt, LocalDateTime updatedAt);



    @Query(value = "select b from Book b " +
            "where name = :name and create_at >= :createAt and update_at >= :updateAt and category is null")
    List<Book> findByNameRecently(
            @Param("name") String name,
            @Param("createAt") LocalDateTime createAt,
            @Param("updateAt") LocalDateTime updateAt);

    @Query(value="select new com.fastcampus.jpa.bookmanager.repository.dto.BookNameAndCategory(b.name ,b.category ) from Book b")
    List<BookNameAndCategory> findBookNameAndCategory();

    @Query(value="select new com.fastcampus.jpa.bookmanager.repository.dto.BookNameAndCategory(b.name ,b.category ) from Book b")
    Page<BookNameAndCategory> findBookNameAndCategory(Pageable pageable);

    @Query(value= "select * from book",nativeQuery = true) //entity 속성들을 사용하지 못한다.
    // 정해놓은 테이블이름과 속성을 사용해야 한다.
    List<Book> findAllCustom();

    @Transactional
    @Modifying // 일반적으로 DML 작업에서는 리턴되는 값이 단순히 적용된 로우갯수로 표시된다.
    @Query(value = "update book set category = 'IT전문서'",nativeQuery = true)
    int updateCategories(); //한번의 쿼리로 업데이트 가능
    // 반환값이 void,int,long-> update 된 로우수를 반환받게 된다.


    @Query(value = "show tables",nativeQuery = true)
    List<String> showTables(); //일반적인 jpql 쿼리로 생성 불가


    @Query(value = "select * from book order by id desc limit 1", nativeQuery = true)
    Map<String ,Object> findRowRecord();


}
