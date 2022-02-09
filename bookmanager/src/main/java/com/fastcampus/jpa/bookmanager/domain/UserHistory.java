package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.domain.listener.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
//@EntityListeners(value= AuditingEntityListener.class)
public class UserHistory extends BaseEntity  { //history entity이기 떄문에 user의 정보를 모두 가지고 있어야한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


 //   @Column(name="user_id",insertable = false,updatable = false) //관계를 맺을 N 측 외래키에 필드 이름 설정 해주기
   // private Long userId;

    private String  name;

    private String email;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "city",column = @Column(name="home_city")),
                    @AttributeOverride(name = "district",column = @Column(name="home_district")),
                    @AttributeOverride(name = "detail",column = @Column(name = "home_address_detail")),
                    @AttributeOverride(name = "zipCode",column= @Column(name = "home_zip_code"))
            }
    )
    private Address homeAddress;

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "city",column = @Column(name="company_city")),
                    @AttributeOverride(name = "district",column = @Column(name="company_district")),
                    @AttributeOverride(name = "detail",column = @Column(name = "company_address_detail")),
                    @AttributeOverride(name = "zipCode",column= @Column(name = "company_zip_code"))
            }
    )
    private Address companyAddress;

    @ManyToOne // N대1관계 - 자신이 앞으로 오게 ( userhistory 가 many임 )
    @ToString.Exclude
    private User user; // userhistory  에서 관계를 맺은 user를 가지고 온다.






}
