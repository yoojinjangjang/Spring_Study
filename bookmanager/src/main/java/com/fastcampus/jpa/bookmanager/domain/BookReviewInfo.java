package com.fastcampus.jpa.bookmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor // entity 는 기본 생성자가 필요하다.
@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookReviewInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    private Long bookId;
    @OneToOne(optional = false)   //1대1 연관관계 매핑 하겠다. book 을 직접 참조한다. --> DDL 에서는 해당 엔티티의 PK를 필드(참조키)로 사용한다.
    private Book book;

    private float averageReviewScore;

    private int reviewCount;  // null을 허용할지 않할지에 대해 primitive 와 wrapper타입중 선택하여 사용한다.
    // 0을 허용하기 위해 primitive를 사용한다. wrapperd 는 대문자로 작성 ( Float, Int : 널체크하지 않으면 exception 발생한다. )



}
