package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


//entity 객체를 저장하고 조회하기 위한 클래스 - repository
public interface UserRepository extends JpaRepository<User,Long> { //jparepository를 상속받고 제너릭으로 해당 객체와 pk값 타입을 넣어준다.
    // spring jpa 라이브러리가 지원해주는 영역이다.
    // 많은 jpa 메서드를 지원해준다.

    // ** select 기능 메소드 :  find,read,get,query,search,stream 등 다양한 접두어를 제공한다. 일반적으로 findBy.를 많이 사용한다.

    List<User> findByName(String name);  //이름을 통해 엔티티를 가져오는 메소드, 반횐되는 객체는 엔티티타입,optional타입,list타입 등 모든 타입으로 반환이 가능하다. 굉장히 많은 리턴타입을 제공한다.
    // 개발자가 정의해주는데로 동일한 값을 찾아서 반환해준다.
    //메소드명의 By 뒤에 적어주는 값(필드명)으로 where조건을 검사한다. ex) findByEmail : email의 where조건을 검사한다.


    User findByEmail(String email); //email로 user를 받아오는 메소드

    User getByEmail(String email);

    User readByEmail(String email);

    User queryByEmail(String email);

    User searchByEmail(String email);

    User streamByEmail(String email);

    User findUserByEmail(String email);    //find .. By : 엔티티이름으로 지정할 수 있다 .

    List<User> findSomethingByName(String email); //find .. By: ..에는 어떤값이 들어가던 매칭이 된다.
    // 메소드명에 잘못된이름이 들어가는 경우 컴파일 에러가 아닌 런타임 에러가 발생한다. 경고메세지를 잘 확인하여야 하며 메소드명을 정확히 설정할수 있도록 신경써야 한다.
    // 쿼리 메소드에 대해서는 테스트를 작성하는 편이 좋다고 생각이 된다. 만약 경고를 확인하지 못하여도 테스트 동작시 런타임오류가 발생하므로 이런 오류를 찾아준다.

    // ..First<number>.. , Top<number> .. : where 조건의 limit 조건을 생성하여 값을 반환해주며 해당 조건에 맞는 여러 요소중에 첫번째부터 몇개를 가져올 것이지를 지정해줄 수 있다.
    // First<가져올 수>  : 여러개를 가져오고자 할경우에는 list로 받아주어야 한다.
    User findFirst1Byname(String name);
    User findTop1Byname(String name);

    List<User> findLast1ByName(String name); //Last1 이라는 쿼리 키워드는 존재하지 않는다.  limit 적용이 되지 않는다. 인식하지 않는 키워드는 무시하게된다.



    //-----------------------------------------------------------------------------------------------------------------------//

    //And , Or : 둘다 참 , 둘중하나만 참 일 경우
    List<User> findByEmailAndName(String email,String name);
    List<User> findByEmailOrName(String email, String name);

    //after,before : 시간에 대한 조건이다. 우리의 필드에는 생성일과 수정일 있다.
    List<User> findByCreateAtAfter(LocalDateTime localDateTime);
    List<User> findByCreateAtBefore(LocalDateTime localDateTime);
    List<User> findByIdAfter(Long id); // 숫자일 경우 after는 큰값 before는 작은값이 반환된다.

    //Greater than , Greater than equals, less than , less than equals : 날짜(시간) , 숫자 모두 사용가능하다.
    List<User> findByCreateAtGreaterThan(LocalDateTime localDateTime); //해당 값보다 큰 값이 출력
    List<User> findByIdGreaterThanEqual(Long id); //해당 값보다 같거나 큰값이 출력

    //between : 양끝단을 포함한다.
    List<User> findByIdBetween(Long a,Long b); // a보다 크거나 같고 b보다 작거나 같은 값을 가지고 온다. 날짜(시간) 숫자 모두 사용가능
    // List<User> findByIdGreaterThanEqualAndIdLessThanEqual(Long a, Long b) 와 같다.


    //--------------------------------------------------------------------------------------------------------------------------//

    //IsEmpty,IsNotEmpty,IsNull,IsNotNull : 빈값,비지않은값 등 반환 -> 인자로 아무것도 받지 않는다.
    List<User> findByIdIsNotNull();
   // List<User> findByAddressIsNotEmpty(); //collection properties에서만 사용이 가능하다. 콜렉션타입의 empty를 검사한다.'

    //True,False : 인자를 받지 않는다.

    //In, NotIn : 많이 사용된다.
    List<User> findByNameIn(List<String> names); //해당 이터러블인자안에 있는 요소를 반환한다.
    List<User> findByNameNotIn(List<String> names); //해당 인자안에 없는 요소를 반환한다.

    //Contaning, ending with, start with ,like: 문자열에 대한 쿼리 like검색 제공
    List<User> findByNameStartingWith(String name);
    List<User> findByNameEndingWith(String name);
    List<User> findByNameContaining(String name);
    List<User> findByNameLike(String name);

    //Is : Is, Equals,(no keyword) 특별한 역할을 하지 않고, 코드의 가독성을 높이는 방법이다.
    List<User> findUserByNameIs(String name);
    List<User> findUserByName(String name);
    List<User> findUSerByNameEquals(String name); // findByName과 동일하다.

    //---------------------------------------------------------------------------------------------------------------------------//

    //sorting : OrderBy<필드><내림/오름차순>
    List<User> findTop1ByNameOrderByIdDesc(String name); //id를 내림차순 DESC 오름차순 ASC
    List<User> findFirstByNameOrderByIdDescEmailAsc(String name); //id에 역순이고 email에 정순이라는 정렬 추가 , 정렬기준을 추가시에는 바로 적어준다.
    List<User> findFirstByName(String name, Sort sort); //sort 인자 추가로 받아오기

    //paging 처리
    Page<User> findByName(String name, Pageable pageable); // page: slice를 상속 받는다. 전체 페이지에 대한 정보 , Slice : 부분집합에 대한 각각의 정보
    //Pageable : page 요청을 하는 값



    //-------------------------------------------------------------------------------------------------------------------------//
    @Query(value = "select * from user limit 1;",nativeQuery = true)
    Map<String,Object> findRowRecord();


    @Query(value = "select * from user",nativeQuery = true)
    List<Map<String ,Object>> findAllRowRecord();
}
