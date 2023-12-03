
package com.libroVault.personalLibrary.controller;

import com.libroVault.personalLibrary.dto.BookDTO;
import com.libroVault.personalLibrary.dto.LibraryDTO;
import com.libroVault.personalLibrary.model.Book;
import com.libroVault.personalLibrary.model.Library;
import com.libroVault.personalLibrary.service.LibraryService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/libraries")
public class LibraryController {

    // Fields
    private final LibraryService libraryService;

    /**
     * Constructor for the LibraryController
     * 
     * @param libraryService The LibraryService class that will be used.
     */
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * Method that retrievs all of the libraries (Not user specified.)
     */
    @GetMapping
    public ResponseEntity<List<Library>> getAllLibraries() {
        return new ResponseEntity<List<Library>>(libraryService.allLibraries(), HttpStatus.OK);
    }

    /**
     * Retrieves a list of libraries associated with a specific user.
     * 
     * This method handles a GET request and returns a list of libraries that are
     * linked to the user identified by the provided user ID.
     * 
     * @param userId The unique identifier of the user whose libraries are to be
     *               retrieved.
     * @return A ResponseEntity containing a list of Library objects associated with
     *         the given user ID and an HTTP status code.
     *         Returns an HTTP 200 OK response with the list of libraries on
     *         success,
     *         or an HTTP 400 Bad Request response if the request is invalid.
     * @throws IllegalArgumentException If the provided userId is invalid or if any
     *                                  other error occurs during the process.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Library>> getLibrariesByUserId(@PathVariable String userId) {
        try {
            List<Library> libraries = libraryService.librariesByUserId(userId);
            return new ResponseEntity<>(libraries, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Method that creates a new Library.
     * 
     * @param library The Library to create.
     * @return The new Library.
     */
    @PostMapping
    public ResponseEntity<Library> createLibrary(@RequestBody LibraryDTO libraryDTO) {
        try {
            Library createdLibrary = libraryService.createLibrary(libraryDTO);
            return ResponseEntity.ok(createdLibrary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Method that delte an existing Library.
     * 
     * @param libraryId The Library's Id.
     * @return A message saying the deletion was successful.
     */
    @DeleteMapping("/{libraryId}")
    public ResponseEntity<Void> deleteLibrary(@PathVariable String libraryId) {
        try {
            libraryService.deleteLibrary(libraryId);
            return ResponseEntity.noContent().build(); // Return 204 No Content on successful deletion
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if the library does not exist
        }
    }

    /**
     * Method that changes the name of a Library.
     * 
     * @param libraryId The Library Id.
     * @param newName   The new name of the Library.
     * @return The updated Library.
     */
    @PutMapping("/update/{libraryId}")
    public ResponseEntity<Library> changeLibraryName(@PathVariable String libraryId, @RequestParam String newName) {
        try {
            Library updatedLibrary = libraryService.changeLibraryName(libraryId, newName);
            return ResponseEntity.ok(updatedLibrary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Adds a book to a specified library.
     * 
     * @param libraryId The unique identifier of the library to which the book is to
     *                  be added.
     * @param bookId    The unique identifier of the book that is to be added to the
     *                  library.
     * @return A ResponseEntity indicating the result of the operation. Returns an
     *         HTTP 200 OK response on success,
     *         or an HTTP 400 Bad Request response with an error message on failure.
     * @throws IllegalArgumentException If the input parameters are invalid or the
     *                                  operation fails.
     */
    @PostMapping("/{libraryId}/addBook/{bookId}")
    public ResponseEntity<?> addBookToLibrary(@PathVariable String libraryId, @PathVariable String bookId) {
        try {
            libraryService.addBookToLibrary(libraryId, bookId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Removes a book from a specified library.
     * 
     * @param libraryId The unique identifier of the library from which the book is
     *                  to be removed.
     * @param bookId    The unique identifier of the book that is to be removed from
     *                  the library.
     * @return A ResponseEntity indicating the result of the operation. Returns an
     *         HTTP 200 OK response on success,
     *         or an HTTP 400 Bad Request response with an error message on failure.
     * @throws IllegalArgumentException If the input parameters are invalid or the
     *                                  operation fails.
     */
    @DeleteMapping("/{libraryId}/removeBook/{bookId}")
    public ResponseEntity<?> removeBookFromLibrary(@PathVariable String libraryId, @PathVariable String bookId) {
        try {
            libraryService.removeBookFromLibrary(libraryId, bookId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // New endpoint to update a book within a library
    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> updateBookInLibrary(
            @PathVariable String bookId,
            @RequestBody BookDTO updatedBookDTO) {
        Book updatedBook = libraryService.updateBookInLibrary(bookId, updatedBookDTO);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

}