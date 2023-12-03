
package com.libroVault.personalLibrary.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libroVault.personalLibrary.dto.BookDTO;
import com.libroVault.personalLibrary.dto.LibraryDTO;
import com.libroVault.personalLibrary.model.Book;
import com.libroVault.personalLibrary.model.Library;
import com.libroVault.personalLibrary.model.User;
import com.libroVault.personalLibrary.repository.BookRepository;
import com.libroVault.personalLibrary.repository.LibraryRepository;
import com.libroVault.personalLibrary.repository.UserRepository;

/**
 * Service class for managing libraries. It allows the creation,
 * deletion, or name change of a Library.
 */
@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    private final BookService bookService; // Add BookService dependency

    public LibraryService(LibraryRepository libraryRepository, BookService bookService) {
        this.libraryRepository = libraryRepository;
        this.bookService = bookService; // Initialize BookService
    }

    /**
     * Retrieves all libraries available in the system.
     * 
     * This method fetches and returns a list of all libraries stored in the
     * database.
     * It is typically used to display all libraries without any user-specific
     * filtering.
     * 
     * @return A list of all Library objects present in the database.
     */
    public List<Library> allLibraries() {
        return libraryRepository.findAll();
    }

    /**
     * Retrieves libraries associated with a specific user.
     * 
     * This method is used to fetch and return all libraries that are linked to a
     * particular user,
     * identified by the user's unique ID. It is useful for displaying libraries
     * that are specific to a user.
     * 
     * @param Id The unique identifier of the user whose libraries are to be
     *           retrieved.
     * @return A list of Library objects that are associated with the given user ID.
     * @throws IllegalArgumentException If the provided user ID is invalid or if any
     *                                  error occurs during the process.
     */
    public List<Library> librariesByUserId(String Id) {
        // Fetch libraries associated with the user ID
        return libraryRepository.findByUserId(Id);
    }

    /**
     * Method that creates a Library for a User.
     * 
     * @param libraryDTO The Library to add.
     * @return The new Library.
     */
    public Library createLibrary(LibraryDTO libraryDTO) {
        // Fetch the user
        User user = userRepository.findById(libraryDTO.getUser())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // Create a new Library object
        Library library = new Library();
        library.setLibraryId(libraryDTO.getLibraryId());
        library.setName(libraryDTO.getName());
        library.setUser(user);
        // add generated libraryId
        library.setLibraryId(UUID.randomUUID().toString());

        return libraryRepository.save(library);
    }

    /**
     * Method that deletes a Library.
     * 
     * @param libraryId The selected Library's id.
     */
    public void deleteLibrary(String libraryId) {
        // Check if the library exists
        if (!libraryRepository.findByLibraryId(libraryId).isPresent()) {
            throw new IllegalArgumentException("Library not found.");
        }

        // Delete the library
        libraryRepository.deleteByLibraryId(libraryId);
    }

    /**
     * Methods that changes an existing Libraries name.
     * 
     * @param libraryId The Library object Id to reference.
     * @param newName   The new name for the Library.
     * @return The Library with a new name.
     */
    public Library changeLibraryName(String libraryId, String newName) {

        Library library = libraryRepository.findByLibraryId(libraryId)
                .orElseThrow(() -> new IllegalArgumentException("Library not found."));
        if (libraryRepository.findByName(newName).isPresent()) {
            throw new IllegalArgumentException("Library name already in use.");
        }

        library.setName(newName);
        return libraryRepository.save(library);
    }

    /**
     * Adds a book to a specified library.
     * 
     * This method locates a library and a book using their respective IDs and adds
     * the book to the library's collection.
     * It throws an exception if either the library or the book is not found.
     * 
     * @param libraryId The unique identifier of the library to which the book is to
     *                  be added.
     * @param bookId    The unique identifier of the book to be added to the
     *                  library.
     * @throws IllegalArgumentException If either the library or the book is not
     *                                  found.
     */
    public void addBookToLibrary(String libraryId, String bookId) {
        Library library = libraryRepository.findByLibraryId(libraryId)
                .orElseThrow(() -> new IllegalArgumentException("Library not found."));

        // Assuming you have a method to fetch a book by its ID
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found."));

        library.getBooks().add(book);
        libraryRepository.save(library);
    }

    /**
     * Removes a book from a specified library.
     * 
     * This method locates a library using its ID and removes a book, identified by
     * its ID, from the library's collection.
     * It throws an exception if the library is not found. The book is removed based
     * on its ID.
     * 
     * @param libraryId The unique identifier of the library from which the book is
     *                  to be removed.
     * @param bookId    The unique identifier of the book to be removed from the
     *                  library.
     * @throws IllegalArgumentException If the library is not found.
     */
    public void removeBookFromLibrary(String libraryId, String bookId) {
        Library library = libraryRepository.findByLibraryId(libraryId)
                .orElseThrow(() -> new IllegalArgumentException("Library not found."));
        library.setBooks(library.getBooks().stream()
                .filter(book -> !book.getId().toString().equals(bookId))
                .collect(Collectors.toList()));
        libraryRepository.save(library);
    }

    // New method to update a book within a specific library
    public Book updateBookInLibrary(String bookId, BookDTO updatedBookDTO) {
        // Delegate the update operation to BookService
        return bookService.updateBook(bookId, updatedBookDTO);
    }

}
