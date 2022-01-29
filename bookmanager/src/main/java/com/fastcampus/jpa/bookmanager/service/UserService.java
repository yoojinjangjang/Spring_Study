package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service

public class UserService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void put(){
        User user = new User();
        user.setName("newUser");
        user.setEmail("newUser@google.com");

        entityManager.persist(user);
        user.setName("newUserAfterPersist");

        User user1 = userRepository.findById(1L).get();
        entityManager.remove(user1); //해당 값 제거된다.

        user1.setName("yoooooooooooooooooooooooojin");
        entityManager.merge(user1); //remove된 객체는 merge되지 않는다. -> 에러 발생
    }


}
