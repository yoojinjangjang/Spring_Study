package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.domain.converter.BookStatusConverter;
import com.fastcampus.jpa.bookmanager.domain.listener.Auditable;
import com.fastcampus.jpa.bookmanager.repository.dto.BookStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor //entity 객체는 기본적으로 기본생성자가 필요하다.
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Where(clause = "deleted = false")
public class Book extends BaseEntity { //pk 필요
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //생성된 값 그대로 가져와서 사용한다. 기본값 : AUTO -> H2 DB 의 기본 값은 sequence이다. id값 생성시 매번 hibernate sequence 를 공용으로 사용한다.
    private Long id;

    private String name;

    private String category;

    //private Long authorId;

   // @Column(name="publisher_id")
 //   private Long publisherId;

    @OneToOne(mappedBy = "book")
    @ToString.Exclude
    private BookReviewInfo bookReviewInfo;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="book_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}) //insert 에 대해 영속성 전이를 일으키겠다.
    // book이 inser될때 publisher도 insert 를 같이 실행시켜라
    @ToString.Exclude
    private Publisher publisher;



    @OneToMany
    @JoinColumn(name="book_id")
   @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    private boolean deleted;


    //다른 시스템의 정보를 연동하는 경우에는 내가 원하지 않는 형태의 데이터 존재가 가능하다.
    // int형의 코드값을 통해서 상태를 저장하는 데이터 저장법은 보편화되었다.
 //   @Convert(converter = BookStatusConverter.class) // 엔티티 필드에 적용하는 어노테이션 , 어떤 컨버터를 적용하지 지정
    private BookStatus status; // 판매 상태
    //런타임 상태의 오류를 컴파일 시점에 미리 찾아준다.


/*    public boolean isDisplayed(){
        return status == 200;
    }*/

    public void addBookAndAuthors(BookAndAuthor... bookAndAuthors){
    Collections.addAll(this.bookAndAuthors, bookAndAuthors);
    }
}
