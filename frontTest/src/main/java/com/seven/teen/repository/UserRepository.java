package com.seven.teen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seven.teen.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
}
