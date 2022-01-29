package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class EntityManagerTest {

    @Autowired
    private EntityManager entityManager; //주입받아 사용할수 있다.
    @Autowired
    private UserRepository userRepository;
    @Test
    void entityManagerTest(){
        System.out.println(entityManager.createQuery("select u from User u").getResultList() ); //jpql (jap에서 사용하는 쿼리), User의 정보를 모두 가져와라
        // entityManager를 사용하지 않도록 mapping을 하여 제공하는 것을 사용하였다. 내부 실제 동작은 entityManager를 통하여 실행된다.
        // jpa에서 제공하지 않는 기능을 사용하거나 성능이슈 특별 문제로 별도 커스터마이징을 위해서는 entityManager를 직접 사용하면된다.
        // 영속성 context내에서 entity를 관리하고 있는 엔티티매니저는 캐시를 가진다. 실제로 세이브 메소드 실행시 디비에 반영되지않는다.
        // 영속성 컨텍스와 실제 디비사이에 일종의 데이터갭이 생긴다.


    }

    @Test
    void cacheFindTest(){ //동일 결과 여러번 호출
        System.out.println(userRepository.findByEmail("yoojin@naver.com"));
        System.out.println(userRepository.findByEmail("yoojin@naver.com"));
        System.out.println(userRepository.findByEmail("yoojin@naver.com"));
        //**findByEmail** => 디비에 직접 쿼리로 조회한다.
        System.out.println(userRepository.findById(2L).get());
        System.out.println(userRepository.findById(2L).get());
        System.out.println(userRepository.findById(2L).get());
        //**findById** = >  조회시 entity cache에서 직접조회한다.(디비에 접근하지 않는다.) ==> JPA의 1차 캐시
        // 캐시(map의 형태이다. key - id, value - entity , id로 조회시 영속성컨텍스트 내의 1차 캐시에 엔티티있는지 확인한후 있으면 db에서 조회하지 않고 바로 값을 가져오고, 캐시에 없으면
        // 실제 쿼리로 조회를 해서 결과값을 1차 캐시에 저장후 리턴한다.
        //jpa특성상 id값을 가진 조회가 빈번하게 일어난다. -> 해당 로직에 대한 성능저하가 생길수 있는데 1차 캐시를 이용하여 그를 줄였다.


        userRepository.deleteById(1L); //jpa내부적으로 id조회가 빈번하므로 하나의 트랜잭션안에서는 1차 캐시를 사용하여 ( id와 entity저장 하고 있음 ) 성능저하를 방지한다.
        //select 조회 -> delete 진행 : update와 delete는 id값으로 조회하는 케이스 가 생긴다.
        // @Transaction 이 존재하는 상태에서 test를 실행 하면 select만 이루어지고 delete는 이루어지지 않는다. -> 최종 커밋이 되지 않고 rollback transaction 으로 해당 쿼리가 영속성 컨텍스트 내에만 존재하고 실제 디비에 적용되지 않았다.

    }

    @Test
    void cachFindTest2(){
        User user = userRepository.findById(1L).get();
        user.setName("yoooooooooojin"); // update
        userRepository.save(user);
   //     userRepository.flush();
        System.out.println("-------------------");


        user.setEmail("yooooooooojin@naver.com"); //update
        userRepository.save(user);

        System.out.println(userRepository.findAll()); // select * from user --> 영속성 컨텍스트에서 가져올 것 이다. ( 변경 시 더 최신인 값을 사용해야 한다. 데이터를 비교해서 merge해야 되는 가정이 추가된다. )
        // 영속성 캐시에 있던 값을 flush시키고 디비 반영후 다시 쿼리를 실행하여 디비의 값을 가져온다.

 //       userRepository.flush(); //디비에 적용하라는 메소드 이다. 영속성 컨텍스트에 쌓여있는 데이터는 엔티티매니저가 자동으로  영속화를 해준다. 개발자가 의도한 시간에 디비 영속화가 일어나진 않는다.
        //개발자가 원한느 시간에 디비 영속화를 해주기 위해 사용하는 메소드이다.
        //영속성 캐시에 쌓여서 아직 반영되지 않은 엔티티의 변경을 해당 메소드 실행시점에 모두 디비에 반영해주는 역할을 한다.
        // 남발시에는 캐시의 장점을 무력화하므로 적당히 사용해야 한다.
        // 영속성 캐시 ! 어렵다. --> 개발자가 저장하겠다고 명시적으로 save해도 디비에 반영되지 않은 경우가 발생한다.


        // transaction 이 묶여있지 않기 때문에 쿼리는 그땍그때 모두 일어난다.
        //상위에 transaction이 존재하지 않으면, 내부에는 각각이 하나의 transaction이 된다. 즉 transaction 이 끝나므로 쿼리를 매번 실행하게 된다.
        // 묶어주게 되면 전체가 하나의 transaction이 된다.(디비에 바로 반영되지 않는다.==쿼리를 실행하지않음)  --> entity cache를 통해 쓰기지연이 일어난다. (flush()로 디비 반영)
        // 캐시에서 모든 변경정보를 가지고 있다가 transaction 이 종료되면 한번에 merge를 실행하여 한번의 디비 접근을 수행한다.



        //영속성 캐시가 flush되어서 실제 디비와 동기화 되는 시점?
        // 1. flush()메소드 호출시 : 자동 flush보다는 개발자가 의도적으로 영속성 캐시를 디비에 반영하는 액션을 한다. 동기화 된다.
        // 2. transaction 종료시 : 명시적으로 @Transaction 작성하지 않으면 해당 라인이 하나의 transaction 이 되므로 그때그때 반영이 된다. ( 자동으로 flush가 일어나게 된다. ) ( 해당 쿼리가 commit 되는 시점 )
        // ------> 마지막 문장이후에 transaction 이 종료된다.( Test 에서는 commit이 일어나지 않고 roll back 처리가 된다. )
        // 3. jpql 쿼리가 실행될 떄 이다. --> 복잡한 조회 조건인 경우에 auto flush가 일어난다.


//        System.out.println(">>>> 1: " + userRepository.findById(1L).get()); //실제 디비와 영속성 컨텍스트와 차이가 발생하는 순간이다 ( update쿼리는 실행되지 않았지만 영속성 컨텍스트는 변경 되어 있댜. )
     //   userRepository.flush();  //실제 쿼리가 실행되는 시점이다. 디비와 영속성 컨텍스트가 동기화된다.


//        System.out.println(">>>>> 2: " + userRepository.findById(1L).get()); // 쿼리 없이 바로 값을 받아온다.  ( 영속성 캐시에서 값을 불러온다. )
    }
}
