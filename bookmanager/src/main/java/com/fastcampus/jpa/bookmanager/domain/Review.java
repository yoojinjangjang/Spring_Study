package com.fastcampus.jpa.bookmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private float score;

    @ManyToOne(fetch = FetchType.LAZY) //필요한 시점에만 쿼리를 실행한다.
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Book book;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id") //중간 테이블 막기 위하여 조인 컬럼 설정 ( OneToMany에 붙임 ) 왜?
    //해당 필드와 연결된 필드 ( comments들이 하나의 review_id와 연결 ) 
    private List<Comment> comments;
}
