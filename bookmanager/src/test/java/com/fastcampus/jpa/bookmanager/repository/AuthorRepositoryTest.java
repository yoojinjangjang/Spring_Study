package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Author;
import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.domain.BookAndAuthor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookAndAuthorRepository bookAndAuthorRepository;

    @Test
    @Transactional
    void mtomTest(){
        Book book1 = givenBook("book1");
        Book book2 = givenBook("book2");
        Book book3 = givenBook("book3");
        Book book4 = givenBook("book4");

        Author author = givenAuthor("a1");
        Author author1 = givenAuthor("a2");


        BookAndAuthor bookAndAuthor = givenBookAndAuthor(book1,author);
        BookAndAuthor bookAndAuthor1 = givenBookAndAuthor(book2, author1);
        BookAndAuthor bookAndAuthor2 = givenBookAndAuthor(book3,author);
        BookAndAuthor bookAndAuthor3 = givenBookAndAuthor(book3, author1);
        BookAndAuthor bookAndAuthor4 = givenBookAndAuthor(book4,author);
        BookAndAuthor bookAndAuthor5 = givenBookAndAuthor(book4, author1);



/*            book1.addAuthor(author);
            book2.addAuthor(author1);
            book3.addAuthor(author);
            book3.addAuthor(author1);
            book4.addAuthor(author);
            book4.addAuthor(author1);


            author.addBook(book1);
            author.addBook(book3);
            author.addBook(book4);
            author1.addBook(book2);
            author1.addBook(book3);
            author1.addBook(book4);*/

    book1.addBookAndAuthors(bookAndAuthor);
    book2.addBookAndAuthors(bookAndAuthor1);
    book3.addBookAndAuthors(bookAndAuthor2,bookAndAuthor3);
    book4.addBookAndAuthors(bookAndAuthor4,bookAndAuthor5);

    author.addBookAndAuthors(bookAndAuthor,bookAndAuthor2,bookAndAuthor4);
    author1.addBookAndAuthors(bookAndAuthor1,bookAndAuthor3,bookAndAuthor5);


    bookRepository.saveAll(Lists.newArrayList(book1,book2,book3,book4));
    authorRepository.saveAll(Lists.newArrayList(author, author1));

     //   System.out.println("authors through book : " + bookRepository.findAll().get(2).getAuthors());
     //   System.out.println("book through author : " + authorRepository.findAll().get(0).getBooks());

    bookRepository.findAll().get(2).getBookAndAuthors().forEach(o -> System.out.println(o.getAuthor()));
    authorRepository.findAll().get(0).getBookAndAuthors().forEach(o -> System.out.println(o.getBook()));

    }

    private Book givenBook(String name){
        Book book = new Book();
        book.setName(name);

        return bookRepository.save(book);
    }

    private Author givenAuthor(String name) {
        Author author = new Author();
        author.setName(name);

        return authorRepository.save(author);
    }

    private BookAndAuthor givenBookAndAuthor(Book book,Author author){
        BookAndAuthor bookAndAuthor = new BookAndAuthor();
        bookAndAuthor.setAuthor(author);
        bookAndAuthor.setBook(book);

       return bookAndAuthorRepository.save(bookAndAuthor);

    }
}