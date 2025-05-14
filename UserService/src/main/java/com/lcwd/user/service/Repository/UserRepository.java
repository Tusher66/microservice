package com.lcwd.user.service.Repository;

import com.lcwd.user.service.Model.users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<users, Long> {

    @Query("SELECT u FROM users u WHERE  (:search IS NULL OR u.userName= :search)")
    Page<users> findAllUsers(@Param("search") Long search, Pageable pageable);
}
