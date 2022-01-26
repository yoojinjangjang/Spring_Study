package com.fastcampus.jpa.bookmanager.domain;
import com.fastcampus.jpa.bookmanager.domain.listener.Auditable;
import com.fastcampus.jpa.bookmanager.domain.listener.UserEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Getter
//@Setter
//@ToString
@NoArgsConstructor//반드시 필요하다. JPA에서는 !
@AllArgsConstructor
@RequiredArgsConstructor//필수 변수가 없을 경우 NoArgsConstructor 와 동일하게 동작한다.
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity //객체를 entity 로 선언해주는 어노테이션
@EntityListeners(value = {UserEntityListener.class})  // listener 등록 어노테이션
//@Table(name = "user", indexes = {@Index(columnList = "name")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends BaseEntity { //entity 에는 primary Key 가 꼭 필요하다.
    //extends가 implements보다 먼저와야한다

    @Id //pk 지정을 위한 어노테이션
    @GeneratedValue //순차적으로 증가시키기 위한 어노테이션
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

/*    @Column(updatable = false) // update 시 해당 필드는 사용되지 못한다. --DML 에서 사용 된다.
    @CreatedDate
    private LocalDateTime createAt;

   // @Column(insertable = false) //insert 시 해당 필드는 사용되지 못한다.
    @LastModifiedDate
    private LocalDateTime updateAt;  //생성 시간,수정 시간 - jap domain 객체 항상 포함되어있다.*/

    //db 레코드에 적용하지 않고 객체에서 따로 사용하고 싶은 경우
    @Transient // 해당 어노테이션이 붙어있는 필드는 영속성에 제외되기 때문에 db에 적용되지 않고 해당 객체와 생명주기를 같이하는 값이 된다. 즉 ddl 등 반영되지 않는 값이다.
    private String testDate;


    //enum 처리 : 자바에서 사용하는 일종의 상수 객체 , entity에서 별도의 처리 방법을 가진다.
    @Enumerated(value = EnumType.STRING)
    private Gender gender; // enum의 순서가 0 index이므로 첫번째 있는 값이 0 두번째 있는 값이 1로 지정이 된다. 자동으로 mapping이 되므로 test에서 확인이 되지 않는다.
    // db에서 예상치 못한값이 저장되거나 리팩토리 과정에서 순서가 바뀌게 되면 값이 틀어질 수 있기 때문에 ordinal로 사용을 하는 경우 문제가 발생할 수 있다.
    // value 속성의 enumType.string으로 지정해주어야 한다. 그래야 실제 db에 저장값도 string으로 지정이 된다.



    /*

    @OneToMany(fetch = FetchType.EAGER)
    private List<Address> address;
*/
/*
    //jpa에서 제공하는 event : 7개를 제공한다. 메소드이름은 어노테이션의 이름과 동일할 필요는 없다.

    @PrePersist //insert 메소드가 호출되기 전에 호출되는 메소드
    public void prePersist(){
        System.out.println(">>> prePersist");
    }
    @PreUpdate  //merge 메소드 호출 전
    public void preUpdate(){
        System.out.println(">>> preUpdate");
    }
    @PreRemove //delete 메소드 호출 전
    public void preRemove(){
        System.out.println(">>> preRemove");
    }
    @PostPersist //insert 메소드 호출된 이후
    public  void postPersist(){
        System.out.println(">>> postPersist");
    }
    @PostUpdate //merge 메소드 호출 이후
    public void postUpdate(){
        System.out.println(">>> postUpdate");
    }
    @PostRemove //delete메소드 호출 이후
    public void postRemove(){
        System.out.println(">>> postRemove");
    }
    @PostLoad //select 조회 일어난 직후
    public void postLoad(){
        System.out.println(">>> postLoad");
    }

*/
    //prePersist, preUpdate : 감시 위해 가장 현업에서 많이 사용한다.
    //createAt과 updateAt 속성은 대부분 함께 넣어서 설계하도록 되어있다.

 /*   @PrePersist
    public void prePersist(){
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updateAt = LocalDateTime.now();
    }*/
}
