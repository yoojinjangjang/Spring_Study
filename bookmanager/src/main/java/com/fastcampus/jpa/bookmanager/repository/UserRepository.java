package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


//entity 객체를 저장하고 조회하기 위한 클래스 - repository
public interface UserRepository extends JpaRepository<User,Long> { //jparepository를 상속받고 제너릭으로 해당 객체와 pk값 타입을 넣어준다.
    // spring jpa 라이브러리가 지원해주는 영역이다.\
    // 많은 jpa 메서드를 지원해준다.



}
