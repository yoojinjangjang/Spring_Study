package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Gender;
import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.domain.UserHistory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHistroyRepository userHistroyRepository;
/*
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

        userRepository.findAll().forEach(System.out::println);

    }

    @Test
    void select(){

        System.out.println(userRepository.findByName("younseok")); //에러가 난다. unique 객체를 반환하지 않는다. --> list로 반환한다. where조건이 추가되었다.


        System.out.println("findByEmail: " + userRepository.findByEmail("yoojin@naver.com"));
        System.out.println("getByEmail: " + userRepository.getByEmail("yoojin@naver.com"));
        System.out.println("readByEmail: " + userRepository.readByEmail("yoojin@naver.com"));
        System.out.println("queryByEmail: " + userRepository.queryByEmail("yoojin@naver.com"));
        System.out.println("searchByEmail: " + userRepository.searchByEmail("yoojin@naver.com"));
        System.out.println("streamByEmail: " + userRepository.streamByEmail("yoojin@naver.com"));
        System.out.println("findUserByEmail: " + userRepository.findUserByEmail("yoojin@naver.com")); //동일 쿼리와 동일 값 나온다. --> 이중 코드가독성 가장 잘어울리는 것으로 골라서 사용한다.


        System.out.println("findSomethingByEmail" +  userRepository.findSomethingByName("yoojin"));

        System.out.println("findFirst1Byname" + userRepository.findFirst1Byname("yoojin"));
        System.out.println("findTop1Byname" + userRepository.findTop1Byname("yoojin"));
        System.out.println("findLast1ByName" + userRepository.findLast1ByName("yoojin"));

        //--------------------------------------------------------------------------//

       System.out.println("findByEmailandName: " + userRepository.findByEmailAndName("yoojin@naver.com","yoojin"));
        System.out.println("findByEmailorName: " + userRepository.findByEmailOrName("yoojin@naver.com","yoojin"));
        System.out.println("findByCreatedAtAfter: " + userRepository.findByCreateAtAfter(LocalDateTime.now().minusDays(1L))); //해당 날짜 이후의 값이 나옴(큰값이 나온다.)
        System.out.println("findByCreatedAtBefore: " + userRepository.findByCreateAtBefore(LocalDateTime.now().minusDays(1L))); //해당 날짜 이전의 값이 나옴(작은값이 나온다.)
        System.out.println("findByIdAfter:" + userRepository.findByIdAfter(4L)); //숫자값일경우 해당 값보다 큰값이 출력된다.
        System.out.println("findByCreateAtGreaterThan: " + userRepository.findByCreateAtGreaterThan(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByIdGreaterThanEquals: " + userRepository.findByIdGreaterThanEqual(4L));
        System.out.println("findByIdBetween: " + userRepository.findByIdBetween(2L,5L));

        //----------------------------------------------------------------------------//

        System.out.println("findByIdisNotNull: "  + userRepository.findByIdIsNotNull());
        //System.out.println("findByIdIsNotEmpty: "  + userRepository.findByAddressIsNotEmpty());
        System.out.println("findByNameIn : " + userRepository.findByNameIn(Lists.newArrayList("younseok","yoojin"))); //일반적으로 다른 쿼리의 결과값을 다시 쿼리에 넣기 위해 사용한다.
        System.out.println("findByNameNotIN: "  + userRepository.findByNameNotIn(Lists.newArrayList("younseok","yoojin")));
        System.out.println("findByNameStartingWith: " + userRepository.findByNameStartingWith("yoo"));
        System.out.println("findByNameEndingWith: " + userRepository.findByNameEndingWith("in"));
        System.out.println("findByNameContaining " + userRepository.findByNameContaining("oo"));
        System.out.println("findByNameLike: " + userRepository.findByNameLike("%oo%"));


        //--------------------------------------------------------------------------------//


    }

    @Test
    void pagingandSortingTest(){
        System.out.println("findTop1ByNaqme : "  + userRepository.findTop1Byname("yoojin"));
        System.out.println("findLast1ByNaqme : "  + userRepository.findLast1ByName("yoojin"));
        System.out.println("findTop1ByNameOrderByIdDesc : "  + userRepository.findTop1ByNameOrderByIdDesc("yoojin"));
        System.out.println("findFirstByNameOrderByIdDescEmailAsc : " + userRepository.findFirstByNameOrderByIdDescEmailAsc("yoojin"));
        System.out.println("findFirstByName: "  + userRepository.findFirstByName("yoojin",Sort.by(Sort.Order.desc("id"),Sort.Order.asc("email")))); //여러개 정렬기준 넣기
        //정렬기준이 여러개일경우 위의 방법을 사용하기 (메소드의 이름이 너무 길어지기 때문에 - OrderBy) ,메소드 하나로 여러가지 정렬기준을 넣어 사용할 수 있다. - 자유도 , 가독성 측면에서 효율적이다.



        //굉장히 많은 경우 리스트 형식으로 조회를 한다. 그러므로 page 기능을 알아야 한다. .jpa에서 많은 기능을 제공한다.
        System.out.println("findByNamewithPaging: " + userRepository.findByName("yoojin",PageRequest.of(0,1)).getContent()); //pageable객체로는 pageReuqest객체의 of 메소드를 사용한다.

    }


    @Test
    void insertandUpdateTest(){
        User user = new User();
        user.setName("martin");
        user.setEmail("martin@google.com");
        userRepository.save(user); // insert 문 동작

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("marrrrrrtin");
        userRepository.save(user2); //update 문 동작

        userRepository.findAll().forEach(System.out::println);

    }        */

    @Test
    void enumTest(){
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setGender(Gender.MALE);
        userRepository.save(user);

        userRepository.findAll().forEach(System.out::println);

        System.out.println(userRepository.findRowRecord().get("gender")); //gender 필드 에

    }



    @Test
    void listenerTest(){
        User user = new User();
        user.setEmail("martin2@google.com");
        user.setName("martin");
        userRepository.save(user); //insert 가 발생

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("marrrrrrtin");
        userRepository.save(user2); // update 가 발생

        userRepository.deleteById(4L); //remove 가 발생

    }


    @Test
    void prePersistTest(){
        User user = new User();
        user.setEmail("martin2@google.com");
        user.setName("martin");
   //     user.setCreateAt(LocalDateTime.now());
   //     user.setUpdateAt(LocalDateTime.now()); //이런 값들을 계속 반복해주는 것은 법칙에 어긋날뿐만아니라 개발자의 실수로 오류가 발생할 수 있는 요소가 된다.
        // 실제 저장 or 수정 시간을 알수 없게 된다. 그래서 entity에 prepersist와 preUpdate를 설정을 해서 메소드가 실행 전 자동으로 해당 값을 set하는 방식을 사용한다.

        userRepository.save(user);

        System.out.println(userRepository.findByEmail("martin2@google.com"));

    }

    @Test
    void preUpdateTest(){
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        System.out.println("as-is : " + user);

        user.setEmail("update@google.com");
        userRepository.save(user); //update 실행 --> updateAt 자동 수정

        System.out.println("to-be : " + userRepository.findAll().get(0)); //실제 db에 들어있는 값을 재호출
    }


    @Test
    void userHistoryTest(){
        User user = new User();
        user.setEmail("martinNew@google.com");
        user.setName("marrtinNew");
        userRepository.save(user); // insert가 수행된다.

        user.setName("martinNewNew");
        userRepository.save(user); //update 수행 --> preUpdate 부분에서 에러가 발생한다.(UserEntityListener)

        userHistroyRepository.findAll().forEach(System.out::println);


    }


    @Test
    void userRelationTest(){ //user 와 user history 의 관계 ( 1대 N )
        User user = new User();
        user.setName("pobi");
        user.setEmail("pobi@google.com");
        user.setGender(Gender.FEMALE);

        userRepository.save(user); //insert 시 history 에 자동으로 user수정 정보 저장된다.
        user.setName("ForBi");
        userRepository.save(user); // update 시 위 와 동일
        user.setEmail("ForBi@google.com");
        userRepository.save(user); //update

       userHistroyRepository.findAll().forEach(System.out::println);


  //      List<UserHistory> result = userHistroyRepository.findByUserId(userRepository.findByEmail("ForBi@google.com").getId());
  //      result.forEach(System.out::println);

        List<UserHistory> result = userRepository.findByEmail("ForBi@google.com").getUserHistories(); // 관계 맺은 entity를 자동으로 불러와 준다.


        result.forEach(System.out::println);

        User result2 = userHistroyRepository.findById(5L).orElseThrow(RuntimeException::new).getUser();
        System.out.println(result2);

    }
}