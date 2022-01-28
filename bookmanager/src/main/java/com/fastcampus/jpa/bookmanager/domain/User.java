package com.fastcampus.jpa.bookmanager.domain;
import com.fastcampus.jpa.bookmanager.domain.listener.Auditable;
import com.fastcampus.jpa.bookmanager.domain.listener.UserEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor//반드시 필요하다. JPA에서는 !
@AllArgsConstructor
@RequiredArgsConstructor//필수 변수가 없을 경우 NoArgsConstructor 와 동일하게 동작한다.
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity //객체를 entity 로 선언해주는 어노테이션
@EntityListeners(value = {UserEntityListener.class})  // listener 등록 어노테이션
public class User extends BaseEntity { //entity 에는 primary Key 가 꼭 필요하다.
    //extends가 implements보다 먼저와야한다

    @Id //pk 지정을 위한 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //순차적으로 증가시키기 위한 어노테이션
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;


    //관계를 맺을 1측 필드에 @OneToMany와 @JoinColumn 달아주기 --> 1대N관계 : user에 해당하는 userHistory list를 자동 관계를 맺어준다.
    @OneToMany
    @JoinColumn(name = "user_id",insertable = false,updatable = false) //entity 가 어떤 컬럼으로 조인할지 지정해주는 어노테이션( 관계를 맺을 entity 중 조인 할 컬럼의 이름을 지정해준다. )
    // insertable과 updateable 을 false로 지정하여 user table 에서 해당 필드를 수정하거나 저장하지 못하게 한다.
    @ToString.Exclude
    private List<UserHistory> userHistories = new ArrayList<>(); // 널값 방지 위해 초기화 해준다.

    @OneToMany
    @JoinColumn(name="user_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();



}
