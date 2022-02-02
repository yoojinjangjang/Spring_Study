package com.fastcampus.jpa.bookmanager.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookNameAndCategory{
 private String name;
    private String category;
}
