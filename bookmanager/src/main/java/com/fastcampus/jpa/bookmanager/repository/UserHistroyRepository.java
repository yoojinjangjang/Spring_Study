package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHistroyRepository extends JpaRepository<UserHistory,Long> {

    //List<UserHistory> findByUserId(Long userId);

}
