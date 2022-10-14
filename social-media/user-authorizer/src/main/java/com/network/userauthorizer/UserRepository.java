package com.network.userauthorizer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface UserRepository extends JpaRepository<MediaUser, Integer> {
    List<MediaUser> findByUsername(String userName);
    List<MediaUser> findByEmail(String email);
}
