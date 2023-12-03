package com.libroVault.personalLibrary.controller;

import com.libroVault.personalLibrary.dto.BookDTO;
import com.libroVault.personalLibrary.model.Book;
import com.libroVault.personalLibrary.service.BookService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Class used to call the API
/*
    Instructions to see if the following works is as follows :
    Postman:
    Create[POST]: http://localhost:8080/api/books
    Update[PUT] : http://localhost:8080/api/books/{bookId} , where bookID is the object id from DB
    Delete[DELETE]: http://localhost:8080/api/books/remove/{bookId}, where bookID is the object id from DB

 */
@RestController
@CrossOrigin
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO){
        Book createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(
            @PathVariable String bookId,
            @RequestBody BookDTO updatedBookDTO
    ){
        Book updatedBook =  bookService.updateBook(bookId, updatedBookDTO);
        return new ResponseEntity<>(updatedBook,HttpStatus.OK);
    }

    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<Void> removeBook(@PathVariable String bookId){
        bookService.removeBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
