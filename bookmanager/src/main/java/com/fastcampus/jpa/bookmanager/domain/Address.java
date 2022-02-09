package com.fastcampus.jpa.bookmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable //임베드를 할 수 있는 클래스를 표시하는 어노테이션 , 엔티티 내부에 표시를 할수 있게 된다.
public class Address {

    private String city;    //시
    private String district;//구
    @Column(name = "address_detail")
    private String detail;  //상세주소
    private String zipCode; //우편번호
}
