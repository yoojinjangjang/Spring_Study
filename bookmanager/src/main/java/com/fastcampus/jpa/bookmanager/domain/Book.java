package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.domain.listener.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor //entity 객체는 기본적으로 기본생성자가 필요하다.
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
//@EntityListeners(value = AuditingEntityListener.class)
public class Book extends BaseEntity { //pk 필요
    @Id
    @GeneratedValue //생성된 값 그대로 가져와서 사용한다.
    private Long id;

    private String name;

    private String author;

  /*  @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;
*/
/*
    @PrePersist
    public void prePersist(){
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updateAt = LocalDateTime.now();
    }*/

    //createAt  과 updateAt 는 많은 객체에서 지정을 해줘야하므로 일일히 지정하는것은 효율적이지 않다. 이럴때 이용하는 것이 엔티티 리스너를 지정해서 활용하는 방법이다.

}
