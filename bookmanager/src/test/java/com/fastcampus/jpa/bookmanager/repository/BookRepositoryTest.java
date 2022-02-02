package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.domain.Publisher;
import com.fastcampus.jpa.bookmanager.domain.Review;
import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.repository.dto.BookStatus;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PostUpdate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("Jpa 패키지");
       // book.setAuthorId(1L);
     //   book.setPublisherId(1L);

        bookRepository.save(book); // insert

        System.out.println(bookRepository.findAll());
    }


    @Test
    @Transactional
    void bookRelationTest(){
        givenBookAndReview();

        User user = userRepository.findByEmail("yoojin@naver.com");
        System.out.println("Review : " + user.getReviews());
        System.out.println("Book : " + user.getReviews().get(0).getBook()); //user가 리뷰로 작성한 책
        System.out.println("Publisher : " + user.getReviews().get(0).getBook().getPublisher());


    }



    private void givenBookAndReview(){

        givenReview(givenUser(),givenBook(givenPublisher()));
    }
    private User givenUser(){
        return userRepository.findByEmail("yoojin@naver.com");
    }
    private Book givenBook(Publisher publisher){
        Book book = new Book();
        book.setName("JPA test");
        book.setPublisher(publisher);
        return bookRepository.save(book);
    }
    private void givenReview(User user,Book book){
        Review review = new Review();
        review.setTitle("내인생바꾼책");
        review.setContent("너무 재밋고 즐거운 책이었어요");
        review.setScore(5.0f);
        review.setUser(user);
        review.setBook(book);

        reviewRepository.save(review);
    }
    private Publisher givenPublisher(){
        Publisher publisher = new Publisher();
        publisher.setName("유진출팔산");
        return publisherRepository.save(publisher);
    }


    @Test
    void bookCascadeTest(){
        Book book = new Book();
        book.setName("JPA TEST PACKAGE");


        Publisher publisher = new Publisher();
        publisher.setName("YOOJIN");


        book.setPublisher(publisher); //영속성 관리되지 않고 저장시 연관관계 맺을수없다.
        bookRepository.save(book);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers: : "+ publisherRepository.findAll());

        Book book1 = bookRepository.findById(1L).get();
        book1.getPublisher().setName("not yoojin");
        bookRepository.save(book1);

        System.out.println("publisher : " + publisherRepository.findAll());

    //    Book book2 = bookRepository.findById(1L).get();
      //  bookRepository.delete(book2);


        Book book3 = bookRepository.findById(1L).get();
        book3.setPublisher(null);

        bookRepository.save(book3);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publisher:  " + publisherRepository.findAll());
        System.out.println("book3-Publisher: " + bookRepository.findById(1L).get().getPublisher());

    }

    @Test
    void bookRemoveCascadeTest(){
        Book book1 = bookRepository.findById(1L).get();
        bookRepository.delete(book1);

        bookRepository.findAll().forEach(book ->
                System.out.println(book.getPublisher()));



        System.out.println(bookRepository.findAll());
        System.out.println(publisherRepository.findAll());
    }

    @Test
    void softDeleteTest(){
       bookRepository.findAll().forEach(System.out::println);
        bookRepository.findByCategoryIsNull().forEach(System.out::println);
        //bookRepository.findALlByDeletedFalse().forEach(System.out::println);
    }



    @Test
    void queryTest(){
        bookRepository.findAll().forEach(System.out::println);

        System.out.println("findByCategoryIsNullAndNameEqualsAndCreateAtGreaterThanEqualAndUpdateAtGreaterThanEqual : " +
                bookRepository.findByCategoryIsNullAndNameEqualsAndCreateAtGreaterThanEqualAndUpdateAtGreaterThanEqual(
                        "JPA Test",
                        LocalDateTime.now().minusDays(1L),
                        LocalDateTime.now().minusDays(1L)
                ));

        System.out.println("findByNameRecently : " + bookRepository.findByNameRecently(
                "JPA test",
                LocalDateTime.now().minusDays(1L),
                LocalDateTime.now().minusDays(1L)
        ));

        System.out.println(bookRepository.findBookNameAndCategory());

        bookRepository.findBookNameAndCategory().forEach(b -> {
            System.out.println(b.getName()+" : " + b.getCategory());
        });


        bookRepository.findBookNameAndCategory(PageRequest.of(0,1)).forEach(book ->{
            System.out.println(book.getName() + " : " + book.getCategory());
        });


    }


    @Test
    void nativeQuery(){
/*        bookRepository.findAll().forEach(System.out::println);
        System.out.println("-----------------------------------");
        bookRepository.findAllCustom().forEach(System.out::println);*/


        List<Book> books = bookRepository.findAll();

        for(Book book : books){
            book.setCategory("IT전문서");

        }
        bookRepository.saveAll(books);

        System.out.println(bookRepository.findAll());


        System.out.println(bookRepository.updateCategories());
        bookRepository.findAllCustom().forEach(System.out::println);


        System.out.println(bookRepository.showTables());
    }

    @Test
    void converterTest(){
        bookRepository.findAll().forEach(System.out::println);


        Book book = new Book();
        book.setName("Converter Test");
        book.setStatus(new BookStatus(200));

        bookRepository.save(book);

        System.out.println(bookRepository.findRowRecord().values());


        bookRepository.findAll().forEach(System.out::println);


    }

}