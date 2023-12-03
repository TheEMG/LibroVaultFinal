package com.libroVault.personalLibrary.repository;

import com.libroVault.personalLibrary.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//Main used to send data into MongoRepository
@Repository
public interface BookRepository extends MongoRepository<Book, String> {

}
