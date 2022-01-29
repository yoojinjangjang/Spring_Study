package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.domain.Publisher;
import com.fastcampus.jpa.bookmanager.domain.Review;
import com.fastcampus.jpa.bookmanager.domain.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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


}