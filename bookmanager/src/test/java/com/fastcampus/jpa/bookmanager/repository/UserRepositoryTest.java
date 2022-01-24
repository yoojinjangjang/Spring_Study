package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
       // ** save 메서드 **
        userRepository.save(new User()); //인자로 받은 객체를 테이블에 저장하겠다는 의미이다.

    }

    @Test
    void findall() {
        // ** findAll 메서드 **
        System.out.println(">>> " + userRepository.findAll());
        //실제로 일반적으로 성능이슈로 인해 사용하지 않는다.
        //우리가 사용하는 많은 메소드는 CrudRepository 클래스 내에 저장되어있다.


        userRepository.findAll().stream().forEach(System.out::println); //리스트의 각 요소를 한줄씩 출력[스트림,람다 학습]

        // findAll 메서드의 sort 인자 지정
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "name")); //findall 의 인자로 sort기준을 넣어줄 수 있다.
        users.forEach(System.out::println);
    }

    @Test
    void findallbyid() {
        // findAllbyId 메서드 : id iterable을 받아서 해당 id에 해당하는 값 리스트를 반환
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);

       // List<User> users1 = userRepository.findAllById(ids);

        List<User> users1 = userRepository.findAllById(Lists.newArrayList(1L, 2L, 3L)); //test 에서 사용가능한 iterable를 간단하게 만들어주는 메서드 Lists.newArrayList(요소)
        users1.forEach(System.out::println);
    }

    @Test
    void saveall(){
        // ** savaAll 메서드 **
        User user1 = new User("jack", "jack@naver.com");
        User user2 = new User("steve", "steve@naver.com");

        userRepository.saveAll(Lists.newArrayList(user1, user2));

        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);


    }

    @Test
    @Transactional
    void getone(){
        //getOne메서드 : entity를 받아온다. -- > 에러가 발생한다. proxy init할수 없다. ( entity 에 대해 lazy 연결 @Transactional 필요)
        User user = userRepository.getOne(1L);
        System.out.println(user);

    }

    @Test
    void findbyid(){
        //findbyId 메서드: optional 맵핑된 entity를 받아온다.
      //  Optional<User> user = userRepository.findById(1L);  //optional객체로 받아온다.
        User user = userRepository.findById(1L).orElse(null); //값이 없을 경우 null을 가져온다.

        System.out.println(user);

    }

    @Test
    void flu(){
        //flush 메서드 , savaandflush 메서드

       // userRepository.save(new User("new yooijn", "new yoojin@naver.com"));
       // userRepository.flush();
        userRepository.saveAndFlush(new User("new yoojin", "new yoojin@google.com"));
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void cou(){
        //count 메서드 : long 형식 반환 , 해당 요소의 개수를 반환한다.
        long count = userRepository.count();
        System.out.println(count);
    }


    @Test
    void  exist(){
        //exist 메서드: 해당 id 존재 여부 반환 boolean
        boolean exist = userRepository.existsById(1L);
        System.out.println(exist);
    }

    @Test
    void deleteentity(){
        //delete(entity)메서드 : 해당 entity 를 제거한다.
        userRepository.delete(userRepository.findById(2L).orElseThrow(RuntimeException::new));

        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void deletebyid(){
        //deletebyid 메서드 : 해당 id를 가진 entity 를 제거한다.  delete쿼리전에 해당 엔티티 존재여부 먼저 확인하므로 select 쿼리가 먼저 나온다.
        userRepository.deleteById(2L);
        userRepository.findAll().forEach(System.out::println);
    }


    @Test
    void deleteall(){
        // deleteAll 메서드 : 요소를 삭제 , 요소 수만큼 삭제를 반복한다. 성능 이슈로 인해 실제로 사용하지 않는다.
        //userRepository.deleteAll(); //모든 요소를 삭제
        userRepository.deleteAll(userRepository.findAllById(Lists.newArrayList(1L,3L))); // 해당 id를 가진 모든 요소들을 삭제한다.
        userRepository.findAll().forEach(System.out::println);

    }

    @Test
    void deletebatch(){ //delete전에 체크하는 select문 없어 바로 해당 id 에 대한 delete를 수행한다.
        userRepository.deleteInBatch(userRepository.findAllById(Lists.newArrayList(1L,3L)));
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void deleteallinbatch(){ //delete전 체크하는 select 문 없이 모든 요소 삭제
        userRepository.deleteAllInBatch();
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void paging(){
        //paging 위한 메서드 :  findAll 메서드의 인자로 pageRequest 객체를 받고 page 객체를 반환한다.
        Page<User> users = userRepository.findAll(PageRequest.of(1, 3)); // 3개씩 요소들을 한페이지에 담고 1페이지를 불러오기 [0 page부터 시작한다]
        System.out.println("page: " + users);
        System.out.println("total elements: " + users.getTotalElements()); // 총 요소 수
        System.out.println("total pages : "  + users.getTotalPages()); //page 수 는 2 장 ( 5를 3으로 나눳음 )
        System.out.println("number of elements : "  + users.getNumberOfElements()); // 현재 페이지 요소 수
        System.out.println("sort : " + users.getSort()); //sort 정보
        System.out.println("size : " + users.getSize()); // poging 시 나누는 크기
        users.getContent().forEach(System.out::println); // 현재 페이지의 요소들의 정보

    }

    @Test
    void qbe(){
        //entity 를 example로 만들고 mathcher를 추가 선언하여 필요한 쿼리들을 만드는 방법
        //오래되지 않음 , 처음부터 제공하던 클래스 아님! query by Example 로 검색
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("name") //matching시 무시하겟다.
                .withMatcher("email",endsWith()); //matching하겠다. endsWith() : like 검사

        Example<User> example = Example.of(new User("ma","google.com"),matcher); //해당 email과 match된 엔티티들만을 가져온다.
        //probe만 가지고 match 를 진행할 경우 exact 매치로 이루어진다. (probe는 entity 가짜 객체)
        userRepository.findAll(example).forEach(System.out::println); // findAll인자로 해당 example 객체를 넣어준다.

    }

    @Test
    void qbe2(){
        User user = new User();
        user.setEmail("naver");

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("email", contains());//contains : 양방항 like 검사
        Example<User> example = Example.of(user,matcher);

        userRepository.findAll(example).forEach(System.out::println);
    }


    @Test
    void update(){
        userRepository.save(new User("david", "david@google.com"));

        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setEmail("yoojin-update@naver.com");

        userRepository.save(user); //update에서도 해당 entity 가 존재하는지 select가 먼저 실행된다 . 해당 id가 있을경우 update가 실행된다.

        userRepository.findAll().forEach(System.out::println
        );

    }







}