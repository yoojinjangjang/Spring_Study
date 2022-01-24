package com.fastcampus.jpa.bookmanager.domain;

import lombok.*;

import java.time.LocalDateTime;

//@Getter
//@Setter
//@ToString
@NoArgsConstructor//반드시 필요하다. JPA에서는 !
@AllArgsConstructor
@RequiredArgsConstructor//필수 변수가 없을 경우 NoArgsConstructor 와 동일하게 동작한다.
@Data
public class User extends Object{

    @NonNull
    private String name;
    @NonNull
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;  //생성 시간,수정 시간 - jap domain 객체 항상 포함되어있다.



}
