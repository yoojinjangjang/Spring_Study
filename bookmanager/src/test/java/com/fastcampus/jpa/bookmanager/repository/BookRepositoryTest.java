package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("Jpa 패키지");
        book.setAuthor("yoojinTeacher");
        bookRepository.save(book); // insert

        System.out.println(bookRepository.findAll());
    }
}