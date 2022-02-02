package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.domain.listener.Auditable;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
//해당 필드들의 설정을 여러곳에서 해야하므로 이를 합치기 위한 클래스
@Data
@MappedSuperclass //해당 클래스의 필드를 상속받는 entity의 컬럼으로 포함시켜주겠다.
// 이 클래스를 entity들이 상속을 받으면 여러번 createAt과 updateAt필드에 대한 설정을 해줄 필요가 없게 된다.
// 또한, 밑의 entityListeners 설정도 해주지 않아도 여기서 모든게 수행된다.
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity implements Auditable {
    @CreatedDate
    @Column(nullable = false,columnDefinition = "datetime(6) default now(6)",updatable = false)
    private LocalDateTime createAt;
    @LastModifiedDate
    @Column(nullable=false,columnDefinition = "datetime(6) default now(6)")
    private LocalDateTime updateAt;

}
