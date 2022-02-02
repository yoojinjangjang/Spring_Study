package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.Author;
import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.repository.AuthorRepository;
import com.fastcampus.jpa.bookmanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
     private final BookRepository bookRepository;
     private final AuthorRepository authorRepository;
    private final AuthorService authorService;
    private final EntityManager entityManager;

     @Transactional(propagation = Propagation.REQUIRED)
     void putBookAndAuthor(){
         Book book = new Book();
         book.setName("Jpa start");
         bookRepository.save(book);  // insert 실행
        try {
            authorService.putAuthor();
        } catch (RuntimeException e){
        }

        throw new RuntimeException("오류가 발생하였습니다.  transaction은 어떻게 될까요?");



     }

     @Transactional(isolation = Isolation.SERIALIZABLE)
    public void get(Long id){
         System.out.println(">>>> " + bookRepository.findById(id));
         System.out.println(">>>> " + bookRepository.findAll());
        entityManager.clear();

         System.out.println(">>>> " + bookRepository.findById(id));
         System.out.println(">>>> " + bookRepository.findAll());

         bookRepository.update();

         entityManager.clear();


/*         Book book = bookRepository.findById(id).get();
         book.setName("change?");
         bookRepository.save(book);*/

     }


    @Transactional
     public List<Book> getAll(){
         List<Book> books = bookRepository.findAll();

         books.forEach(System.out::println);
         return books;
     }


}
