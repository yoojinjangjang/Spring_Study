package com.fastcampus.jpa.bookmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert //insert  시 dynamic하게 실행 ( 즉, set이 실행되지 않으면 해당 필드는 insert문에서 빠지게 된다. )
@DynamicUpdate // update 시 필요한 영향을 받은 필드에만 처리가 된다.
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne
    @ToString.Exclude
    private Review review; // 하나의 리뷰에 여러개의 코멘트 ( 코멘트에서는 하나의 리뷰를 확인 )

    @Column(columnDefinition = "datetime(6) default now(6)")
    private LocalDateTime commentedAt;



}
