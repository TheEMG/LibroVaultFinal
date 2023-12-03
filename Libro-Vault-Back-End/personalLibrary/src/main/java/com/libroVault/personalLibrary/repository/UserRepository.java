package com.libroVault.personalLibrary.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.libroVault.personalLibrary.model.User;


@Repository
public interface UserRepository extends MongoRepository<User, String>
{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
