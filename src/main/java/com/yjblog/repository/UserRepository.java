package com.yjblog.repository;

import com.yjblog.domain.User;
import com.yjblog.request.Login;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// CrudRepository 이게 뭔지 확인해야 될 듯
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);
}
