package com.fastcampus.jpa.bookmanager.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

//@Getter
//@Setter
//@ToString
@NoArgsConstructor//반드시 필요하다. JPA에서는 !
@AllArgsConstructor
@RequiredArgsConstructor//필수 변수가 없을 경우 NoArgsConstructor 와 동일하게 동작한다.
@Data
@Entity //객체를 entity 로 선언해주는 어노테이션
public class User{ //entity 에는 primary Key 가 꼭 필요하다.
    @Id //pk 지정을 위한 어노테이션
    @GeneratedValue //순차적으로 증가시키기 위한 어노테이션
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;  //생성 시간,수정 시간 - jap domain 객체 항상 포함되어있다.



}
