package com.fastcampus.jpa.bookmanager.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class UserTest { //동일한 패키지 명 하위에 test클래스가 존재해야 한다.

    @Test
    public void test(){
        User user = new User();
        user.setEmail("yoojin@naver.OM");
        user.setName("yoojin");
        System.out.println(">>> " + user);

        User user1 = new User(null,"yoo", "yoo", LocalDateTime.now(), LocalDateTime.now());
        System.out.println(">>>" + user1);

        User user2 = new User("yoo", "yoo");
        System.out.println(">>> " + user2);


    }
}