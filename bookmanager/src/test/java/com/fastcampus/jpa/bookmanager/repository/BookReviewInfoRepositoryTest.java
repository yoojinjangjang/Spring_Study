package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.domain.BookReviewInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookReviewInfoRepositoryTest {

    @Autowired
    BookReviewInfoRepository bookReviewInfoRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    void crudTest(){
        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        //bookReviewInfo.setBookId(1L);
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfo.setReviewCount(2);

        bookReviewInfoRepository.save(bookReviewInfo); //insert

        System.out.println(bookReviewInfoRepository.findAll());
    }

    @Test
    void crudTest2(){


        givenBookReviewInfo();

        System.out.println(">>>" + bookReviewInfoRepository.findAll());

/*        Book result = bookRepository.findById(
                bookReviewInfoRepository
                        .findById(1L)
                        .orElseThrow(RuntimeException::new)
                        .getBook().getId()
        ).orElseThrow(RuntimeException::new);*/

        Book result = bookReviewInfoRepository.findById(1L).orElseThrow(RuntimeException::new).getBook(); // 1대1 관계의 book을 바로 가져온다.
        // 기존 -> bookreviewinfo 에서 book id 를 가지고 book reposiotory 에서 book 을 가져왔다.
        // 현재 -> bookreviewinfo 에서 1대1관계의 book 을 바로 가져온다.

        System.out.println(">>>" + result);

        BookReviewInfo result2 = bookRepository.findById(1L).orElseThrow(RuntimeException::new).getBookReviewInfo(); //서로 관계를 맺은 entity를 가져올수 있다.
        System.out.println(">>> " + result2);
    }

    private Book givenBook(){
        Book book = new Book();
        book.setName("jpa test");
        //book.setAuthorId(1L);
      //  book.setPublisherId(1L);

        return bookRepository.save(book); // book insert
    }

    private void givenBookReviewInfo(){
        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        bookReviewInfo.setBook(givenBook());
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfo.setReviewCount(2);

        bookReviewInfoRepository.save(bookReviewInfo); // book review info insert
    }



}