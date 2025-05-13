package com.lcwd.user.service.Repository;

import com.lcwd.user.service.Model.users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<users, Long> {
}
