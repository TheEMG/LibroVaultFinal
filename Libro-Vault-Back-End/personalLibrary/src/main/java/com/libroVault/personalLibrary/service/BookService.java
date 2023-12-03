package com.libroVault.personalLibrary.service;

import com.libroVault.personalLibrary.dto.BookDTO;
import com.libroVault.personalLibrary.model.Book;
import com.libroVault.personalLibrary.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

//Book related logic such as input validation
@Service
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public Book createBook (BookDTO bookDTO){


        //Validation points
        if(bookDTO.getTitle() == null || bookDTO.getTitle().trim().isEmpty()){
            throw new IllegalArgumentException("Book Title is required");
        }
        if(bookDTO.getAuthor() == null || bookDTO.getAuthor().trim().isEmpty()){
            throw new IllegalArgumentException("Author is required");
        }
        if(bookDTO.getGenre() == null || bookDTO.getGenre().trim().isEmpty()){
            throw new IllegalArgumentException("Genre is required");
        }
        //create a new book entity
        Book newBook = new Book();

        //Set required book attributes
        newBook.setTitle(bookDTO.getTitle());
        newBook.setAuthor(bookDTO.getAuthor());
        newBook.setGenre(bookDTO.getGenre());

        //Optional book attributes
        if(bookDTO.getTranslator() != null){
            newBook.setTranslator(bookDTO.getTranslator());
        }
        if (bookDTO.getPublicationDate() != null) {
            newBook.setPublicationDate(bookDTO.getPublicationDate());
        }
        if(bookDTO.getEdition() != null){
            newBook.setEdition(bookDTO.getEdition());

        }
        if(bookDTO.getVolumeNumber() != null){
            newBook.setVolumeNumber(bookDTO.getVolumeNumber());
        }
        if(bookDTO.getSubgenre() != null){
            newBook.setSubgenre(bookDTO.getSubgenre());
        }
        if(bookDTO.getIsbn() != null){
            newBook.setIsbn(bookDTO.getIsbn());
        }

        //saves the book into mongoDB , that is the purpose of bookRepository
        return bookRepository.save(newBook);
    }

    //converts a BookDTO to a book entity
    //@param bookDTO the BookDTO to convert
    //@returns A Book entity with data from the BookDTO
    private Book convertDTOToEntity(BookDTO bookDTO){
        //New book object
        Book book = new Book();
        //We are using the attributes of BookDTO to set to the Book entity (this entity is whats being
        //sent to the DB "Book: and "BookDTO" are different for more information look at the BookDTO
        //class
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setTranslator(bookDTO.getTranslator());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book.setEdition(bookDTO.getEdition());
        book.setVolumeNumber(bookDTO.getVolumeNumber());
        book.setGenre(bookDTO.getGenre());
        book.setSubgenre(bookDTO.getSubgenre());
        book.setIsbn(bookDTO.getIsbn());

        //Return the book entity created from the provided BookDTO
        return book;
    }

    public Book updateBook(String bookId, BookDTO updatedBookDTO){
        //First retrieve from exisiting book by ID
        Book existingBook = bookRepository.findById(bookId).orElseThrow(()->
                new EntityNotFoundException("Book not found"));

        //Update the book attributes based on DTO
        //We can add which attributes can be updated or not
        existingBook.setTitle(updatedBookDTO.getTitle());
        existingBook.setAuthor(updatedBookDTO.getAuthor());
        existingBook.setTranslator(updatedBookDTO.getTranslator());
        existingBook.setPublicationDate(updatedBookDTO.getPublicationDate());
        existingBook.setEdition(updatedBookDTO.getEdition());
        existingBook.setVolumeNumber(updatedBookDTO.getVolumeNumber());
        existingBook.setGenre(updatedBookDTO.getGenre());
        existingBook.setSubgenre(updatedBookDTO.getSubgenre());
        existingBook.setIsbn(updatedBookDTO.getIsbn());

        return bookRepository.save(existingBook);
    }

    //remove book
    public void removeBook(String bookId){
        //check if book exists
        if(!bookRepository.existsById(bookId)){
            throw new EntityNotFoundException("Book new found ID: "+bookId);
        }

        //delete if book exists
        bookRepository.deleteById(bookId);
    }

}

