package com.java.developer.test.repository;

import com.java.developer.test.model.UserDAO;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserDAO, Long> {
  UserDAO findByUsername(String username);
}
