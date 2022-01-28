package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.domain.listener.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor //entity 객체는 기본적으로 기본생성자가 필요하다.
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity { //pk 필요
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //생성된 값 그대로 가져와서 사용한다. 기본값 : AUTO -> H2 DB 의 기본 값은 sequence이다. id값 생성시 매번 hibernate sequence 를 공용으로 사용한다.
    private Long id;

    private String name;

    private String category;

    //private Long authorId;

   // @Column(name="publisher_id")
 //   private Long publisherId;

    @OneToOne(mappedBy = "book")
    @ToString.Exclude
    private BookReviewInfo bookReviewInfo;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="book_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    private Publisher publisher;


/*    @ManyToMany
    @ToString.Exclude
    private List<Author> authors = new ArrayList<>();*/

    @OneToMany
    @JoinColumn(name="book_id")
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();



/*    public void addAuthor(Author author){
        this.authors.add(author);
    }*/
    public void addBookAndAuthors(BookAndAuthor... bookAndAuthors){
    Collections.addAll(this.bookAndAuthors, bookAndAuthors);
    }
}
