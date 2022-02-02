package com.fastcampus.jpa.bookmanager.domain.converter;


import com.fastcampus.jpa.bookmanager.repository.dto.BookStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.swing.text.AbstractDocument;
// 앞 - entity , 뒤 - column

@Converter(autoApply = true)
// 해당 클래스가 컨버터라는 것을 알려주는 어노테이션이다.
public class BookStatusConverter implements AttributeConverter<BookStatus,Integer> {//제네릭에는 integer 사용
    @Override
    public Integer convertToDatabaseColumn(BookStatus attribute) {
       return attribute.getCode();

        //return null;
    }

    @Override //데이터베이스 Integer -> entity bookstatus
    public BookStatus convertToEntityAttribute(Integer dbData) {
        return dbData != null ? new BookStatus(dbData) : null; //삼항연산자 -- >  (조건) ? 참 : 거짓 ;
    }

}
