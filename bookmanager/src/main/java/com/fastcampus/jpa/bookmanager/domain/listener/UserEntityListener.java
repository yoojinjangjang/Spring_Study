package com.fastcampus.jpa.bookmanager.domain.listener;

import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.domain.UserHistory;
import com.fastcampus.jpa.bookmanager.repository.UserHistroyRepository;
import com.fastcampus.jpa.bookmanager.support.BeanUtils;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

// @Component  //autowired사용위해 필요
public class UserEntityListener { //entity listener에서는 스프링빈을 주입받지 못한다.
    //@Autowired
    //private UserHistroyRepository userHistroyRepository;



    //@PrePersist 는 insert 실행 전 호출--> insert 이전에는 db에 저장하지 않았기 때문에 id값이 존재하지 않는다.
    //@PreUpdate 는 update 실행 전 호출
    @PostPersist
    @PostUpdate
    public void prePersistandUpdate(Object o){ //User table에 update(insert) 호출 전에 수행할 메소드

        UserHistroyRepository userHistroyRepository =
                BeanUtils.getBean(UserHistroyRepository.class); //스프링 빈을 listener에서 받아오는 방법

        User user  = (User)o; //해당 object를 user 타입으로 형변환
        //userHistory 객체에 정보 지정 후 userHistroy db에 저장
        UserHistory userHistory = new UserHistory();
       // userHistory.setUserId(user.getId());
        userHistory.setName(user.getName());
        userHistory.setEmail(user.getEmail());
        userHistory.setUser(user);

        userHistroyRepository.save(userHistory);
    }
}
