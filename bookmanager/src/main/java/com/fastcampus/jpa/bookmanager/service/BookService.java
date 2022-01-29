package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.Author;
import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.repository.AuthorRepository;
import com.fastcampus.jpa.bookmanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
     private final BookRepository bookRepository;
     private final AuthorRepository authorRepository;

     @Transactional
     public void putBookAndAuthor(){
         Book book = new Book();
         book.setName("Jpa start");
         bookRepository.save(book);  // insert 실행

         Author author = new Author();
         author.setName("yooojin");
         authorRepository.save(author); //insert 실행


     }


}
